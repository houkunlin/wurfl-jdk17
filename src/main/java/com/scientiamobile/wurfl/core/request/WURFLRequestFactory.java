package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.EngineTarget;
import javax.servlet.http.HttpServletRequest;

public interface WURFLRequestFactory {
  WURFLRequest createRequest(HttpServletRequest paramHttpServletRequest, EngineTarget paramEngineTarget);
  
  WURFLRequest createRequest(String paramString, EngineTarget paramEngineTarget);
  
  WURFLRequest createRequest(WURFLHeaderProvider paramWURFLHeaderProvider, EngineTarget paramEngineTarget);
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\WURFLRequestFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */