package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.utils.ExceptionUtils;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class WurflBackupTask implements UpdatePipelineTask {
  public void execute(Map<String, String> paramMap) {
    String str1 = (String)paramMap.get("original_wurfl_path");
    String str2 = str1 + ".old";
    try {
      FileUtils.copyFile(new File(str1), new File(str2));
      paramMap.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
      paramMap.put("backup_wurfl_path", str2);
      return;
    } catch (IOException iOException) {
      paramMap.put("task_error_message", "IOException: Error trying to backup WURFL file: " + ExceptionUtils.getFirstAvailableMessage(iOException));
      paramMap.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
      return;
    } 
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\updater\WurflBackupTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */