package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.cache.CacheProvider;
import com.scientiamobile.wurfl.core.cache.DoubleLRUMapCacheProvider;
import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.matchers.MatcherManager;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequestFactoryWithPriority;
import com.scientiamobile.wurfl.core.resource.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implementation of WURFL Service  Implementation.
 */

class WURFLServiceImpl implements WURFLService {
    private static final Logger log = LoggerFactory.getLogger(WURFLServiceImpl.class);
    private final WURFLModel wurflModel;
    private ReentrantReadWriteLock modelLock;
    private volatile CacheProvider cacheProvider;
    private final MatcherManager matcherManager;
    private final DeviceProvider deviceProvider;
    private WURFLRequestFactoryWithPriority requestFactory;
    private EngineTarget engineTarget;

    public WURFLServiceImpl(WURFLModel wurflModel, MatcherManager matcherManager, DeviceProvider deviceProvider, WURFLRequestFactoryWithPriority requestFactory, EngineTarget engineTarget) {
        XmlFileLoader configFileLoader = new XmlFileLoader("classpath:/META-INF/wurfl-config.xml", new ApiConfigHandler(this, (byte) 0));
        configFileLoader.parseFile();
        this.wurflModel = wurflModel;
        this.matcherManager = matcherManager;
        this.deviceProvider = deviceProvider;
        this.requestFactory = requestFactory;
        if (engineTarget != null) {
            this.engineTarget = engineTarget;
        }
        this.modelLock = new ReentrantReadWriteLock();
        log.info("{} created", this.getClass().getSimpleName());
    }

    public WURFLServiceImpl(WURFLModel wurflModel, MatcherManager matcherManager, DeviceProvider deviceProvider, WURFLRequestFactoryWithPriority requestFactory) {
        this(wurflModel, matcherManager, deviceProvider, requestFactory, null);
    }

    static EngineTarget getEngineTarget(WURFLServiceImpl service) {
        return service.engineTarget;
    }

    static void setEngineTarget(WURFLServiceImpl service, EngineTarget engineTarget) {
        service.engineTarget = engineTarget;
    }

    @Override
/**
 * Returns the matche ranager.
 */

    public MatcherManager getMatcherManager() {
        return this.matcherManager;
    }

    @Override
/**
 * Sets the cach erovider.
 */

    public void setCacheProvider(CacheProvider cacheProvider) {
        log.info("feeding {}", cacheProvider);
        this.cacheProvider = cacheProvider;
    }

    @Override
/**
 * Returns the device.
 */

    public Device getDevice(WURFLRequest request) {
        this.modelLock.readLock().lock();
        try {
            Validate.notNull(request, "The request is null");
            this.ensureCacheProvider();
            InternalDevice internalDevice = this.cacheProvider.getDevice(request.getOriginalUserAgent());
            if (internalDevice != null) {
                return buildCachedDevice(internalDevice, request);
            }
            return matchAndCacheDevice(request);
        } finally {
            this.modelLock.readLock().unlock();
        }
    }

    /**
     * Buil dache device.
     */

    private Device buildCachedDevice(InternalDevice internalDevice, WURFLRequest request) {
        DeviceInfo deviceInfo = new DeviceInfo(internalDevice.getId(), MatchType.cached, "Cache", "Cache",
                request.getOriginalUserAgent(), "");
        return this.deviceProvider.buildDevice(internalDevice, request, deviceInfo.getMatchType(),
                deviceInfo.getMatcherName(), deviceInfo.getBucketMatcherName());
    }

    /**
     * Matc hn dach eevice.
 */

    private Device matchAndCacheDevice(WURFLRequest request) {
        request.performGenericNormalization();
        if (EngineTarget.fastDesktopBrowserMatch.equals(request.getEngineTarget())
                && request._internalIsDesktopBrowserHeavyDutyAnalysis()) {
            InternalDevice internalDevice = this.deviceProvider.getInternalDevice("generic_web_browser");
            this.cacheProvider.putDevice(request.getOriginalUserAgent(), internalDevice);
            return this.deviceProvider.buildDevice(internalDevice, request, MatchType.fastDesktopBrowser, "", "");
        }
        DeviceInfo deviceInfo = this.matcherManager.matchRequest(request);
        InternalDevice internalDevice = this.cacheProvider.getInternalDeviceFromDeviceId(deviceInfo.getId());
        if (internalDevice == null) {
            internalDevice = this.deviceProvider.getInternalDevice(deviceInfo.getId());
        }
        this.cacheProvider.putDevice(request.getOriginalUserAgent(), internalDevice);
        assert internalDevice != null;
        return this.deviceProvider.buildDevice(internalDevice, request, deviceInfo.getMatchType(),
                deviceInfo.getMatcherName(), deviceInfo.getBucketMatcherName());
    }

    @Override
/**
 * Returns the device.
 */

    public Device getDevice(HttpServletRequest request) {
        Validate.notNull(request, "The request must be not null");
        WURFLRequest wurflRequest = this.requestFactory.createRequest(request, this.engineTarget);
        return this.getDevice(wurflRequest);
    }

    @Override
/**
 * Returns the device.
 */

    public Device getDevice(String userAgent) {
        Validate.notNull(userAgent, "The userAgent must be not null");
        WURFLRequest wurflRequest = this.requestFactory.createRequest(userAgent, this.engineTarget);
        return this.getDevice(wurflRequest);
    }

    /**
     * Ensur each erovider.
 */

    private void ensureCacheProvider() {
        if (this.cacheProvider == null) {
            synchronized (this) {
                if (this.cacheProvider == null) {
                    log.info("no Cache Provider, using default (DoubleLRUMapCacheProvider)");
                    this.cacheProvider = new DoubleLRUMapCacheProvider();
                }

            }
        }
    }

    @Override
/**
 * Returns the engin earget.
 */

    public EngineTarget getEngineTarget() {
        return this.engineTarget;
    }

    @Override
/**
 * Sets the engin earget.
 */

    public void setEngineTarget(EngineTarget engineTarget) {
        if (engineTarget != EngineTarget.fastDesktopBrowserMatch) {
            this.engineTarget = EngineTarget.defaultTarget;
        } else {
            this.engineTarget = engineTarget;
        }
    }

    @Override
/**
 * Returns the use rgen triority.
 */

    public UserAgentPriority getUserAgentPriority() {
        return this.requestFactory.getUserAgentPriority();
    }

    @Override
/**
 * Sets the use rgen triority.
 */

    public void setUserAgentPriority(UserAgentPriority priority) {
        this.requestFactory.setUserAgentPriority(priority);
    }

    @Override
/**
 * Returns the devic e yd.
 */

    public Device getDeviceById(String deviceId) {
        InternalDevice internalDevice = this.deviceProvider.getInternalDevice(deviceId);
        String userAgent = resolveUserAgent(internalDevice);
        WURFLRequest request = this.requestFactory.createRequest(userAgent, this.engineTarget);
        return this.getDeviceById(deviceId, request);
    }

    /**
     * Resolv ese rgent.
 */

    private static String resolveUserAgent(InternalDevice internalDevice) {
        String userAgentFromModel = internalDevice.getWURFLUserAgent();
        if (!userAgentFromModel.startsWith("DO_NOT_MATCH")) {
            return userAgentFromModel;
        }
        ModelDevice ancestor = ((InternalDeviceImpl) internalDevice).getAncestorModelDevice();
        while (ancestor != null && ancestor.getUserAgent() != null && ancestor.getUserAgent().contains("DO_NOT_MATCH")) {
            ancestor = ancestor.getAncestor();
        }
        return ancestor != null && ancestor.getUserAgent() != null ? ancestor.getUserAgent() : "";
    }

    @Override
/**
 * Returns the devic e yd.
 */

    public Device getDeviceById(String deviceId, HttpServletRequest request) {
        Validate.notNull(request, "The request must be not null");
        WURFLRequest wurflRequest = this.requestFactory.createRequest(request, this.engineTarget);
        return this.getDeviceById(deviceId, wurflRequest);
    }

    @Override
/**
 * Returns the devic e yd.
 */

    public Device getDeviceById(String deviceId, WURFLRequest request) {
        Validate.notNull(request, "The request must be not null");
        request.performGenericNormalization();
        return this.deviceProvider.buildDevice(this.deviceProvider.getInternalDevice(deviceId), request, MatchType.none, "Utils", "Utils");
    }

    @Override
/**
 * Reload.
 */

    public void reload(WURFLResource wurflResource, WURFLResources wurflResources, String... patches) {
        this.modelLock.writeLock().lock();
        log.info("reloading service");

        try {
            this.wurflModel.reload(wurflResource, wurflResources, patches);
            this.matcherManager.reloadModel(this.wurflModel);
            this.clearCacheProvider();
        } finally {
            this.modelLock.writeLock().unlock();
        }

    }

    /**
     * Clea rach erovider.
 */

    private void clearCacheProvider() {
        log.info("about to clear cache provider");
        this.ensureCacheProvider();
        this.cacheProvider.clear();
    }

    @Override
/**
 * Appl yatches.
 */

    public void applyPatches(WURFLResources wurflResources, String... patches) {
        log.info("before applying patches {}", wurflResources);
        this.modelLock.writeLock().lock();

        try {
            this.wurflModel.applyPatches(wurflResources, patches);
            this.matcherManager.reloadModel(this.wurflModel);
            this.clearCacheProvider();
            log.info("finished applying patches {}", wurflResources);
        } finally {
            this.modelLock.writeLock().unlock();
        }

    }

    /**
     * Sets the reques tactory.
 */

    public final void setRequestFactory(WURFLRequestFactoryWithPriority requestFactory) {
        this.requestFactory = requestFactory;
    }
}
