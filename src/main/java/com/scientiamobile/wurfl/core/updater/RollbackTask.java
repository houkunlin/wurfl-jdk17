package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

/**
 * WURFL 更新失败时的回滚管线任务。
 * <p>当更新管线中的某个任务失败后，此任务将之前备份的原始 WURFL 文件恢复回原位，
 * 并清理临时文件和备份文件，确保系统回到更新前的稳定状态。</p>
 */

public class RollbackTask implements UpdatePipelineTask {
    private static final Logger log = LoggerFactory.getLogger(RollbackTask.class);

    /**
     * 执行回滚操作。
     * <p>仅当上下文标记 {@code original_wurfl_overwritten} 为 {@code "true"}（即文件已被覆盖）
     * 时才执行回滚。恢复完成后清理临时下载文件和备份文件。</p>
     *
     * @param context 管线执行上下文 Map
     */
    public void execute(Map<String, Object> context) {
        log.warn("Starting WURFL update rollback task");
        String originalWurflOverwritten = (String) context.get("original_wurfl_overwritten");
        if ("true".equals(originalWurflOverwritten)) {
            String backupWurflPath = (String) context.get("backup_wurfl_path");
            String originalWurflPath = (String) context.get("original_wurfl_path");

            try {
                FileUtils.copyFile(new File(backupWurflPath).getCanonicalFile(), new File(originalWurflPath).getCanonicalFile());
                log.info("Update rollback: restored file {}", originalWurflPath);
                FileUtils.deleteQuietly(new File((String) context.get("new_wurfl_temp_path")).getCanonicalFile());
                FileUtils.deleteQuietly(new File((String) context.get("backup_wurfl_path")).getCanonicalFile());
                context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
            } catch (Exception e) {
                String errorMessage = "An error occurred while performing WURFL update rollback task";
                log.error(errorMessage, e);
                throw new WURFLRuntimeException(errorMessage, e);
            }
        }
    }
}
