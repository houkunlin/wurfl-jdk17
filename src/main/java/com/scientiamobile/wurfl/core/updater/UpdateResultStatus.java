package com.scientiamobile.wurfl.core.updater;

/**
 * 更新管线任务的结果状态枚举。
 * <p>定义管线中各任务以及整个更新过程的执行结果状态。
 * 这些状态值以字符串形式存储在上下文 Map 中，供管线流程控制和结果解析使用。</p>
 *
 * <ul>
 *   <li>{@link #PIPELINE_TASK_DONE} - 单个管线任务执行成功</li>
 *   <li>{@link #PIPELINE_TASK_FAILED} - 单个管线任务执行失败</li>
 *   <li>{@link #UPDATED} - 整个更新流程完成并且 WURFL 文件已更新</li>
 *   <li>{@link #UPDATE_SKIPPED} - 更新被跳过（例如远程文件未变更）</li>
 * </ul>
 */

public enum UpdateResultStatus {
    PIPELINE_TASK_DONE("TASK_DONE"),
    PIPELINE_TASK_FAILED("TASK_FAILED"),
    UPDATED("UPDATED"),
    UPDATE_SKIPPED("UPDATE_SKIPPED");

    private String value;

    private UpdateResultStatus(String value) {
        this.value = value;
    }

    /**
     * 获取状态对应的字符串值。
     *
     * @return 状态字符串标识
     */
    public final String value() {
        return this.value;
    }
}
