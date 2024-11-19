package org.olhab.springboot.config;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.olhab.springboot.exception.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .sorted(Comparator.comparing(FieldError::getField))
                .map(fieldError -> {
                    String errorMessage = String.format("Field '%s': %s", fieldError.getField(), fieldError.getDefaultMessage());
                    logger.warn(errorMessage);
                    return errorMessage;
                })
                .toList();

        ErrorResponse errorResponse = new ErrorResponse(
                "Input data is invalid",
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return getStandardTemplateOfResponseEntity("Internal error", ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(java.sql.SQLException.class)
    public ResponseEntity<ErrorResponse> handleSQLException(java.sql.SQLException ex) {
        logger.error("SQL exception: {}", ex.getMessage(), ex);
        return getStandardTemplateOfResponseEntity("Internal error", ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
            EntityNotFoundException ex) {
        logger.warn(ex.getMessage(), ex);
        return getStandardTemplateOfResponseEntity("Database entity wasn't found", ex, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> getStandardTemplateOfResponseEntity(
            String message,
            Throwable e,
            HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(message, LocalDateTime.now(), httpStatus,
                List.of(e.getMessage()));
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    private record ErrorResponse(
            String message,
            LocalDateTime time,
            HttpStatus status,
            List<String> error
    ) {
    }
}
