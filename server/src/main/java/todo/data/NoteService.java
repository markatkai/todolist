package todo.data;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import todo.api.NoteStatus;

@Service
public class NoteService {

    private final NoteRepository repository;

    @Autowired
    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    /**
     * Delete a todo-note.
     * @param id
     */
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * Update existing todo-note by id.
     * @param id
     * @param text
     * @param status
     * @return
     */
    public NoteDto updateByIId(Long id, String text, NoteStatus status) {
        NoteDto existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));

        NoteStatusDto newStatus = status != null
                ? NoteStatusDto.valueOf(status.name())
                : existing.getStatus();

        Instant now = Instant.now();
        Instant finishingTime = newStatus == NoteStatusDto.FINISHED && existing.getFinishingTime() == null
                ? now
                : existing.getFinishingTime();

        NoteDto updated = new NoteDto(
                existing.getId(),
                existing.getCreateTime(),
                now,
                text != null ? text : existing.getText(),
                newStatus,
                finishingTime
        );

        return repository.save(updated);
    }

    /**
     * Create a new todo-note.
     * @param text
     * @return
     */
    public NoteDto create(String text) {
        Instant now = Instant.now();
        NoteDto note = new NoteDto(
                null,
                now,
                now,
                text,
                NoteStatusDto.UNFINISHED,
                null
        );

        NoteDto saved = repository.save(note);
        return saved;
    }

    public List<NoteDto> find(Instant timeStart, Instant timeEnd) {
        return repository.findAll();
    }
}
