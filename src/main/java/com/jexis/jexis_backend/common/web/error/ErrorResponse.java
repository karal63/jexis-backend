package com.jexis.jexis_backend.common.web.error;

/**
 * Strict returned error shape
 *
 * This class defines the structure of error responses sent back to clients when
 * exceptions occur. It includes fields for an error code, a human-readable
 * message, and an HTTP status
 *
 * Author: Leo
 */
public class ErrorResponse extends AErrorResponse {
    public ErrorResponse(Integer status, String code, String message) {
        super(status, code, message);
    }
}
