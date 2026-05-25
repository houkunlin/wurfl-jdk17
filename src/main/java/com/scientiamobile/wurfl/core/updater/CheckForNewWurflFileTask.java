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
   private final Logger b = LoggerFactory.getLogger(this.getClass());
   static final SimpleDateFormat a;
   private ProxySettings c;

   public CheckForNewWurflFileTask() {
   }

   public CheckForNewWurflFileTask(ProxySettings var1) {
      this.c = var1;
   }

   public void execute(Map var1) {
      String var2 = (String)var1.get("original_wurfl_path");
      File var11;
      String var10000;
      if ((var11 = new File(var2)).exists()) {
         Date var12 = new Date(var11.lastModified());
         var10000 = a.format(var12);
      } else {
         var10000 = "";
      }

      String var13 = var10000;

      try {
         try {
            URL var3 = new URL((String)var1.get("new_wurfl_url"));
            Integer var4 = UpdatePipeline.safeGetConnectionTimeout(var1);
            int var14;
            if ((var14 = UpdatePipeline.a(var3, var13, var4, (String)var1.get("API_USER_AGENT"), this.c)) == 200) {
               var1.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
            } else if (var14 == 304) {
               var1.put("task_result_status", UpdateResultStatus.UPDATE_SKIPPED.value());
               this.b.info("WURFL file is already updated to the latest version, exiting file update process");
            } else if (var14 == 402) {
               var1.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
               var1.put("task_error_message", "Your WURFL LICENSE EXPIRED, WURFL file will not be updated. Please  renew you license to access newer versions of WURFL file and APIs");
               this.b.info("WURFL license is invalid or expired, exiting update process");
            } else {
               var1.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
               var1.put("task_error_message", "Invalid HTTP response code " + var14);
            }
         } catch (SocketTimeoutException var8) {
            var1.put("task_error_message", "Error trying to check if a new WURFL file is available: connection timed out");
            var1.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
         } catch (Exception var9) {
            var1.put("task_error_message", "Error trying to check if a new WURFL file is available: " + ExceptionUtils.getFirstAvailableMessage(var9));
            var1.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
         }
      } catch (Throwable var10) {
         throw var10;
      }
   }

   static {
      (a = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).setTimeZone(TimeZone.getTimeZone("UTC"));
   }
}
