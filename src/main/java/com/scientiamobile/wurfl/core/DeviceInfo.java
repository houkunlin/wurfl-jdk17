package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.matchers.MatchType;

public class DeviceInfo {
   private final String id;
   private final MatchType matchType;
   private final String matcherName;
   private final String bucketMatcherName;

   public DeviceInfo(String id, MatchType matchType, String matcherName, String bucketMatcherName, String unused1, String unused2) {
      this.id = id;
      this.matchType = matchType;
      this.matcherName = matcherName;
      this.bucketMatcherName = bucketMatcherName;
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

   @Override
   public String toString() {
      StringBuilder builder;
      builder = new StringBuilder("{id='");
      builder.append(this.id).append('\'');
      builder.append(", match=").append(this.matchType);
      builder.append(", matcher=").append(this.matcherName);
      builder.append('}');
      return builder.toString();
   }
}
