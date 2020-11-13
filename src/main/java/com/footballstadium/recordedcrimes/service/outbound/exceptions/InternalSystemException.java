package com.footballstadium.recordedcrimes.service.outbound.exceptions;

/**
 * @author US
 *
 * Indicates that something internal to the system has failed. Subclasses of this exception further detail the cause of
 * the failure.
 */
public class InternalSystemException extends RuntimeException {
    /**
     * Creates a new instance of {@link InternalSystemException}.
     * @param message the exception message
     */
    public InternalSystemException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of {@link InternalSystemException}.
     * @param message the exception message
     * @param cause the root cause
     */
    public InternalSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
