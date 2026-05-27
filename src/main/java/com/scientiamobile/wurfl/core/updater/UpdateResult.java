package com.scientiamobile.wurfl.core.updater;

public class UpdateResult {
    private UpdateResultStatus resultStatus;
    private String message;

    public UpdateResult(UpdateResultStatus resultStatus, String message) {
        this.resultStatus = resultStatus;
        this.message = message;
    }

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

    public boolean isUpdateProcessSuccessful() {
        return this.resultStatus == UpdateResultStatus.UPDATED || this.resultStatus == UpdateResultStatus.UPDATE_SKIPPED;
    }

    final boolean isUpdated() {
        return this.resultStatus == UpdateResultStatus.UPDATED;
    }

    public UpdateResultStatus getResultStatus() {
        return this.resultStatus;
    }

    public String getMessage() {
        return this.message;
    }
}
