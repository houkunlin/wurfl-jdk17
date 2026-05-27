package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.resource.WURFLResource;

import java.io.Serial;

public class WURFLResourceException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 10L;
    private final transient WURFLResource resource;

    public WURFLResourceException(WURFLResource resource) {
        super("WURFL resource exception in: " + resource.getInfo());
        this.resource = resource;
    }

    public WURFLResourceException(WURFLResource resource, Throwable cause) {
        super(cause);
        this.resource = resource;
    }

    public WURFLResourceException(WURFLResource resource, String message) {
        super(message);
        this.resource = resource;
    }

    public WURFLResourceException(WURFLResource resource, String message, Throwable cause) {
        super(message, cause);
        this.resource = resource;
    }

    public WURFLResource getResource() {
        return this.resource;
    }
}
