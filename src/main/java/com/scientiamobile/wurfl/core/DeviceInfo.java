package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.matchers.MatchType;

public class DeviceInfo {
   private final String id;
   private final MatchType matchType;
   private final String matcherName;
   private final String bucketMatcherName;

   public DeviceInfo(String var1, MatchType var2, String var3, String var4, String var5, String var6) {
      this.id = var1;
      this.matchType = var2;
      this.matcherName = var3;
      this.bucketMatcherName = var4;
   }

   public String getId() {
      return this.id;
   }

   public MatchType getMatchType() {
      return this.matchType;
   }

   public String getMatcherName() {
      return this.matcherName;
   }

   public String getBucketMatcherName() {
      return this.bucketMatcherName;
   }

   final String a() {
      return this.getMatcherName();
   }

   final String b() {
      return this.getBucketMatcherName();
   }

   public String toString() {
      StringBuilder var1;
      (var1 = new StringBuilder()).append("{id='").append(this.id).append('\'');
      var1.append(", match=").append(this.matchType);
      var1.append(", matcher=").append(this.matcherName);
      var1.append('}');
      return var1.toString();
   }
}
