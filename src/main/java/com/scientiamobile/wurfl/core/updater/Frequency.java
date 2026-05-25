package com.scientiamobile.wurfl.core.updater;

public enum Frequency {
   MINUTES(60000),
   DAILY(86400000),
   THREE_DAYS(259200000),
   WEEKLY(604800000);

   private long a;

   private Frequency(int var3) {
      this.a = (long)var3;
   }

   public final long value() {
      return this.a;
   }
}
