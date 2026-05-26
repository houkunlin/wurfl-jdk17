package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.utils.ExceptionUtils;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckForNewWurflFileTask implements UpdatePipelineTask {
   private static final Logger log = LoggerFactory.getLogger(CheckForNewWurflFileTask.class);
   static final SimpleDateFormat LAST_MODIFIED_FORMAT;
   private ProxySettings proxySettings;

   public CheckForNewWurflFileTask() {
   }

   public CheckForNewWurflFileTask(ProxySettings proxySettings) {
      this.proxySettings = proxySettings;
   }

   public void execute(Map<String, Object> context) {
      String originalWurflPath = (String)context.get("original_wurfl_path");

      try {
         File originalWurflFile = new File(originalWurflPath).getCanonicalFile();
         String ifModifiedSince = originalWurflFile.exists() ? LAST_MODIFIED_FORMAT.format(new Date(originalWurflFile.lastModified())) : "";
         URL newWurflUrl = URI.create((String)context.get("new_wurfl_url")).toURL();
         Validate.isTrue(newWurflUrl.getHost() != null && (newWurflUrl.getHost().endsWith(".scientiamobile.com") || newWurflUrl.getHost().equals("localhost") || newWurflUrl.getHost().equals("127.0.0.1")), "Invalid URL host: " + newWurflUrl.getHost());
         Integer connectionTimeoutMs = UpdatePipeline.getConnectionTimeoutMsOrDefault(context);
         int responseCode = UpdatePipeline.headRequest(newWurflUrl, ifModifiedSince, connectionTimeoutMs, (String)context.get("API_USER_AGENT"), this.proxySettings);
         if (responseCode == 200) {
            context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
         } else if (responseCode == 304) {
            context.put("task_result_status", UpdateResultStatus.UPDATE_SKIPPED.value());
            log.info("WURFL file is already updated to the latest version, exiting file update process");
         } else if (responseCode == 402) {
            context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
            context.put("task_error_message", "Your WURFL LICENSE EXPIRED, WURFL file will not be updated. Please  renew you license to access newer versions of WURFL file and APIs");
            log.info("WURFL license is invalid or expired, exiting update process");
         } else {
            context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
            context.put("task_error_message", "Invalid HTTP response code " + responseCode);
         }
      } catch (Exception e) {
         if (e instanceof java.net.SocketTimeoutException) {
            context.put("task_error_message", "Error trying to check if a new WURFL file is available: connection timed out");
         } else {
            context.put("task_error_message", "Error trying to check if a new WURFL file is available: " + ExceptionUtils.getFirstAvailableMessage(e));
         }

         context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
      }
   }

   static {
      (LAST_MODIFIED_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).setTimeZone(TimeZone.getTimeZone("UTC"));
   }
}
