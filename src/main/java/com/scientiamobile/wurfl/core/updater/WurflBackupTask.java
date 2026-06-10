package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.utils.ExceptionUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 备份现有 WURFL 文件的管线任务。
 * <p>在下载新文件之前，将当前 WURFL 文件复制为 {@code .old} 后缀的备份文件。
 * 该备份文件在后续的覆盖一致性检查失败时，用于回滚操作。</p>
 */

public class WurflBackupTask implements UpdatePipelineTask {
    /**
     * 执行 WURFL 文件备份。
     * <p>从上下文中获取原始 WURFL 文件路径，将其复制为同路径下以 {@code .old} 结尾的备份文件。
     * 备份路径会存入上下文中供 {@link RollbackTask} 使用。</p>
     *
     * @param context 管线执行上下文 Map
     */
    public void execute(Map<String, Object> context) {
        String originalWurflPath = (String) context.get("original_wurfl_path");
        String backupWurflPath = originalWurflPath + ".old";

        try {
            FileUtils.copyFile(new File(originalWurflPath).getCanonicalFile(), new File(backupWurflPath).getCanonicalFile());
            context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
            context.put("backup_wurfl_path", backupWurflPath);
        } catch (IOException e) {
            context.put("task_error_message", "IOException: Error trying to backup WURFL file: " + ExceptionUtils.getFirstAvailableMessage(e));
            context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
        }
    }
}
