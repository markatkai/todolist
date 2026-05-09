package todo.api;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import todo.data.Note;
import todo.data.NoteService;
import todo.exception.InvalidRequestCriteriaException;

@Validated
@RestController
@RequestMapping("/notes")
public class NotesController {

    Logger logger = LoggerFactory.getLogger(NotesController.class);

    private NoteService noteService;

    @Autowired
    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Find notes created between times.
     * @param timeStart Search for todo notes created earliest at this time 
     * @param timeEnd Search for todo notes created before this time
     * @param status Search for todo notes which have this status
     * @param orderBy Order search results by this value
     * @param order If ordering by a specific value, select if the order should be ascending or descending
     * @return List of existing todo notes that match the search criteria
     */
    @GetMapping()
    ResponseEntity<List<NoteResponse>> findNotes(@RequestParam(required = false) Instant timeStart, 
            @RequestParam(required = false) Instant timeEnd,
            @RequestParam(required = false) NoteStatus status,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) ApiOrder order
        ) {
            if (orderBy != null && !NoteService.ALLOWED_ORDER_CRITERIA.contains(orderBy)) {
                throw new InvalidRequestCriteriaException("invalid orderBy value");
            }
            if (timeStart != null && timeEnd != null && timeStart.isAfter(timeEnd)) {
                throw new InvalidRequestCriteriaException("timeStart must be before timeEnd");
            }
            logger.info("Attempting to find notes...");
            return ResponseEntity.ok(noteService.findNonDeletedNotes(timeStart, timeEnd, status, orderBy, order).stream()
                .map(this::noteToResponse)
                .toList());
    }

    /**
     * Create a new unfinished note
     * @param request Request body with new note data
     * @return
     */
    @PostMapping()
    ResponseEntity<NoteResponse> createNotes(@Valid @RequestBody NoteCreateRequest request) {
        logger.info("Attempting to create a note... {}", request);
        var created = noteService.create(request.text());
        return ResponseEntity.ok(noteToResponse(created));
    }

    /**
     * Update an existing note with new status
     * @param id Identifier of the note to update
     * @param request Request body with new updateable data
     * @return
     */
    @PutMapping("/{id}")
    ResponseEntity<NoteResponse> updateNotes(@PathVariable Long id, 
        @Valid @RequestBody NoteUpdateRequest request) {
        logger.info("Attempting to update a note... {}", request);
        var updated = noteService.updateById(id, request.status());
        return ResponseEntity.ok(noteToResponse(updated));
    }

    /**
     * Delete a note
     * @param id Identifier of the note to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteNote(@PathVariable Long id) {
        logger.info("Attempting to delete a note with id... {}", id);
        noteService.softDeleteById(id);
    }

    /**
     * Helper for converting internal presentation to api presentation.
     * @param dbnote note read from database
     * @return API representation of a todo note
     */
    private NoteResponse noteToResponse(Note dbnote) {
        return new NoteResponse(
            dbnote.getId(),
            dbnote.getCreateTime(),
            dbnote.getText(),
            NoteStatus.valueOf(dbnote.getStatus().name()),
            dbnote.getFinishingTime()
        );
    }
}
