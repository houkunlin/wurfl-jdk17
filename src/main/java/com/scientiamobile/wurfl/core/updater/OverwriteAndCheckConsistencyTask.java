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
   private final Logger log = LoggerFactory.getLogger(this.getClass());
   private String[] patchPaths;

   public OverwriteAndCheckConsistencyTask() {
   }

   public OverwriteAndCheckConsistencyTask(String[] patchPaths) {
      this.patchPaths = patchPaths;
   }

   public void execute(Map<String, Object> context) {
      String newWurflTempPath;
      Validate.notEmpty(newWurflTempPath = (String)context.get("new_wurfl_temp_path"));
      String originalWurflPath = (String)context.get("original_wurfl_path");

      try {
         (new File(originalWurflPath).getCanonicalFile()).delete();
         FileUtils.copyFile(new File(newWurflTempPath).getCanonicalFile(), new File(originalWurflPath).getCanonicalFile(), true);
         context.put("original_wurfl_overwritten", "true");
         GeneralWURFLEngine newEngine;
         if (ArrayUtils.isEmpty(this.patchPaths)) {
            newEngine = new GeneralWURFLEngine(originalWurflPath);
         } else {
            newEngine = new GeneralWURFLEngine(originalWurflPath, this.patchPaths);
         }

         newEngine.load();
         context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
      } catch (Throwable e) {
         this.log.error("WURFL consistency check failed", e);
         context.put("task_error_message", "Error trying to overwrite WURFL file : " + ExceptionUtils.getFirstAvailableMessage(e));
         context.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_FAILED.value());
      }
   }
}
