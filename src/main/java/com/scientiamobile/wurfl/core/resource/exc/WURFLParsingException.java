package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;

public class WURFLParsingException extends WURFLRuntimeException {
    private static final long serialVersionUID = 10L;

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
