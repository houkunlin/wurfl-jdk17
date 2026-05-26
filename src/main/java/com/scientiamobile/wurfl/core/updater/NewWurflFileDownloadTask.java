package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.utils.ExceptionUtils;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewWurflFileDownloadTask implements UpdatePipelineTask {
   private static final Logger log = LoggerFactory.getLogger(NewWurflFileDownloadTask.class);
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
         Validate.isTrue(newWurflUrl.getHost() != null && (newWurflUrl.getHost().endsWith(".scientiamobile.com") || newWurflUrl.getHost().equals("localhost") || newWurflUrl.getHost().equals("127.0.0.1")), "Invalid URL host: " + newWurflUrl.getHost());
         HttpsURLConnection connection = this.proxySettings != null ? (HttpsURLConnection)newWurflUrl.openConnection(this.proxySettings.getProxy()) : (HttpsURLConnection)newWurflUrl.openConnection();
         connection.setRequestMethod("GET");
         connection.setUseCaches(false);
         connection.setConnectTimeout(connectionTimeoutMs);
         connection.setReadTimeout(connectionTimeoutMs);
         connection.connect();
         int responseCode = connection.getResponseCode();
         if (responseCode == 200) {
            File tempWurflFile = new File(tempWurflPath).getCanonicalFile();
            if (!tempWurflFile.exists() && !tempWurflFile.createNewFile()) {
               log.warn("Failed to create temp WURFL file: {}", tempWurflFile.getAbsolutePath());
            }

            FileUtils.copyInputStreamToFile(connection.getInputStream(), tempWurflFile);
            if (!tempWurflFile.setLastModified(connection.getLastModified())) {
               log.warn("Failed to set last modified time on temp WURFL file");
            }
            log.info("WURFL updater: new WURFL file download completed");
            context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
            context.put("new_wurfl_temp_path", tempWurflPath);
         } else {
            log.error("Wurfl updater: unable to download new WURFL file, HTTP RESPONSE code: {}", responseCode);
            context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
            context.put("task_error_message", "Invalid HTTP response code " + responseCode);
         }
      } catch (Exception e) {
         context.put("task_error_message", "Error trying to check if a new WURFL file is available: " + ExceptionUtils.getFirstAvailableMessage(e));
         context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
      }
   }
}
