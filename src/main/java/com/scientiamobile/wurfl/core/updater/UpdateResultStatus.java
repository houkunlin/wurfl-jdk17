package com.scientiamobile.wurfl.core.updater;

public enum UpdateResultStatus {
  PIPELINE_TASK_DONE("TASK_DONE"),
  PIPELINE_TASK_FAILED("TASK_FAILED"),
  UPDATED("UPDATED"),
  UPDATE_SKIPPED("UPDATE_SKIPPED");
  
  private String a;
  
  UpdateResultStatus(String paramString1) {
    this.a = paramString1;
  }
  
  public final String value() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\updater\UpdateResultStatus.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */