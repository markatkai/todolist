package todo.api;

import java.time.Instant;

public record NoteResponse(
    long id,
    Instant createTime,
    String text,
    NoteStatus status,
    Instant finishingTime
) {
    
}
