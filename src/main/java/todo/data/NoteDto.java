package todo.data;

import java.time.Instant;

public record NoteDto(
    Instant createTime,
    Instant updateTime,
    String text,
    NoteStatusDto status,
    Instant finishingTime
) {
    
}
