package com.scientiamobile.wurfl.core.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceUtils {
  public static String getBuildId() {
    String str1 = getFullBuildId();
    String str2 = str1;
    if (str1 != null && !"unknown".equals(str1)) {
      int i;
      str2 = ((i = str1.indexOf(":")) != -1) ? str1.substring(i + 1) : str1;
    } 
    return str2;
  }
  
  public static String getFullBuildId() {
    String str = "unknown";
    InputStream inputStream;
    if ((inputStream = ResourceUtils.class.getResourceAsStream("/ca")) != null) {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      try {
        return ((str = bufferedReader.readLine()) != null) ? str : "unknown";
      } catch (IOException iOException) {
        return str;
      } finally {
        try {
          bufferedReader.close();
        } catch (IOException iOException) {}
      } 
    } 
    return str;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\ResourceUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */