package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

interface Matcher {
   boolean canHandle(WURFLRequest request);

   DeviceInfo match(WURFLRequest request);

   String normalize(String userAgent);

   MatcherFilter getFilter();

   String getMatcherName();
}
