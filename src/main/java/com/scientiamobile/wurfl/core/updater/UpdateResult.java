package com.scientiamobile.wurfl.core.updater;

public class UpdateResult {
  private UpdateResultStatus a;
  
  private String b;
  
  public UpdateResult(UpdateResultStatus paramUpdateResultStatus, String paramString) {
    this.a = paramUpdateResultStatus;
    this.b = paramString;
  }
  
  public boolean isUpdateProcessSuccessful() {
    return (this.a == UpdateResultStatus.UPDATED || this.a == UpdateResultStatus.UPDATE_SKIPPED);
  }
  
  final boolean a() {
    return (this.a == UpdateResultStatus.UPDATED);
  }
  
  public UpdateResultStatus getResultStatus() {
    return this.a;
  }
  
  public String getMessage() {
    return this.b;
  }
  
  public static UpdateResultStatus statusFromString(String paramString) {
    return UpdateResultStatus.PIPELINE_TASK_FAILED.value().equals(paramString) ? UpdateResultStatus.PIPELINE_TASK_FAILED : (UpdateResultStatus.UPDATE_SKIPPED.value().equals(paramString) ? UpdateResultStatus.UPDATE_SKIPPED : (UpdateResultStatus.UPDATED.value().equals(paramString) ? UpdateResultStatus.UPDATED : (UpdateResultStatus.PIPELINE_TASK_DONE.value().equals(paramString) ? UpdateResultStatus.PIPELINE_TASK_DONE : null)));
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\updater\UpdateResult.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */