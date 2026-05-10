package todo.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import todo.exception.InvalidRequestCriteriaException;

@RestControllerAdvice
public class ApiExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse entityErrorResponse(EntityNotFoundException ex) {
        logger.error("Entity not found");
        return new ErrorResponse("Not found");
    }

    @ExceptionHandler(InvalidRequestCriteriaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse invalidSortValue(InvalidRequestCriteriaException ex) {
        logger.error("Invalid orderBy value {}", ex.getErrorDescription());
        return new ErrorResponse(ex.getErrorDescription());
    }

    /* Request validation fails */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse methodArgumentNotValid(MethodArgumentNotValidException ex) {
        logger.error("Invalid request", ex);
        return new ErrorResponse("Invalid request body");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse runtimeException(RuntimeException ex) {
        logger.error("Runtime exception", ex);
        return new ErrorResponse("Unexpected error");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse exception(Exception ex) {
        logger.error("Exception", ex);
        return new ErrorResponse("Unexpected error");
    }
}
