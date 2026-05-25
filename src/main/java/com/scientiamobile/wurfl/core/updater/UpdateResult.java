package com.scientiamobile.wurfl.core.updater;

public class UpdateResult {
   private UpdateResultStatus a;
   private String b;

   public UpdateResult(UpdateResultStatus var1, String var2) {
      this.a = var1;
      this.b = var2;
   }

   public boolean isUpdateProcessSuccessful() {
      return this.a == UpdateResultStatus.UPDATED || this.a == UpdateResultStatus.UPDATE_SKIPPED;
   }

   final boolean a() {
      return this.a == UpdateResultStatus.UPDATED;
   }

   public UpdateResultStatus getResultStatus() {
      return this.a;
   }

   public String getMessage() {
      return this.b;
   }

   public static UpdateResultStatus statusFromString(String var0) {
      if (UpdateResultStatus.PIPELINE_TASK_FAILED.value().equals(var0)) {
         return UpdateResultStatus.PIPELINE_TASK_FAILED;
      } else if (UpdateResultStatus.UPDATE_SKIPPED.value().equals(var0)) {
         return UpdateResultStatus.UPDATE_SKIPPED;
      } else if (UpdateResultStatus.UPDATED.value().equals(var0)) {
         return UpdateResultStatus.UPDATED;
      } else {
         return UpdateResultStatus.PIPELINE_TASK_DONE.value().equals(var0) ? UpdateResultStatus.PIPELINE_TASK_DONE : null;
      }
   }
}
