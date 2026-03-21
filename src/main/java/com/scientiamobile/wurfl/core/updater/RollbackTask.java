package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import java.io.File;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RollbackTask implements UpdatePipelineTask {
  private final Logger a = LoggerFactory.getLogger(getClass());
  
  public void execute(Map<String, String> paramMap) {
    this.a.warn("Starting WURFL update rollback task");
    String str = (String)paramMap.get("original_wurfl_overwritten");
    if ("true".equals(str)) {
      str = (String)paramMap.get("backup_wurfl_path");
      String str1 = (String)paramMap.get("original_wurfl_path");
      try {
        FileUtils.copyFile(new File(str), new File(str1));
        this.a.info("Update rollback: restored file " + str1);
        FileUtils.deleteQuietly(new File((String)paramMap.get("new_wurfl_temp_path")));
        FileUtils.deleteQuietly(new File((String)paramMap.get("backup_wurfl_path")));
        paramMap.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
        return;
      } catch (Throwable throwable) {
        str = "An error occurred while performing WURFL update rollback task";
        this.a.error(str, throwable);
        throw new WURFLRuntimeException(str, throwable);
      } 
    } 
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\updater\RollbackTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */