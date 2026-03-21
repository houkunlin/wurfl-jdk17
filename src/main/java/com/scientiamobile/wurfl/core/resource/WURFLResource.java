package com.scientiamobile.wurfl.core.resource;

public interface WURFLResource {
  c getData(String... paramVarArgs);
  
  String getInfo();
  
  String getVersion();
  
  void release();
  
  String getOriginalPath();
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\WURFLResource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */