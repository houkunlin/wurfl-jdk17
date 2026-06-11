package com.scientiamobile.wurfl.core.updater;

import java.util.TimerTask;

/**
 * 用于停止 WURFL 定期更新的定时任务。
 * <p>继承 {@link TimerTask}，可在指定时间触发后调用
 * {@link WURFLUpdater#stopPeriodicUpdate()} 停止正在运行的定期更新调度器。</p>
 */

public class StopUpdaterTask extends TimerTask {
    private WURFLUpdater wurflUpdater;

    public StopUpdaterTask(WURFLUpdater wurflUpdater) {
        this.wurflUpdater = wurflUpdater;
    }
    /**
     * 执行停止更新操作。
     * <p>委托给 {@link WURFLUpdater#stopPeriodicUpdate()} 关闭调度器线程池。</p>
     */
    @Override
    public void run() {
        this.wurflUpdater.stopPeriodicUpdate();
    }
}
