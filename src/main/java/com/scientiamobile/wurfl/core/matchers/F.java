package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

interface F {
  boolean canHandle(WURFLRequest paramWURFLRequest);
  
  boolean a(WURFLRequest paramWURFLRequest, String paramString);
  
  G a();
  
  String getMatcherName();
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\F.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */