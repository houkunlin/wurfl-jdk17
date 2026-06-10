package com.scientiamobile.wurfl.core.resource.exc;

import java.io.Serial;

/**
 * Exception thrown when generic not defined occurs.
 */

public class GenericNotDefinedException extends WURFLConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;
    public GenericNotDefinedException() {
        super("Device: generic is not defined");
    }
}
