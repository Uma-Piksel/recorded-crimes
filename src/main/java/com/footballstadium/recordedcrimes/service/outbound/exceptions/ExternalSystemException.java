package com.footballstadium.recordedcrimes.service.outbound.exceptions;

/**
 * Indicates that an external system has failed.
 * @author US
 */
public class ExternalSystemException extends RuntimeException {
    private final String system;

    /**
     * Creates a new instance of {@link ExternalSystemException}.
     * @param system the system in error
     * @param message the exception message
     */
    public ExternalSystemException(String system, String message) {
        super(message);
        this.system = system;
    }

    /**
     * Creates a new instance of {@link ExternalSystemException}.
     * @param system the system in error
     * @param message the exception message
     * @param cause the root cause
     */
    public ExternalSystemException(String system, String message, Throwable cause) {
        super(message, cause);
        this.system = system;
    }

    /**
     * Returns the system.
     * @return the system
     */
    public String getSystem() {
        return system;
    }

}

