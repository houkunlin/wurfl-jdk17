package com.scientiamobile.wurfl.core.updater;

public enum UpdateResultStatus {
    PIPELINE_TASK_DONE("TASK_DONE"),
    PIPELINE_TASK_FAILED("TASK_FAILED"),
    UPDATED("UPDATED"),
    UPDATE_SKIPPED("UPDATE_SKIPPED");

    private String value;

    private UpdateResultStatus(String value) {
        this.value = value;
    }

    public final String value() {
        return this.value;
    }
}
