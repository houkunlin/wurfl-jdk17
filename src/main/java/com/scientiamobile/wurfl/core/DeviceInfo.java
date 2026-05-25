package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.matchers.MatchType;

public class DeviceInfo {
   private final String a;
   private final MatchType b;
   private final String c;
   private final String d;

   public DeviceInfo(String var1, MatchType var2, String var3, String var4, String var5, String var6) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public String getId() {
      return this.a;
   }

   public MatchType getMatchType() {
      return this.b;
   }

   final String a() {
      return this.c;
   }

   final String b() {
      return this.d;
   }

   public String toString() {
      StringBuilder var1;
      (var1 = new StringBuilder()).append("{id='").append(this.a).append('\'');
      var1.append(", match=").append(this.b);
      var1.append(", matcher=").append(this.c);
      var1.append('}');
      return var1.toString();
   }
}
