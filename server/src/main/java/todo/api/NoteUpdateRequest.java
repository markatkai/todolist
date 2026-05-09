package todo.api;

import jakarta.validation.constraints.NotNull;

public record NoteUpdateRequest(
    @NotNull NoteStatus status
) {}
