package ch.zero.project295.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

import java.util.stream.Collectors;

/**
 * This is a global exception handler that handles different types of exceptions.
 * It provides error messages for various scenarios such as validation errors,
 * entity not found exceptions, constraint violations, and unexpected errors.
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation exceptions that occur when fields fail validation checks.
     *
     * @param ex the MethodArgumentNotValidException
     * @return a ResponseEntity containing a user-friendly error message
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage()) // Extracts custom validation messages
                .collect(Collectors.joining(", "));

        ApiResponse<String> response = new ApiResponse<>(false, errorMessage, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles constraint violations such as foreign key or unique constraints violations.
     *
     * @param ex the ConstraintViolationException
     * @return a ResponseEntity containing a user-friendly error message
     */

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(violation -> violation.getMessage()) // Extracts the custom constraint violation messages
                .collect(Collectors.joining(", "));

        ApiResponse<String> response = new ApiResponse<>(false, errorMessage, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles entity not found exceptions when the requested entity doesn't exist in the database.
     *
     * @param ex the EntityNotFoundException
     * @return a ResponseEntity containing a user-friendly error message
     */

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiResponse<String> response = new ApiResponse<>(false, "The requested entity was not found.", null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles illegal argument exceptions, such as when invalid IDs are provided.
     *
     * @param ex the IllegalArgumentException
     * @return a ResponseEntity containing a user-friendly error message
     */

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiResponse<String> response = new ApiResponse<>(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles all other exceptions, providing a simple fallback for unexpected errors.
     *
     * @param ex the Exception
     * @return a ResponseEntity containing a generic error message
     */
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
        ex.printStackTrace();
        ApiResponse<String> response = new ApiResponse<>(false, "An unexpected error occurred", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
