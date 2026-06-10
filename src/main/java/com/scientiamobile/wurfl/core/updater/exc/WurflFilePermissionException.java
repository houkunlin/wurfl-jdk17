package com.scientiamobile.wurfl.core.updater.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;

import java.io.Serial;

/**
 * Exception thrown when wurfl file permission occurs.
 */

public class WurflFilePermissionException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public WurflFilePermissionException(String message) {
        super(message);
    }

    public WurflFilePermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
