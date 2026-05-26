package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.utils.ExceptionUtils;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class WurflBackupTask implements UpdatePipelineTask {
   public void execute(Map<String, Object> context) {
      String originalWurflPath = (String)context.get("original_wurfl_path");
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
