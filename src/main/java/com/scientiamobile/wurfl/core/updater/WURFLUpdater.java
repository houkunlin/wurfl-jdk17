package com.scientiamobile.wurfl.core.updater;

import com.scientiamobile.wurfl.core.WURFLEngine;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.updater.exc.BadWurflExtensionException;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        log.info("WURFL path passed to Updater constructor: {}", this.resolvedWurflPath);
        this.wurflEngine = wurflEngine;
        this.resolvedWurflPath = UpdatePipeline.resolvePath(wurflEngine.getRootPath());
        log.info("WURFL path passed to Updater constructor after resolve: {}", this.resolvedWurflPath);
        this.updateUrl = updateUrl;
        this.validateSetup();
    }

    public WURFLUpdater(WURFLEngine wurflEngine, String updateUrl, ProxySettings proxySettings) {
        this.frequency = Frequency.DAILY;
        log.info("WURFL path passed to Updater constructor: {}", this.resolvedWurflPath);
        this.wurflEngine = wurflEngine;
        this.resolvedWurflPath = UpdatePipeline.resolvePath(wurflEngine.getRootPath());
        log.info("WURFL path passed to Updater constructor after resolve: {}", this.resolvedWurflPath);
        this.updateUrl = updateUrl;
        this.proxySettings = proxySettings;
        this.validateSetup();
    }

    public boolean usesProxy() {
        return this.proxySettings != null;
    }

    public void setFirstExecution(Calendar firstExecution) {
        this.firstExecution = firstExecution;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public List<UpdateResult> getLastPeriodicUpdateResults() {
        return this.periodicUpdateTask != null ? this.periodicUpdateTask.getLastResults() : new ArrayList<>(0);
    }

    public void setConnectionTimeoutMs(Integer connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public void setPatches(String[] patchPaths) {
        this.patchPaths = patchPaths;
    }

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

    public synchronized void performPeriodicUpdate() {
        if (this.isPeriodicUpdateRunning()) {
            log.warn("Periodic update is already running. Shutdown the current update process before invoking this method");
        } else {
            try {
                UpdatePipeline updatePipeline;
                updatePipeline = this.usesProxy() ? new UpdatePipeline(this.resolvedWurflPath, this.updateUrl, this.proxySettings) : new UpdatePipeline(this.resolvedWurflPath, this.updateUrl);
                updatePipeline.setApiUserAgent(UserAgentUtils.createApiUserAgent(this.wurflEngine));
                updatePipeline.setConnectionTimeoutMs(this.connectionTimeoutMs);
                this.scheduler = Executors.newScheduledThreadPool(1);
                this.periodicUpdateTask = new PeriodicUpdateTask(this.wurflEngine, updatePipeline, this.resolvedWurflPath);
                this.scheduler.scheduleAtFixedRate(this.periodicUpdateTask, this.firstExecution != null ? this.firstExecution.getTimeInMillis() - Calendar.getInstance().getTimeInMillis() : 100L, this.frequency.value(), TimeUnit.MILLISECONDS);
            } catch (BadWurflExtensionException e) {
                String errorMessage = "Unable to start WURFL updater, cause: " + e.getMessage();
                log.error(errorMessage, e);
            }
        }
    }

    private boolean isPeriodicUpdateRunning() {
        return this.scheduler != null && !this.scheduler.isTerminated();
    }

    public void stopPeriodicUpdate() {
        if (this.isPeriodicUpdateRunning()) {
            this.scheduler.shutdown();
            this.scheduler = null;
        } else {
            log.warn("Cannot stop an updater that is not running. Command ignored");
        }
    }

    private void validateSetup() {
        Validator.checkFileExtensions(this.resolvedWurflPath, this.updateUrl);
        Validator.validateWritableFile(this.resolvedWurflPath);
        Validator.validateRemoteUrl(this.updateUrl, this.wurflEngine, this.proxySettings);
    }
}
