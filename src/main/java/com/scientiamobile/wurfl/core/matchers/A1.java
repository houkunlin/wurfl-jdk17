package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

interface A {
   boolean canHandle(WURFLRequest var1);

   DeviceInfo match(WURFLRequest var1);

   String normalize(String var1);

   F getFilter();

   String getMatcherName();
}
