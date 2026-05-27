package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

public class RollbackTask implements UpdatePipelineTask {
    private static final Logger log = LoggerFactory.getLogger(RollbackTask.class);

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
            } catch (Throwable e) {
                String errorMessage = "An error occurred while performing WURFL update rollback task";
                log.error(errorMessage, e);
                throw new WURFLRuntimeException(errorMessage, e);
            }
        }
    }
}
