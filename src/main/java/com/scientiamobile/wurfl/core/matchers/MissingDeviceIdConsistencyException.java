package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.resource.exc.WURFLConsistencyException;

import java.io.Serial;

/**
 * Exception thrown when missing device id consistency occurs.
 */

final class MissingDeviceIdConsistencyException extends WURFLConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;

    MissingDeviceIdConsistencyException(String message) {
        super(message);
    }
}

