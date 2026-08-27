package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Data transfer object used to return validation error information to API clients.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class ValidationErrorResponse {

    /**
     * Time at which the error response was generated.
     */
    private LocalDateTime timestamp;
    /**
     * HTTP status code associated with the response.
     */
    private int status;
    /**
     * HTTP error description.
     */
    private String error;
    /**
     * Human-readable message describing the result or error.
     */
    private String message;

    /**
     * Validation errors mapped to their corresponding request fields.
     */
    private Map<String, String> fieldErrors;

    /**
     * Creates a ValidationErrorResponse object. This no-argument constructor supports request/response binding and object creation.
     */
    public ValidationErrorResponse() {
    }

    /**
     * Returns the timestamp.
     * @return time at which the error response was generated.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Updates the timestamp carried by this DTO.
     * @param timestamp time at which the error response was generated.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the status.
     * @return hTTP status code associated with the response.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Updates the status carried by this DTO.
     * @param status hTTP status code associated with the response.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Returns the error.
     * @return hTTP error description.
     */
    public String getError() {
        return error;
    }

    /**
     * Updates the error carried by this DTO.
     * @param error hTTP error description.
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Returns the message.
     * @return human-readable message describing the result or error.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Updates the message carried by this DTO.
     * @param message human-readable message describing the result or error.
     */
    public void setMessage(String message) {
        this.message = message;
    }



    /**
     * Returns the field errors.
     * @return validation errors mapped to their corresponding request fields.
     */
    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    /**
     * Updates the field errors carried by this DTO.
     * @param fieldErrors validation errors mapped to their corresponding request fields.
     */
    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
