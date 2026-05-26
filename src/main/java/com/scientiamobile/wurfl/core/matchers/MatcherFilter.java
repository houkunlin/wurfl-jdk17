package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

interface MatcherFilter {
   boolean canHandle(WURFLRequest request);

   boolean recordMatch(WURFLRequest request, String deviceId);

   FilteredDeviceIndex getIndex();

   String getMatcherName();
}
