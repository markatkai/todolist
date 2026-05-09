package todo.api;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import todo.data.NoteDto;
import todo.data.NoteService;

@RestController
public class NoteController {

    Logger logger = LoggerFactory.getLogger(NoteController.class);

    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Find notes created between times.
     * @param timeStart
     * @param timeEnd
     * @return
     */
    @GetMapping("/notes")
    List<NoteResponse> findNotes(@RequestParam(required = false) Instant timeStart, 
            @RequestParam(required = false) Instant timeEnd,
            @RequestParam(required = false) NoteStatus status
        ) {
            logger.info("Attempting to find notes...");
            return noteService.find(timeStart, timeEnd, status).stream()
                .map(this::noteToResponse)
                .toList();
    }

    /**
     * Create a new unfinished note
     * @param request
     * @return
     */
    @PostMapping("/notes")
    NoteResponse createNotes(@RequestBody NoteCreateRequest request) {
        logger.info("Attempting to create notes... {}", request);
        var created = noteService.create(request.text());
        return noteToResponse(created);
    }

    /**
     * Update an existing note: text or status
     * @param id
     * @param request
     * @return
     */
    @PutMapping("/notes/{id}")
    NoteResponse updateNotes(@PathVariable Long id, 
        @RequestBody NoteUpdateRequest request) {
        logger.info("Attempting to update notes... {}", request);
        var updated = noteService.updateByIId(id, request.status());
        return noteToResponse(updated);
    }

    /**
     * Delete a note
     * @param id
     */
    @DeleteMapping("/notes/{id}")
    void deleteNote(@PathVariable Long id) {
        logger.info("Attempting to delete note with id... {}", id);
        noteService.softDeleteById(id);
    }

    /**
     * Helper for converting internal presentation to api presentation.
     * @param noteDto
     * @return
     */
    private NoteResponse noteToResponse(NoteDto noteDto) {
        return new NoteResponse(
            noteDto.getId(),
            noteDto.getCreateTime(),
            noteDto.getText(),
            NoteStatus.valueOf(noteDto.getStatus().name()),
            noteDto.getFinishingTime()
        );
    }
}
