package com.scientiamobile.wurfl.core.exc;

import java.io.Serial;

public class WURFLRuntimeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public WURFLRuntimeException() {
        super("WURFL unexpected exception");
    }

    public WURFLRuntimeException(String message) {
        super(message);
    }

    public WURFLRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WURFLRuntimeException(Throwable cause) {
        super("WURFL unexpected exception", cause);
    }
}
