package com.scientiamobile.wurfl.core.updater;

import java.io.File;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class CleanupTask implements UpdatePipelineTask {
   public void execute(Map<String, Object> var1) {
      String[] var2;
      if (ArrayUtils.isNotEmpty(var2 = new String[]{(String)var1.get("backup_wurfl_path"), (String)var1.get("new_wurfl_temp_path")})) {
         for(int var3 = 0; var3 < 2; ++var3) {
            String var4;
            File var5;
            if (StringUtils.isNotEmpty(var4 = var2[var3]) && (var5 = new File(var4)).exists()) {
               var5.delete();
            }
         }
      }

      var1.clear();
   }
}
