package com.scientiamobile.wurfl.core.updater;

/**
 * WURFL 更新操作的结果封装。
 * <p>包含更新过程的最终状态（成功、跳过、失败）以及相关的描述信息。
 * 提供便捷方法判断更新过程是否成功、WURFL 文件是否实际被更新等。</p>
 */

public class UpdateResult {
    private UpdateResultStatus resultStatus;
    private String message;

    public UpdateResult(UpdateResultStatus resultStatus, String message) {
        this.resultStatus = resultStatus;
        this.message = message;
    }

    /**
     * 将字符串状态值转换为对应的枚举常量。
     * <p>用于将上下文 Map 中存储的字符串状态还原为枚举类型。</p>
     *
     * @param value 状态字符串（"TASK_DONE"、"TASK_FAILED"、"UPDATED"、"UPDATE_SKIPPED"）
     * @return 对应的枚举常量，如果无法识别则返回 {@code null}
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

    /**
     * 判断 WURFL 文件是否实际被更新。
     *
     * @return 如果文件已更新则返回 {@code true}
     */

    final boolean isUpdated() {
        return this.resultStatus == UpdateResultStatus.UPDATED;
    }

    /**
     * 获取更新结果的状态。
     *
     * @return 更新结果状态枚举
     */

    public UpdateResultStatus getResultStatus() {
        return this.resultStatus;
    }

    /**
     * 获取更新结果的描述信息。
     * <p>成功时为更新时间戳或跳过的说明，失败时为具体的错误信息。</p>
     *
     * @return 结果描述文本
     */
    public String getMessage() {
        return this.message;
    }
}
