package todo.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import todo.data.Note;

@RestController
public class NoteController {

    @GetMapping("/notes")
    List<Note> findNotes() {
        // TODO
        return null;
    }

    @PostMapping("/notes")
    Note createNotes(@RequestBody NoteCreateRequest request) {
        // TODO
        return null;
    }

    @PutMapping("/notes/{id}")
    Note updateNotes(@PathVariable Long id, 
        @RequestBody NoteUpdateRequest request) {
        // TODO
        return null;
    }

    @DeleteMapping("/notes/{id}")
    void deleteNote(@PathVariable Long id) {
        // TODO
    }
}
