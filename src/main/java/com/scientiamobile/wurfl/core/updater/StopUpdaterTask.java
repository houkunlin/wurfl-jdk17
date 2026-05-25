package com.scientiamobile.wurfl.core.updater;

import java.util.TimerTask;

public class StopUpdaterTask extends TimerTask {
   private WURFLUpdater a;

   public StopUpdaterTask(WURFLUpdater var1) {
      this.a = var1;
   }

   public void run() {
      this.a.stopPeriodicUpdate();
   }
}
