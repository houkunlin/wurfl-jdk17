package com.scientiamobile.wurfl.core.updater;

public enum Frequency {
  MINUTES(60000),
  DAILY(86400000),
  THREE_DAYS(259200000),
  WEEKLY(604800000);
  
  private long a;
  
  Frequency(int paramInt1) {
    this.a = paramInt1;
  }
  
  public final long value() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\updater\Frequency.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */