package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.slf4j.LoggerFactory;

final class DefaultMatcherFilter implements MatcherFilter {
   private Matcher matcher;
   private FilteredDeviceIndex index;

   public DefaultMatcherFilter(Matcher matcher) {
      LoggerFactory.getLogger(this.getClass());
      this.matcher = matcher;
      this.index = new FilteredDeviceIndex(this);
   }

   @Override
   public final boolean canHandle(WURFLRequest request) {
      return this.matcher.canHandle(request);
   }

   public final boolean recordMatch(WURFLRequest request, String deviceId) {
      this.index.put(this.matcher.normalize(request.getCleanedDeviceUserAgent()), deviceId);
      return true;
   }

   public final FilteredDeviceIndex getIndex() {
      return this.index;
   }

   @Override
   public final String getMatcherName() {
      return this.matcher.getMatcherName();
   }
}

