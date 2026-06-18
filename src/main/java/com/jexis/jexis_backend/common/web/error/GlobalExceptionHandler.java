package com.jexis.jexis_backend.common.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}