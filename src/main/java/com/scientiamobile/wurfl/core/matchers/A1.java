package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

interface A {
  boolean canHandle(WURFLRequest paramWURFLRequest);
  
  DeviceInfo match(WURFLRequest paramWURFLRequest);
  
  String normalize(String paramString);
  
  F getFilter();
  
  String getMatcherName();
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\A.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */