package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import java.io.File;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RollbackTask implements UpdatePipelineTask {
   private final Logger log = LoggerFactory.getLogger(this.getClass());

   public void execute(Map<String, Object> context) {
      this.log.warn("Starting WURFL update rollback task");
      String originalWurflOverwritten = (String)context.get("original_wurfl_overwritten");
      if ("true".equals(originalWurflOverwritten)) {
         String backupWurflPath = (String)context.get("backup_wurfl_path");
         String originalWurflPath = (String)context.get("original_wurfl_path");

         try {
            FileUtils.copyFile(new File(backupWurflPath), new File(originalWurflPath));
            this.log.info("Update rollback: restored file " + originalWurflPath);
            FileUtils.deleteQuietly(new File((String)context.get("new_wurfl_temp_path")));
            FileUtils.deleteQuietly(new File((String)context.get("backup_wurfl_path")));
            context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
         } catch (Throwable e) {
            String errorMessage = "An error occurred while performing WURFL update rollback task";
            this.log.error(errorMessage, e);
            throw new WURFLRuntimeException(errorMessage, e);
         }
      }
   }
}
