package com.scientiamobile.wurfl.core.updater.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;

public class WurflFilePermissionException extends WURFLRuntimeException {
    private static final long serialVersionUID = 1L;

    public WurflFilePermissionException(String message) {
        super(message);
    }

    public WurflFilePermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
