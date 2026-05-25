package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

interface F {
   boolean canHandle(WURFLRequest var1);

   boolean a(WURFLRequest var1, String var2);

   G a();

   String getMatcherName();
}
