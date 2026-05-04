package todo.api;

public record NoteUpdateRequest(
    String text,
    NoteStatus status
) {

}
