package com.scientiamobile.wurfl.core.updater;

import java.util.Map;

/**
 * WURFL 更新管线的任务接口。
 * <p>定义更新管线中各步骤的执行契约。每个实现类代表管线中的一个独立步骤，
 * 例如检查更新、下载文件、备份、覆盖文件、回滚和清理等。</p>
 *
 * <p>各任务通过共享的上下文 {@code Map<String, Object>} 传递数据，
 * 形成链式处理流程。</p>
 */

public interface UpdatePipelineTask {
    /**
     * 执行当前管线任务。
     * <p>任务从上下文中读取所需的输入数据，并将执行结果（状态、临时路径、错误信息等）
     * 写回上下文中，供后续任务或最终结果处理使用。</p>
     *
     * @param context 管线执行上下文 Map，用于在任务间传递数据
     */
    void execute(Map<String, Object> context);
}
