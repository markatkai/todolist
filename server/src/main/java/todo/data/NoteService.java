package todo.data;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import todo.api.NoteResponse;
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
    public NoteResponse updateByIId(Long id, String text, NoteStatus status) {
        NoteDto existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));

        NoteStatusDto newStatus = status != null
                ? NoteStatusDto.valueOf(status.name())
                : existing.status();

        Instant now = Instant.now();
        Instant finishingTime = newStatus == NoteStatusDto.FINISHED && existing.finishingTime() == null
                ? now
                : existing.finishingTime();

        NoteDto updated = new NoteDto(
                existing.id(),
                existing.createTime(),
                now,
                text != null ? text : existing.text(),
                newStatus,
                finishingTime
        );

        NoteDto saved = repository.save(updated);
        return new NoteResponse(
                saved.createTime(),
                saved.text(),
                NoteStatus.valueOf(saved.status().name()),
                saved.finishingTime()
        );
    }

    /**
     * Create a new todo-note.
     * @param text
     * @return
     */
    public NoteResponse create(String text) {
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
        return new NoteResponse(
                saved.createTime(),
                saved.text(),
                NoteStatus.valueOf(saved.status().name()),
                saved.finishingTime()
        );
    }


}
