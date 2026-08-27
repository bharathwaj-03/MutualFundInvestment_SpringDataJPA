package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response;

import java.time.LocalDateTime;

/**
 * Data transfer object used to return error information to API clients.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class ErrorResponse {

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
     * Request path on which the error occurred.
     */
    private String path;

    /**
     * Creates a ErrorResponse object. This no-argument constructor supports request/response binding and object creation.
     */
    public ErrorResponse() {
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
     * Returns the path.
     * @return request path on which the error occurred.
     */
    public String getPath() {
        return path;
    }

    /**
     * Updates the path carried by this DTO.
     * @param path request path on which the error occurred.
     */
    public void setPath(String path) {
        this.path = path;
    }
}
