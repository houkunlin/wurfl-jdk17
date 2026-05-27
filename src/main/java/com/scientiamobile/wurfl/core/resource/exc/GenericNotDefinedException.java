package com.scientiamobile.wurfl.core.resource.exc;

public class GenericNotDefinedException extends WURFLConsistencyException {
    public GenericNotDefinedException() {
        super("Device: generic is not defined");
    }
}
