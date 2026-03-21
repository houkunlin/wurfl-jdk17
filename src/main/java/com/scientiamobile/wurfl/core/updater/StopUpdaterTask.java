package com.scientiamobile.wurfl.core.updater;

import java.util.TimerTask;

public class StopUpdaterTask extends TimerTask {
  private WURFLUpdater a;
  
  public StopUpdaterTask(WURFLUpdater paramWURFLUpdater) {
    this.a = paramWURFLUpdater;
  }
  
  public void run() {
    this.a.stopPeriodicUpdate();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\updater\StopUpdaterTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */