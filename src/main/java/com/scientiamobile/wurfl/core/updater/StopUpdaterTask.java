package com.scientiamobile.wurfl.core.updater;

import java.util.TimerTask;

public class StopUpdaterTask extends TimerTask {
    private WURFLUpdater wurflUpdater;

    public StopUpdaterTask(WURFLUpdater wurflUpdater) {
        this.wurflUpdater = wurflUpdater;
    }

    @Override
    public void run() {
        this.wurflUpdater.stopPeriodicUpdate();
    }
}
