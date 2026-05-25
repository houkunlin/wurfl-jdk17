package com.scientiamobile.wurfl.core.matchers;

final class UserAgentFallbackRule {
   final String keyword;
   final String deviceId;

   UserAgentFallbackRule(String keyword, String deviceId) {
      this.keyword = keyword;
      this.deviceId = deviceId;
   }
}

