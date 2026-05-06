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
     * Soft delete a todo-note: set the note's status as DELETED
     * @param id
     */
    public void softDeleteById(Long id) {
		NoteDto existing = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Note not found"));
		NoteDto updated = new NoteDto(
				existing.getId(),
				existing.getCreateTime(),
				Instant.now(),
				existing.getText(),
				NoteStatusDto.DELETED,
				existing.getFinishingTime()
		);
		repository.save(updated);
    }

    /**
     * Update existing todo-note by id.
     * @param id
     * @param status
     * @return
     */
    public NoteDto updateByIId(Long id, NoteStatus status) {
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
				existing.getText(),
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

    /**
     * Find items.
     * @param timeStart
     * @param timeEnd
     * @param status
     * @return
     */
    public List<NoteDto> find(Instant timeStart, Instant timeEnd, NoteStatus status) {
		return repository.findAll().stream() // TODO find directly from db with WHERE clause
				.filter(note -> note.getStatus() == NoteStatusDto.valueOf(status.name())) 
				.toList();
    }
}
