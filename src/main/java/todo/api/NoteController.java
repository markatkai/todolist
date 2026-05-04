package todo.api;

import java.time.Instant;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {

    /**
     * Find notes created between times.
     * @param timeStart
     * @param timeEnd
     * @return
     */
    @GetMapping("/notes")
    List<NoteResponse> findNotes(@RequestParam Instant timeStart, 
        @RequestParam Instant timeEnd) {
        // TODO
        return null;
    }

    /**
     * Create a new unfinished note
     * @param request
     * @return
     */
    @PostMapping("/notes")
    NoteResponse createNotes(@RequestBody NoteCreateRequest request) {
        // TODO
        return null;
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
        // TODO
        return null;
    }

    /**
     * Delete a note
     * @param id
     */
    @DeleteMapping("/notes/{id}")
    void deleteNote(@PathVariable Long id) {
        // TODO
    }
}
