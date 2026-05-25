package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import java.io.File;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RollbackTask implements UpdatePipelineTask {
   private final Logger a = LoggerFactory.getLogger(this.getClass());

   public void execute(Map var1) {
      this.a.warn("Starting WURFL update rollback task");
      String var2 = (String)var1.get("original_wurfl_overwritten");
      if ("true".equals(var2)) {
         var2 = (String)var1.get("backup_wurfl_path");
         String var3 = (String)var1.get("original_wurfl_path");

         try {
            FileUtils.copyFile(new File(var2), new File(var3));
            this.a.info("Update rollback: restored file " + var3);
            FileUtils.deleteQuietly(new File((String)var1.get("new_wurfl_temp_path")));
            FileUtils.deleteQuietly(new File((String)var1.get("backup_wurfl_path")));
            var1.put("task_result_status", UpdateResultStatus.PIPELINE_TASK_DONE.value());
         } catch (Throwable var4) {
            var2 = "An error occurred while performing WURFL update rollback task";
            this.a.error(var2, var4);
            throw new WURFLRuntimeException(var2, var4);
         }
      }
   }
}
