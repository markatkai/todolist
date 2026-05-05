package todo.data;

import org.springframework.data.domain.*;
import org.springframework.data.repository.*;


public interface NoteRepository extends ListCrudRepository<NoteDto, Long> {

    Page<NoteDto> findAll(Pageable pageable);
    
}
