package com.scientiamobile.wurfl.core.updater.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;

import java.io.Serial;

public class BadWurflExtensionException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = -6562761498439936698L;

    public BadWurflExtensionException(String message) {
        super(message);
    }
}
