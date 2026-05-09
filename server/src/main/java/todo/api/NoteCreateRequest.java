package todo.api;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NoteCreateRequest(
    @NotNull @Size(min=1, max=120) String text
) {}
