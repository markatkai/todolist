package todo.data;

import org.springframework.stereotype.Service;

import todo.api.NoteResponse;
import todo.api.NoteStatus;

@Service
public class NoteDao {

    public NoteResponse create(String text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    public NoteResponse update(Long id, String text, NoteStatus status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
