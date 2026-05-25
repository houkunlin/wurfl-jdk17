package com.scientiamobile.wurfl.core.updater;

public enum UpdateResultStatus {
   PIPELINE_TASK_DONE("TASK_DONE"),
   PIPELINE_TASK_FAILED("TASK_FAILED"),
   UPDATED("UPDATED"),
   UPDATE_SKIPPED("UPDATE_SKIPPED");

   private String a;

   private UpdateResultStatus(String var3) {
      this.a = var3;
   }

   public final String value() {
      return this.a;
   }
}
