package com.scientiamobile.wurfl.core.request;

import java.util.Enumeration;

public interface WURFLHeaderProvider {
  String getHeader(String paramString);
  
  Enumeration getHeaderNames();
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\WURFLHeaderProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */