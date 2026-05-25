package com.scientiamobile.wurfl.core.utils;

public class UserAgentWithNeedleCount {
   private String a;
   private int b;
   private int c;
   private boolean d;

   UserAgentWithNeedleCount(String var1, int var2, int var3, boolean var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public String getAsciiPrintableUserAgent() {
      return this.a;
   }

   public int getPlusCharCount() {
      return this.b;
   }

   public int getPercentageCharCount() {
      return this.c;
   }

   public boolean hasSpaceChars() {
      return this.d;
   }
}
