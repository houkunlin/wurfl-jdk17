package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.utils.ExceptionUtils;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class WurflBackupTask implements UpdatePipelineTask {
   public void execute(Map var1) {
      String var2 = (String)var1.get("original_wurfl_path");
      String var3 = var2 + ".old";

      try {
         FileUtils.copyFile(new File(var2), new File(var3));
         var1.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
         var1.put("backup_wurfl_path", var3);
      } catch (IOException var4) {
         var1.put("task_error_message", "IOException: Error trying to backup WURFL file: " + ExceptionUtils.getFirstAvailableMessage(var4));
         var1.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
      }
   }
}
