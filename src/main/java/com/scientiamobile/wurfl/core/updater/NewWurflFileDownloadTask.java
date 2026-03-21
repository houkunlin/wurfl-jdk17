package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.utils.ExceptionUtils;
import java.io.File;
import java.net.URL;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewWurflFileDownloadTask implements UpdatePipelineTask {
  private final Logger a = LoggerFactory.getLogger(getClass());
  
  private ProxySettings b;
  
  public NewWurflFileDownloadTask() {}
  
  public NewWurflFileDownloadTask(ProxySettings paramProxySettings) {
    this.b = paramProxySettings;
  }
  
  public void execute(Map<String, String> paramMap) {
    try {
      File file;
      String str = (String)paramMap.get("original_wurfl_path") + ".wtmp";
      Integer integer = UpdatePipeline.safeGetConnectionTimeout(paramMap);
      int j = integer.intValue();
      URL uRL = new URL((String)paramMap.get("new_wurfl_url"));
      HttpsURLConnection httpsURLConnection;
      NewWurflFileDownloadTask newWurflFileDownloadTask;
      (httpsURLConnection = ((newWurflFileDownloadTask = this).b != null) ? (HttpsURLConnection)uRL.openConnection(newWurflFileDownloadTask.b.getProxy()) : (HttpsURLConnection)uRL.openConnection()).setRequestMethod("GET");
      httpsURLConnection.setUseCaches(false);
      httpsURLConnection.setConnectTimeout(j);
      httpsURLConnection.setReadTimeout(j);
      httpsURLConnection.connect();
      int i;
      if ((i = (httpsURLConnection = httpsURLConnection).getResponseCode()) == 200) {
        if (!(file = new File(str)).exists())
          file.createNewFile(); 
        FileUtils.copyInputStreamToFile(httpsURLConnection.getInputStream(), file);
        file.setLastModified(httpsURLConnection.getLastModified());
        this.a.info("WURFL updater: new WURFL file download completed");
        paramMap.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
        paramMap.put("new_wurfl_temp_path", str);
        return;
      } 
      this.a.error("Wurfl updater: unable to download new WURFL file, HTTP RESPONSE code: " + file);
      paramMap.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
      paramMap.put("task_error_message", "Invalid HTTP response code " + file);
      return;
    } catch (Exception exception) {
      paramMap.put("task_error_message", "Error trying to check if a new WURFL file is available: " + ExceptionUtils.getFirstAvailableMessage(exception));
      paramMap.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
      return;
    } 
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\updater\NewWurflFileDownloadTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */