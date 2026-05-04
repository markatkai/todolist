package todo.api;

import java.time.Instant;

public record NoteResponse(
    Instant createTime,
    String text,
    NoteStatus status,
    Instant finishingTime
) {
    
}
