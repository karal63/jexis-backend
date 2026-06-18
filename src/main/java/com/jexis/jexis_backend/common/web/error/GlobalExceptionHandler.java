package com.jexis.jexis_backend.common.web.error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Catches all exceptions of type {@link DomainException} thrown by any
 * controller
 *
 * When a {@link DomainException} is thrown, this handler constructs an to
 * custom error shape defined by {@link ErrorResponse} and returns it with a
 * specified HTTP status code. This allows for consistent error responses across
 * the application, providing clients with structured information about what
 * went wrong, including an error code, message, and HTTP status.
 *
 * Author: Leo
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handle(DomainException ex) {
        return ResponseEntity
                .status(ex.getStatus() != null ? ex.getStatus() : HttpStatus.BAD_REQUEST.value())
                .body(new ErrorResponse(ex.getStatus(), ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleServerError(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "UNKNOWN_ERROR", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "VALIDATION_ERROR",
                        errors.toString()));
    }
}