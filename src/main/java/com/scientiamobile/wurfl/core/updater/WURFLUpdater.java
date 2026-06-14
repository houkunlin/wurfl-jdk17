package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.WURFLEngine;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.updater.exc.BadWurflExtensionException;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * WURFL 更新器。
 * <p>提供 WURFL 文件的自动更新能力，支持单次手动更新和定时定期更新两种模式。
 * 通过与 {@link UpdatePipeline} 管线协作，完成检查更新、下载、备份、覆盖和校验
 * 的完整流程。定期更新使用 {@link java.util.concurrent.ScheduledExecutorService}
 * 按配置的频率周期性执行。</p>
 */

public class WURFLUpdater {
    private static final Logger log = LoggerFactory.getLogger(WURFLUpdater.class);
    private String updateUrl;
    private WURFLEngine wurflEngine;
    private Frequency frequency;
    private PeriodicUpdateTask periodicUpdateTask;
    private ScheduledExecutorService scheduler;
    private Integer connectionTimeoutMs;
    private String resolvedWurflPath;
    private String[] patchPaths;
    private Calendar firstExecution;
    private ProxySettings proxySettings;

    public WURFLUpdater(WURFLEngine wurflEngine, String updateUrl) {
        this.frequency = Frequency.DAILY;
        this.wurflEngine = wurflEngine;
        this.resolvedWurflPath = UpdatePipeline.resolvePath(wurflEngine.getRootPath());
        this.updateUrl = updateUrl;
        log.info("WURFL path passed to Updater constructor: {}", this.resolvedWurflPath);
        this.validateSetup();
    }

    public WURFLUpdater(WURFLEngine wurflEngine, String updateUrl, ProxySettings proxySettings) {
        this.frequency = Frequency.DAILY;
        this.wurflEngine = wurflEngine;
        this.resolvedWurflPath = UpdatePipeline.resolvePath(wurflEngine.getRootPath());
        this.updateUrl = updateUrl;
        this.proxySettings = proxySettings;
        log.info("WURFL path passed to Updater constructor: {}", this.resolvedWurflPath);
        this.validateSetup();
    }

    /**
     * Use sroxy.
     */

    public boolean usesProxy() {
        return this.proxySettings != null;
    }

    public void setFirstExecution(Calendar firstExecution) {
        this.firstExecution = firstExecution;
    }

    /**
     * Sets the frequency.
     */

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public List<UpdateResult> getLastPeriodicUpdateResults() {
        return this.periodicUpdateTask != null ? this.periodicUpdateTask.getLastResults() : Collections.emptyList();
    }

    /**
     * 设置连接超时时间。
     *
     * @param connectionTimeoutMs 连接超时毫秒数
     */

    public void setConnectionTimeoutMs(Integer connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public void setPatches(String[] patchPaths) {
        this.patchPaths = patchPaths;
    }

    /**
     * 执行一次手动 WURFL 更新。
     * <p>构造更新管线并同步执行。如果更新成功并且 WURFL 文件确实发生了变化，
     * 则重新加载 WURFL 引擎以使新数据立即生效。</p>
     *
     * @return 更新结果，包含状态和描述信息
     */
    public synchronized UpdateResult performUpdate() {
        UpdateResult updateResult;
        try {
            UpdatePipeline updatePipeline;
            updatePipeline = this.usesProxy() ? new UpdatePipeline(this.resolvedWurflPath, this.updateUrl, this.proxySettings) : new UpdatePipeline(this.resolvedWurflPath, this.updateUrl);
            updatePipeline.setApiUserAgent(UserAgentUtils.createApiUserAgent(this.wurflEngine));
            updatePipeline.setConnectionTimeoutMs(this.connectionTimeoutMs);
            updateResult = updatePipeline.execute();
            if (!updateResult.isUpdateProcessSuccessful()) {
                log.warn(updateResult.getMessage());
            } else if (updateResult.isUpdated()) {
                if (ArrayUtils.isEmpty(this.patchPaths)) {
                    this.wurflEngine.reload(this.resolvedWurflPath);
                } else {
                    this.wurflEngine.reload(this.resolvedWurflPath, this.patchPaths);
                }
            }
        } catch (WURFLRuntimeException e) {
            String errorMessage = "Unable to start WURFL updater, cause: " + e.getMessage();
            log.error(errorMessage, e);
            updateResult = new UpdateResult(UpdateResultStatus.PIPELINE_TASK_FAILED, errorMessage);
        }

        return updateResult;
    }

    /**
     * 启动定期更新调度器。
     * <p>创建 {@link ScheduledExecutorService} 线程池，按照配置的更新频率
     * 定期执行 {@link PeriodicUpdateTask}。如果已有定期更新正在运行，则忽略本次请求。</p>
     */

    public synchronized void performPeriodicUpdate() {
        if (this.isPeriodicUpdateRunning()) {
            log.warn("Periodic update is already running. Shutdown the current update process before invoking this method");
        } else {
            try {
                UpdatePipeline updatePipeline;
                updatePipeline = this.usesProxy() ? new UpdatePipeline(this.resolvedWurflPath, this.updateUrl, this.proxySettings) : new UpdatePipeline(this.resolvedWurflPath, this.updateUrl);
                updatePipeline.setApiUserAgent(UserAgentUtils.createApiUserAgent(this.wurflEngine));
                updatePipeline.setConnectionTimeoutMs(this.connectionTimeoutMs);
                this.scheduler = Executors.newScheduledThreadPool(1, r -> {
                    Thread t = new Thread(r, "wurfl-periodic-update");
                    t.setDaemon(true);
                    return t;
                });
                this.periodicUpdateTask = new PeriodicUpdateTask(this.wurflEngine, updatePipeline, this.resolvedWurflPath);
                this.scheduler.scheduleAtFixedRate(this.periodicUpdateTask, this.firstExecution != null ? this.firstExecution.getTimeInMillis() - Calendar.getInstance().getTimeInMillis() : 100L, this.frequency.value(), TimeUnit.MILLISECONDS);
            } catch (BadWurflExtensionException e) {
                String errorMessage = "Unable to start WURFL updater, cause: " + e.getMessage();
                log.error(errorMessage, e);
            }
        }
    }

    /**
     * 检查定期更新是否正在运行。
     *
     * @return 如果调度器存在且未终止则返回 {@code true}
     */
    private boolean isPeriodicUpdateRunning() {
        return this.scheduler != null && !this.scheduler.isTerminated();
    }

    public synchronized void stopPeriodicUpdate() {
        if (this.isPeriodicUpdateRunning()) {
            this.scheduler.shutdown();
            this.scheduler = null;
        } else {
            log.warn("Cannot stop an updater that is not running. Command ignored");
        }
    }

    /**
     * 验证 WURFL 更新的初始配置。
     * <p>依次检查：</p>
     * <ul>
     *   <li>文件扩展名是否为 .zip 或 .gz</li>
     *   <li>本地文件是否可写</li>
     *   <li>远程 URL 是否可达且有效</li>
     * </ul>
     */
    private void validateSetup() {
        Validator.checkFileExtensions(this.resolvedWurflPath, this.updateUrl);
        Validator.validateWritableFile(this.resolvedWurflPath);
        Validator.validateRemoteUrl(this.updateUrl, this.wurflEngine, this.proxySettings);
    }
}
