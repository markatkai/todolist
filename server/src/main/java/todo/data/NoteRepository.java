package todo.data;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.*;


public interface NoteRepository extends ListCrudRepository<Note, Long>,  JpaSpecificationExecutor<Note> {
}
