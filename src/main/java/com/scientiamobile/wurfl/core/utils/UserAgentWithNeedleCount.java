package com.scientiamobile.wurfl.core.utils;

public class UserAgentWithNeedleCount {
   private String asciiPrintableUserAgent;
   private int plusCharCount;
   private int percentageCharCount;
   private boolean hasSpaceChars;

   UserAgentWithNeedleCount(String asciiPrintableUserAgent, int plusCharCount, int percentageCharCount, boolean hasSpaceChars) {
      this.asciiPrintableUserAgent = asciiPrintableUserAgent;
      this.plusCharCount = plusCharCount;
      this.percentageCharCount = percentageCharCount;
      this.hasSpaceChars = hasSpaceChars;
   }

   public String getAsciiPrintableUserAgent() {
      return this.asciiPrintableUserAgent;
   }

   public int getPlusCharCount() {
      return this.plusCharCount;
   }

   public int getPercentageCharCount() {
      return this.percentageCharCount;
   }

   public boolean hasSpaceChars() {
      return this.hasSpaceChars;
   }
}
