package todo.exception;

/**
 * Exception for the case in which search results have been asked to be ordered by an unknown value.
 */
public class InvalidRequestCriteriaException extends RuntimeException {

    private final String errorDescription;

    public InvalidRequestCriteriaException(String invalidValue) {
        this.errorDescription = invalidValue;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
