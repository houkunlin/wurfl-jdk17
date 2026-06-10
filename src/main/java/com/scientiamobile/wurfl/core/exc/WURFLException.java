package com.scientiamobile.wurfl.core.exc;

import java.io.Serial;

/**
 * Exception thrown when wurfl occurs.
 */

public abstract class WURFLException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    protected WURFLException() {
        super("Generic Exception in WURFL.");
    }

    protected WURFLException(String message) {
        super(message);
    }

    protected WURFLException(Throwable cause) {
        super(cause);
    }

    protected WURFLException(String message, Throwable cause) {
        super(message, cause);
    }
}
