package com.scientiamobile.wurfl.core.updater;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CleanupTask implements UpdatePipelineTask {
   private final Logger log = LoggerFactory.getLogger(this.getClass());

   public void execute(Map<String, Object> context) {
      String[] pathsToDelete;
      if (ArrayUtils.isNotEmpty(pathsToDelete = new String[]{(String)context.get("backup_wurfl_path"), (String)context.get("new_wurfl_temp_path")})) {
         for(int i = 0; i < 2; ++i) {
            String path;
            if (StringUtils.isNotEmpty(path = pathsToDelete[i])) {
               try {
                  File file = new File(path).getCanonicalFile();
                  if (file.exists() && !file.delete()) {
                     this.log.warn("Failed to delete file: {}", file.getAbsolutePath());
                  }
               } catch (IOException e) {
               }
            }
         }
      }

      context.clear();
   }
}
