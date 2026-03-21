package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.GeneralWURFLEngine;
import com.scientiamobile.wurfl.core.utils.ExceptionUtils;
import java.io.File;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OverwriteAndCheckConsistencyTask implements UpdatePipelineTask {
  private final Logger a = LoggerFactory.getLogger(getClass());
  
  private String[] b;
  
  public OverwriteAndCheckConsistencyTask() {}
  
  public OverwriteAndCheckConsistencyTask(String[] paramArrayOfString) {
    this.b = paramArrayOfString;
  }
  
  public void execute(Map<String, String> paramMap) {
    String str1;
    Validate.notEmpty(str1 = (String)paramMap.get("new_wurfl_temp_path"));
    String str2 = (String)paramMap.get("original_wurfl_path");
    try {
      GeneralWURFLEngine generalWURFLEngine;
      (new File(str2)).delete();
      FileUtils.copyFile(new File(str1), new File(str2), true);
      paramMap.put("original_wurfl_overwritten", "true");
      if (ArrayUtils.isEmpty((Object[])this.b)) {
        generalWURFLEngine = new GeneralWURFLEngine(str2);
      } else {
        generalWURFLEngine = new GeneralWURFLEngine(str2, this.b);
      } 
      generalWURFLEngine.load();
      paramMap.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
      return;
    } catch (Throwable throwable) {
      this.a.error("WURFL consistency check failed", throwable);
      paramMap.put("task_error_message", "Error trying to overwrite WURFL file : " + ExceptionUtils.getFirstAvailableMessage(throwable));
      paramMap.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
      return;
    } 
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\updater\OverwriteAndCheckConsistencyTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
