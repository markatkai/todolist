package todo.data;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public record NoteDto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,
    Instant createTime,
    Instant updateTime,
    String text,
    NoteStatusDto status,
    Instant finishingTime
) {
    
}
