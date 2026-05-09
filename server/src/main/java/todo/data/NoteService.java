package todo.data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import todo.api.NoteStatus;
import todo.api.ApiOrder;

@Service
public class NoteService {

	public static final List<String> ALLOWED_ORDER_CRITERIA = List.of("createTime", "finishingTime", "status");

    private final NoteRepository repository;

    @Autowired
    public NoteService(NoteRepository repository) {
		this.repository = repository;
    }

    /**
     * Soft delete a todo-note: set the note's status as DELETED
     * @param id Identifier of the note to set as deleted
     */
    public void softDeleteById(Long id) {
		var existing = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Note not found"));
		if (existing.getStatus() == Status.DELETED) {
			// Let's pretend that the note does not exist if it is DELETED
			throw new EntityNotFoundException("Note not found");
		} 
		var updated = new Note(
				existing.getId(),
				existing.getCreateTime(),
				Instant.now(),
				existing.getText(),
				Status.DELETED,
				existing.getFinishingTime()
		);
		repository.save(updated);
    }

    /**
     * Update existing todo-note by id.
     * @param id Identifier of the note that should be updated
     * @param status new status for the task
     * @return Updated task item
     */
    public Note updateById(Long id, @NotNull NoteStatus newStatus) {
		var existing = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Note not found"));

		var now = Instant.now();
		var finishingTime = newStatus == NoteStatus.FINISHED && existing.getFinishingTime() == null
				? now
				: existing.getFinishingTime();

		if (existing.getStatus() == Status.DELETED) {
			// Let's pretend that the note does not exist if it is DELETED
			throw new EntityNotFoundException("Note not found");
		} 

		var updated = new Note(
				existing.getId(),
				existing.getCreateTime(),
				now,
				existing.getText(),
				Status.valueOf(newStatus.name()),
				finishingTime
		);

		return repository.save(updated);
    }

    /**
     * Create a new todo-note.
     * @param text text for the new todo task
     * @return full presentation of the newly created task
     */
    public Note create(String text) {
		var now = Instant.now();
		var note = new Note(
				null,
				now,
				now,
				text,
				Status.UNFINISHED,
				null
		);

		return repository.save(note);
    }

    /**
     * Find items by potential search criteria, and potentially sort by the given value and order.
     * @param timeStart Inclusive start time for finding tasks created earliest at this date time
     * @param timeEnd Exclusive end time for finding tasks created before this date time
     * @param status Status of the notes to search for
     * @param orderBy Order results by the given value
     * @param order Select if the results should be ordered ASC or DESC. Default is ASC. Only applies if orderBy is given
     * @return The list of 
     */
    public List<Note> findNonDeletedNotes(Instant timeStart, Instant timeEnd, NoteStatus status, String orderBy, ApiOrder order) {
		var searchCriteriaList = new ArrayList<Specification<Note>>();
		if (timeStart != null) {
			searchCriteriaList.add((root, q, cb) -> cb.greaterThanOrEqualTo(root.get("createTime"), timeStart));
		}
		if (timeEnd != null) {
			searchCriteriaList.add((root, q, cb) -> cb.lessThan(root.get("createTime"), timeEnd));
		}
		if (status != null) {
			searchCriteriaList.add((root, q, cb) -> cb.equal(root.get("status"), Status.valueOf(status.name())));
		} else {
			// If not finding by 
			searchCriteriaList.add((root, q, cb) -> cb.notEqual(root.get("status"), Status.DELETED.name()));
		}

		Specification<Note> searchCriteria = Specification.allOf(searchCriteriaList);
		Sort sort = orderBy == null ? Sort.unsorted()
			: Sort.by(order == ApiOrder.DESC ? Sort.Direction.DESC : Sort.Direction.ASC, orderBy);

		return repository.findAll(searchCriteria, sort);
    }
}
