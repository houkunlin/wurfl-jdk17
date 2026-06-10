package com.scientiamobile.wurfl.core.updater;

import java.util.TimerTask;

/**
 * A task that performs Stop Updater.
 */

public class StopUpdaterTask extends TimerTask {
    private WURFLUpdater wurflUpdater;

    public StopUpdaterTask(WURFLUpdater wurflUpdater) {
        this.wurflUpdater = wurflUpdater;
    }

    @Override
/**
 * Executes this task.
 */

    public void run() {
        this.wurflUpdater.stopPeriodicUpdate();
    }
}
