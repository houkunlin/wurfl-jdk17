package com.scientiamobile.wurfl.core.updater;

/**
 * Implementation of Frequency.
 */

public enum Frequency {
    MINUTES(60000),
    DAILY(86400000),
    THREE_DAYS(259200000),
    WEEKLY(604800000);

    private long value;

    private Frequency(int value) {
        this.value = value;
    }

    /**
     * Value.
     */

    public final long value() {
        return this.value;
    }
}
