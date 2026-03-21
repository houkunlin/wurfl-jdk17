package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.utils.ExceptionUtils;
import java.io.File;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckForNewWurflFileTask implements UpdatePipelineTask {
  private final Logger b = LoggerFactory.getLogger(getClass());
  
  static final SimpleDateFormat a;
  
  private ProxySettings c;
  
  public CheckForNewWurflFileTask() {}
  
  public CheckForNewWurflFileTask(ProxySettings paramProxySettings) {
    this.c = paramProxySettings;
  }
  
  public void execute(Map<String, String> paramMap) {
    String str2 = (String)paramMap.get("original_wurfl_path");
    Date date = new Date(file.lastModified());
    File file;
    String str1 = (file = new File(str2)).exists() ? a.format(date) : "";
    try {
      URL uRL = new URL((String)paramMap.get("new_wurfl_url"));
      Integer integer = UpdatePipeline.safeGetConnectionTimeout(paramMap);
      int i;
      if ((i = UpdatePipeline.a(uRL, str1, integer.intValue(), (String)paramMap.get("API_USER_AGENT"), this.c)) == 200) {
        paramMap.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
        return;
      } 
      if (i == 304) {
        paramMap.put("task_result_status", UpdateResultStatus.UPDATE_SKIPPED.value());
        this.b.info("WURFL file is already updated to the latest version, exiting file update process");
        return;
      } 
      if (i == 402) {
        paramMap.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
        paramMap.put("task_error_message", "Your WURFL LICENSE EXPIRED, WURFL file will not be updated. Please  renew you license to access newer versions of WURFL file and APIs");
        this.b.info("WURFL license is invalid or expired, exiting update process");
        return;
      } 
      paramMap.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
      paramMap.put("task_error_message", "Invalid HTTP response code " + i);
      return;
    } catch (SocketTimeoutException socketTimeoutException) {
      paramMap.put("task_error_message", "Error trying to check if a new WURFL file is available: connection timed out");
      paramMap.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
      return;
    } catch (Exception exception) {
      paramMap.put("task_error_message", "Error trying to check if a new WURFL file is available: " + ExceptionUtils.getFirstAvailableMessage(exception));
      paramMap.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
      return;
    } finally {}
  }
  
  static {
    (a = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).setTimeZone(TimeZone.getTimeZone("UTC"));
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\updater\CheckForNewWurflFileTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */