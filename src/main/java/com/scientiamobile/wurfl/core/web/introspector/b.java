package com.scientiamobile.wurfl.core.web.introspector;

final class MatchResultRow implements Comparable {
   private String matcherName;
   private String deviceId;
   private String normalizedUserAgent;
   private String originalUserAgent;

   private MatchResultRow(String matcherName, String deviceId, String normalizedUserAgent, String originalUserAgent) {
      this.matcherName = matcherName;
      this.deviceId = deviceId;
      this.normalizedUserAgent = normalizedUserAgent;
      this.originalUserAgent = originalUserAgent;
   }

   public final String toString() {
      String var1;
      int var2;
      if ((var2 = (var1 = this.matcherName).indexOf("Matcher")) > 0) {
         var1 = var1.substring(0, var2);
      }

      return var1 + "\t" + this.deviceId + "\t" + this.normalizedUserAgent + "\t" + this.originalUserAgent;
   }

   // $FF: synthetic method
   MatchResultRow(String var1, String var2, String var3, String var4, byte var5) {
      this(var1, var2, var3, var4);
   }

   public final int compareTo(Object other) {
      MatchResultRow o = (MatchResultRow)other;
      int c = this.matcherName.compareTo(o.matcherName);
      if (c != 0) {
         return c;
      }

      c = this.deviceId.compareTo(o.deviceId);
      if (c != 0) {
         return c;
      }

      c = this.normalizedUserAgent.compareTo(o.normalizedUserAgent);
      if (c != 0) {
         return c;
      }

      return this.originalUserAgent.compareTo(o.originalUserAgent);
   }
}
