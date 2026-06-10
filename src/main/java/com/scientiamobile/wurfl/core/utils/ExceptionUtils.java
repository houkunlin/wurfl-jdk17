package com.scientiamobile.wurfl.core.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of Exception Utils.
 */

public class ExceptionUtils {
    public static String getFirstAvailableMessage(Throwable throwable) {
        return getFirstAvailableMessage(throwable, 10);
    }

    /**
     * Returns the firs tvailabl eessage.
     */

    private static String getFirstAvailableMessage(Throwable throwable, int maxDepth) {
        while (throwable != null && maxDepth > 0) {
            String message = throwable.getMessage();
            if (StringUtils.isNotEmpty(message)) {
                return message;
            }
            throwable = throwable.getCause();
            maxDepth--;
        }
        return "";
    }
}
