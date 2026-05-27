package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;

import java.io.Serial;

public class WURFLParsingException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public WURFLParsingException() {
    }

    public WURFLParsingException(String message) {
        super(message);
    }

    public WURFLParsingException(Throwable cause) {
        super(cause);
    }

    public WURFLParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
