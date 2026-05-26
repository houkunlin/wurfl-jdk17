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
   private final Logger a = LoggerFactory.getLogger(this.getClass());
   private String[] b;

   public OverwriteAndCheckConsistencyTask() {
   }

   public OverwriteAndCheckConsistencyTask(String[] var1) {
      this.b = var1;
   }

   public void execute(Map<String, Object> var1) {
      String var2;
      Validate.notEmpty(var2 = (String)var1.get("new_wurfl_temp_path"));
      String var3 = (String)var1.get("original_wurfl_path");

      try {
         (new File(var3)).delete();
         FileUtils.copyFile(new File(var2), new File(var3), true);
         var1.put("original_wurfl_overwritten", "true");
         GeneralWURFLEngine var5;
         if (ArrayUtils.isEmpty(this.b)) {
            var5 = new GeneralWURFLEngine(var3);
         } else {
            var5 = new GeneralWURFLEngine(var3, this.b);
         }

         var5.load();
         var1.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
      } catch (Throwable var4) {
         this.a.error("WURFL consistency check failed", var4);
         var1.put("task_error_message", "Error trying to overwrite WURFL file : " + ExceptionUtils.getFirstAvailableMessage(var4));
         var1.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
      }
   }
}
