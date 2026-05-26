package com.scientiamobile.wurfl.core.updater;

import java.io.File;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class CleanupTask implements UpdatePipelineTask {
   public void execute(Map<String, Object> context) {
      String[] pathsToDelete;
      if (ArrayUtils.isNotEmpty(pathsToDelete = new String[]{(String)context.get("backup_wurfl_path"), (String)context.get("new_wurfl_temp_path")})) {
         for(int i = 0; i < 2; ++i) {
            String path;
            File file;
            if (StringUtils.isNotEmpty(path = pathsToDelete[i]) && (file = new File(path)).exists()) {
               file.delete();
            }
         }
      }

      context.clear();
   }
}
