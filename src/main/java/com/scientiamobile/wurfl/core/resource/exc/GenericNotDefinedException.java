package com.scientiamobile.wurfl.core.resource.exc;

public class GenericNotDefinedException extends WURFLConsistencyException {
    private static final long serialVersionUID = 10L;

    public GenericNotDefinedException() {
        super("Device: generic is not defined");
    }
}
