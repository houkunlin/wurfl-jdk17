package com.scientiamobile.wurfl.core.web.introspector;

final class MatchResultRow implements Comparable<MatchResultRow> {
   private final String matcherName;
   private final String deviceId;
   private final String normalizedUserAgent;
   private final String originalUserAgent;

   MatchResultRow(String matcherName, String deviceId, String normalizedUserAgent, String originalUserAgent) {
      this.matcherName = matcherName;
      this.deviceId = deviceId;
      this.normalizedUserAgent = normalizedUserAgent;
      this.originalUserAgent = originalUserAgent;
   }

   @Override
   public String toString() {
      String matcherNameWithoutSuffix = this.matcherName;
      int matcherSuffixIndex;
      matcherSuffixIndex = matcherNameWithoutSuffix.indexOf("Matcher");
      if (matcherSuffixIndex > 0) {
         matcherNameWithoutSuffix = matcherNameWithoutSuffix.substring(0, matcherSuffixIndex);
      }

      return matcherNameWithoutSuffix + "\t" + this.deviceId + "\t" + this.normalizedUserAgent + "\t" + this.originalUserAgent;
   }

   public final int compareTo(MatchResultRow other) {
      int c = this.matcherName.compareTo(other.matcherName);
      if (c != 0) {
         return c;
      }

      c = this.deviceId.compareTo(other.deviceId);
      if (c != 0) {
         return c;
      }

      c = this.normalizedUserAgent.compareTo(other.normalizedUserAgent);
      if (c != 0) {
         return c;
      }

      return this.originalUserAgent.compareTo(other.originalUserAgent);
   }
}
