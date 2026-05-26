package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.utils.ExceptionUtils;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewWurflFileDownloadTask implements UpdatePipelineTask {
   private final Logger log = LoggerFactory.getLogger(this.getClass());
   private ProxySettings proxySettings;

   public NewWurflFileDownloadTask() {
   }

   public NewWurflFileDownloadTask(ProxySettings proxySettings) {
      this.proxySettings = proxySettings;
   }

   public void execute(Map<String, Object> context) {
      try {
         String tempWurflPath = (String)context.get("original_wurfl_path") + ".wtmp";
         Integer connectionTimeoutMs = UpdatePipeline.getConnectionTimeoutMsOrDefault(context);
         URL newWurflUrl = URI.create((String)context.get("new_wurfl_url")).toURL();
         HttpsURLConnection connection = this.proxySettings != null ? (HttpsURLConnection)newWurflUrl.openConnection(this.proxySettings.getProxy()) : (HttpsURLConnection)newWurflUrl.openConnection();
         connection.setRequestMethod("GET");
         connection.setUseCaches(false);
         connection.setConnectTimeout(connectionTimeoutMs);
         connection.setReadTimeout(connectionTimeoutMs);
         connection.connect();
         int responseCode = connection.getResponseCode();
         if (responseCode == 200) {
            File tempWurflFile = new File(tempWurflPath);
            if (!tempWurflFile.exists()) {
               tempWurflFile.createNewFile();
            }

            FileUtils.copyInputStreamToFile(connection.getInputStream(), tempWurflFile);
            tempWurflFile.setLastModified(connection.getLastModified());
            this.log.info("WURFL updater: new WURFL file download completed");
            context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
            context.put("new_wurfl_temp_path", tempWurflPath);
         } else {
            this.log.error("Wurfl updater: unable to download new WURFL file, HTTP RESPONSE code: {}", responseCode);
            context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
            context.put("task_error_message", "Invalid HTTP response code " + responseCode);
         }
      } catch (Exception e) {
         context.put("task_error_message", "Error trying to check if a new WURFL file is available: " + ExceptionUtils.getFirstAvailableMessage(e));
         context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
      }
   }
}
