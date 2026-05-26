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
   private final Logger a = LoggerFactory.getLogger(this.getClass());
   private ProxySettings b;

   public NewWurflFileDownloadTask() {
   }

   public NewWurflFileDownloadTask(ProxySettings var1) {
      this.b = var1;
   }

   public void execute(Map<String, Object> var1) {
      try {
         String var2 = (String)var1.get("original_wurfl_path") + ".wtmp";
         Integer var3 = UpdatePipeline.safeGetConnectionTimeout(var1);
         URL var10001 = URI.create((String)var1.get("new_wurfl_url")).toURL();
         int var5 = var3;
         URL var4 = var10001;
         HttpsURLConnection var7;
         (var7 = this.b != null ? (HttpsURLConnection)var4.openConnection(this.b.getProxy()) : (HttpsURLConnection)var4.openConnection()).setRequestMethod("GET");
         var7.setUseCaches(false);
         var7.setConnectTimeout(var5);
         var7.setReadTimeout(var5);
         var7.connect();
         int var8;
         if ((var8 = var7.getResponseCode()) == 200) {
            File var9;
            if (!(var9 = new File(var2)).exists()) {
               var9.createNewFile();
            }

            FileUtils.copyInputStreamToFile(var7.getInputStream(), var9);
            var9.setLastModified(var7.getLastModified());
            this.a.info("WURFL updater: new WURFL file download completed");
            var1.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
            var1.put("new_wurfl_temp_path", var2);
         } else {
            this.a.error("Wurfl updater: unable to download new WURFL file, HTTP RESPONSE code: " + var8);
            var1.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
            var1.put("task_error_message", "Invalid HTTP response code " + var8);
         }
      } catch (Exception var6) {
         var1.put("task_error_message", "Error trying to check if a new WURFL file is available: " + ExceptionUtils.getFirstAvailableMessage(var6));
         var1.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
      }
   }
}
