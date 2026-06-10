package com.scientiamobile.wurfl.core.updater;

/**
 * Implementation of Update Result.
 */

public class UpdateResult {
    private UpdateResultStatus resultStatus;
    private String message;

    public UpdateResult(UpdateResultStatus resultStatus, String message) {
        this.resultStatus = resultStatus;
        this.message = message;
    }

    /**
     * Statu sro mtring.
     */

    public static UpdateResultStatus statusFromString(String value) {
        if (UpdateResultStatus.PIPELINE_TASK_FAILED.value().equals(value)) {
            return UpdateResultStatus.PIPELINE_TASK_FAILED;
        } else if (UpdateResultStatus.UPDATE_SKIPPED.value().equals(value)) {
            return UpdateResultStatus.UPDATE_SKIPPED;
        } else if (UpdateResultStatus.UPDATED.value().equals(value)) {
            return UpdateResultStatus.UPDATED;
        } else {
            return UpdateResultStatus.PIPELINE_TASK_DONE.value().equals(value) ? UpdateResultStatus.PIPELINE_TASK_DONE : null;
        }
    }

    /**
     * Returns whether this i spdat eroces successful.
 */

    public boolean isUpdateProcessSuccessful() {
        return this.resultStatus == UpdateResultStatus.UPDATED || this.resultStatus == UpdateResultStatus.UPDATE_SKIPPED;
    }

    final boolean isUpdated() {
        return this.resultStatus == UpdateResultStatus.UPDATED;
    }

    /**
     * Returns the resul ttatus.
 */

    public UpdateResultStatus getResultStatus() {
        return this.resultStatus;
    }

    public String getMessage() {
        return this.message;
    }
}
