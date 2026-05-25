package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.EngineTarget;
import javax.servlet.http.HttpServletRequest;

public interface WURFLRequestFactory {
   WURFLRequest createRequest(HttpServletRequest var1, EngineTarget var2);

   WURFLRequest createRequest(String var1, EngineTarget var2);

   WURFLRequest createRequest(WURFLHeaderProvider var1, EngineTarget var2);
}
