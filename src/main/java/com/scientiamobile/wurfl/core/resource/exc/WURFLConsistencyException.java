package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;

public abstract class WURFLConsistencyException extends WURFLRuntimeException {

    protected WURFLConsistencyException() {
        super("WURFL consistency exception");
    }

    protected WURFLConsistencyException(Throwable cause) {
        super("WURFL consistency exception", cause);
    }

    protected WURFLConsistencyException(String message) {
        super(message);
    }

    protected WURFLConsistencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
