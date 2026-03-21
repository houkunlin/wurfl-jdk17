package com.scientiamobile.wurfl.core.updater;

import java.io.File;
import java.util.Map;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class CleanupTask implements UpdatePipelineTask {
  public void execute(Map paramMap) {
    String[] arrayOfString;
    if (ArrayUtils.isNotEmpty((Object[])(arrayOfString = new String[] { (String)paramMap.get("backup_wurfl_path"), (String)paramMap.get("new_wurfl_temp_path") })))
      for (byte b = 0; b < 2; b++) {
        File file;
        String str;
        if (StringUtils.isNotEmpty(str = arrayOfString[b]) && (file = new File(str)).exists())
          file.delete(); 
      }  
    paramMap.clear();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\updater\CleanupTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */