package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.apache.commons.lang.StringUtils;

final class PhilipsMatcher extends a {
   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringUtils.startsWithIgnoreCase(var1.getCleanedDeviceUserAgent(), "philips");
   }

   public final String getMatcherName() {
      return "PhilipsMatcher";
   }

   public final String getBucketMatcherName() {
      return "Philips";
   }
}

