package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.WURFLEngine;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * 定期 WURFL 更新执行任务。
 * <p>实现 {@link Runnable} 接口，由调度器定期触发执行。
 * 执行时委托 {@link UpdatePipeline} 完成更新流程，然后根据更新结果
 * 决定是否重新加载 WURFL 引擎。维护最近 10 次更新的历史记录。</p>
 */

public class PeriodicUpdateTask implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(PeriodicUpdateTask.class);
    private final LinkedList<UpdateResult> lastResults = new LinkedList<>();
    private UpdatePipeline updatePipeline;
    private Calendar lastSuccessfulUpdate;
    private WURFLEngine wurflEngine;
    private String resolvedWurflPath;
    private String[] patchPaths;

    public PeriodicUpdateTask(WURFLEngine wurflEngine, UpdatePipeline updatePipeline, String resolvedWurflPath) {
        this.updatePipeline = updatePipeline;
        this.resolvedWurflPath = resolvedWurflPath;
        this.wurflEngine = wurflEngine;
    }

    /**
     * Sets the patc haths.
     */

    public void setPatchPaths(String[] patchPaths) {
        this.patchPaths = patchPaths;
    }

    /**
     * Executes this task.
     */
    @Override
    public void run() {
        log.info("WURFL periodic update started");

        try {
            UpdateResult updateResult = this.updatePipeline.execute();
            if (this.lastResults.size() >= 10) {
                this.lastResults.poll();
            }

            this.lastResults.add(updateResult);
            if (!updateResult.isUpdateProcessSuccessful()) {
                log.error("Update process failed. Reason: {}", updateResult.getMessage());
                if (this.lastSuccessfulUpdate != null) {
                    log.warn("Last successful updated was completed on {}", CheckForNewWurflFileTask.LAST_MODIFIED_FORMAT.get().format(this.lastSuccessfulUpdate.getTime()));
                }
            } else if (updateResult.isUpdated()) {
                log.info("Free memory before reload process {}", Runtime.getRuntime().freeMemory());
                if (ArrayUtils.isEmpty(this.patchPaths)) {
                    this.wurflEngine.reload(this.resolvedWurflPath);
                } else {
                    this.wurflEngine.reload(this.resolvedWurflPath, this.patchPaths);
                }

                this.lastSuccessfulUpdate = Calendar.getInstance();
            }

            if (this.lastSuccessfulUpdate != null) {
                log.info("WURFL file update completed on {}", CheckForNewWurflFileTask.LAST_MODIFIED_FORMAT.get().format(this.lastSuccessfulUpdate.getTime()));
            }

        } catch (RuntimeException e) {
            log.error("Unexpected exception performing periodic update", e);
        }
    }

    /**
     * 获取最近多次周期性更新的结果列表。
     *
     * @return 最近更新结果的只读列表（最多 10 条）
     */
    public List<UpdateResult> getLastResults() {
        return this.lastResults;
    }
}
