package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.cache.CacheProvider;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.matchers.MatcherManager;
import com.scientiamobile.wurfl.core.request.*;
import com.scientiamobile.wurfl.core.resource.*;
import com.scientiamobile.wurfl.core.vcap.VirtualCapabilityEvaluator;
import com.scientiamobile.wurfl.core.vcap.VirtualCapabilityHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implementation of General WURFL Engine.
 */

public class GeneralWURFLEngine implements WURFLEngine {
    private static final Logger log = LoggerFactory.getLogger(GeneralWURFLEngine.class);
    private static final List<String> ALWAYS_INCLUDED_CAPABILITIES = Arrays.asList("device_os", "device_os_version", "is_tablet", "is_wireless_device", "pointing_method", "preferred_markup", "resolution_height", "resolution_width", "ux_full_desktop", "xhtml_support_level", "is_smarttv", "can_assign_phone_number", "brand_name", "model_name", "marketing_name", "mobile_browser_version");
    private final ReadWriteLock lock;
    private final Object initLock;
    private String[] capabilityFilter;
    private WURFLResource rootResource;
    private WURFLResources patchResources;
    private String rootPath;
    private MarkupResolver markupResolver;
    private CapabilitiesHolderFactory capabilitiesHolderFactory;
    private DeviceProvider deviceProvider;
    private CacheProvider cacheProvider;
    private WURFLService wurflService;
    private UserAgentResolver userAgentResolver;
    private WURFLUtils wurflUtils;
    private WURFLModel wurflModel;
    private volatile boolean initialized;
    private WURFLRequestFactoryWithPriority requestFactory;
    private EngineTarget engineTarget;
    private UserAgentPriority userAgentPriority;

    public GeneralWURFLEngine(String rootPath) {
        this(new XMLResource(rootPath));
        this.rootPath = rootPath;
    }

    public GeneralWURFLEngine(WURFLResource rootResource) {
        this(rootResource, (WURFLResources) null);
    }

    public GeneralWURFLEngine(String rootPath, String... patchPaths) {
        this(createXmlResource(rootPath), createXmlResources(patchPaths));
        this.rootPath = rootPath;
    }

    public GeneralWURFLEngine(WURFLResource rootResource, WURFLResource... patchResources) {
        this(rootResource, new WURFLResources(patchResources));
    }

    public GeneralWURFLEngine(WURFLResource rootResource, WURFLResources patchResources) {
        this.capabilityFilter = null;
        this.lock = new ReentrantReadWriteLock();
        this.initLock = new Object();
        this.initialized = false;
        this.userAgentPriority = UserAgentPriority.OverrideSideloadedBrowserUserAgent;
        Validate.notNull(rootResource, "The root resource is null");
        this.rootResource = rootResource;
        this.rootPath = rootResource.getOriginalPath();
        this.patchResources = patchResources;
    }

    /**
     * Creat em lesource.
     */

    private static WURFLResource createXmlResource(String path) {
        Validate.notEmpty(path, "The path is null");
        return new XMLResource(path);
    }

    /**
     * Creat em lesources.
 */

    private static WURFLResources createXmlResources(String[] paths) {
        WURFLResources resources = new WURFLResources();

        for (int i = 0; paths != null && i < paths.length; ++i) {
            resources.add(new XMLResource(paths[i]));
        }

        return resources;
    }

    @Override
/**
 * Reload.
 */

    public void reload(String rootPath) {
        WURFLResource wurflResource = createXmlResource(rootPath);
        this.reload(wurflResource, (WURFLResources) null);
    }

    @Override
/**
 * Appl yatches.
 */

    public void applyPatches(String... patchPaths) {
        if (patchPaths == null) {
            log.warn("null patches, do nothing...");
        } else {
            this.applyPatches(createXmlResources(patchPaths));
        }
    }

    @Override
/**
 * Appl yatches.
 */

    public void applyPatches(WURFLResource... patchResources) {
        this.applyPatches(new WURFLResources(patchResources));
    }

    @Override
/**
 * Appl yatches.
 */

    public void applyPatches(WURFLResources patchResources) {
        if (patchResources == null) {
            log.warn("null patches, do nothing...");
        } else {
            this.lock.writeLock().lock();

            try {
                this.ensureInitialized();
                this.wurflService.applyPatches(patchResources, this.capabilityFilter);
            } finally {
                this.lock.writeLock().unlock();
            }

        }
    }

    @Override
/**
 * Reload.
 */

    public void reload(String rootPath, String[] patchPaths) {
        WURFLResource wurflResource = createXmlResource(rootPath);
        WURFLResources wurflResources = createXmlResources(patchPaths);
        this.reload(wurflResource, wurflResources);
    }

    @Override
/**
 * Reload.
 */

    public void reload(WURFLResource rootResource, WURFLResource... patchResources) {
        this.reload(rootResource, new WURFLResources(patchResources));
    }

    @Override
/**
 * Reload.
 */

    public void reload(WURFLResource rootResource, WURFLResources patchResources) {
        this.rootPath = rootResource.getOriginalPath();
        this.ensureInitialized();
        if (patchResources == null) {
            patchResources = new WURFLResources();
        }

        this.lock.writeLock().lock();

        try {
            this.wurflService.reload(rootResource, patchResources, this.capabilityFilter);
        } finally {
            this.lock.writeLock().unlock();
        }

    }

    @Override
/**
 * Replac eoot.
 */

    public boolean replaceRoot(String newRootPath) {
        try {
            if (StringUtils.isBlank(newRootPath)) {
                log.warn("Empty value has been provided for replacing root, skipping");
                return false;
            } else if (!(new File(this.rootPath).getCanonicalFile()).canWrite()) {
                log.error("Engine root at {} is not writable, cannot replace it", this.rootPath);
                return false;
            } else {
                GeneralWURFLEngine newEngine;
                if (this.patchResources != null && this.patchResources.size() != 0) {
                    newEngine = new GeneralWURFLEngine(new XMLResource(newRootPath), this.patchResources);
                } else {
                    newEngine = new GeneralWURFLEngine(newRootPath);
                }

                newEngine.load();
                java.nio.file.Files.copy(new File(newRootPath).toPath(), new File(this.rootPath).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                this.reload(this.rootPath);
                return true;
            }
        } catch (Exception e) {
            log.error("An error has occurred replacing {}root with {}", this.rootPath, newRootPath, e);
            return false;
        }
    }

    @Override
/**
 * Sets the marku pesolver.
 */

    public void setMarkupResolver(MarkupResolver markupResolver) {
        this.markupResolver = markupResolver;
    }

    @Override
/**
 * Sets the capabilitie solde ractory.
 */

    public void setCapabilitiesHolderFactory(CapabilitiesHolderFactory capabilitiesHolderFactory) {
        this.capabilitiesHolderFactory = capabilitiesHolderFactory;
    }

    @Override
/**
 * Sets the wurf leques tactory.
 */

    public void setWurflRequestFactory(WURFLRequestFactory requestFactory) {
        if (!(requestFactory instanceof WURFLRequestFactoryWithPriority)) {
            throw new UnsupportedOperationException("User-Agent priority is not supported if the custom request factory does not implement WURFLRequestFactoryWithPriority");
        } else {
            synchronized (this.initLock) {
                this.requestFactory = (WURFLRequestFactoryWithPriority) requestFactory;
                this.userAgentPriority = this.requestFactory.getUserAgentPriority();
                if (this.wurflService != null) {
                    this.wurflService.setRequestFactory(this.requestFactory);
                }

            }
        }
    }

    @Override
/**
 * Sets the use rgen tesolver.
 */

    public void setUserAgentResolver(UserAgentResolver userAgentResolver) {
        this.userAgentResolver = userAgentResolver;
    }

    @Override
/**
 * Sets the devic erovider.
 */

    public void setDeviceProvider(DeviceProvider deviceProvider) {
        this.deviceProvider = deviceProvider;
    }

    @Override
/**
 * Sets the cach erovider.
 */

    public void setCacheProvider(CacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    @Override
/**
 * Returns the wurflutils.
 */

    public WURFLUtils getWURFLUtils() {
        this.ensureInitialized();
        this.lock.readLock().lock();
        WURFLUtils out = this.wurflUtils;
        this.lock.readLock().unlock();
        return out;
    }

    @Override
/**
 * Returns the devic e yd.
 */

    public Device getDeviceById(String deviceId) {
        return this.getWURFLUtils().getDeviceById(deviceId);
    }

    @Override
/**
 * Returns the devic e yd.
 */

    public Device getDeviceById(String deviceId, WURFLRequest request) {
        return this.getWURFLUtils().getDeviceById(deviceId, request);
    }

    @Override
/**
 * Returns the devic e yd.
 */

    public Device getDeviceById(String deviceId, HttpServletRequest request) {
        this.ensureInitialized();
        return this.getWURFLUtils().getDeviceById(deviceId, (new DefaultWURFLRequestFactory(this.userAgentResolver, this.wurflService.getUserAgentPriority())).createRequest(request, this.wurflService.getEngineTarget()));
    }

    @Override
/**
 * Returns the al lirtua lapabilities.
 */

    public Set<String> getAllVirtualCapabilities() {
        this.ensureInitialized();
        return new HashSet<>(VirtualCapabilityHandler.getAllVirtualCapabilities());
    }

    /**
     * Ensur enitialized.
 */

    private void ensureInitialized() {
        if (this.initialized) return;
        synchronized (this.initLock) {
            if (this.initialized) return;
            this.lock.writeLock().lock();
            try {
                initModel();
                initService();
                initUtils();
                VirtualCapabilityUserAgentTool.getInstance()
                        .assignProperties(this.requestFactory.createRequest("", this.engineTarget),
                                this.deviceProvider.getInternalDevice("generic"));
                this.initialized = true;
            } catch (WURFLRuntimeException e) {
                log.error("cannot initialize: {}", e.getMessage(), e);
                throw e;
            } catch (Exception e) {
                log.error("cannot initialize: {}", e.getMessage(), e);
                throw new WURFLRuntimeException(e);
            } finally {
                this.lock.writeLock().unlock();
            }
            initCheckConnection();
        }
    }

    /**
     * Ini todel.
 */

    private void initModel() {
        if (this.wurflModel != null) return;
        this.wurflModel = new DefaultWURFLModel(this.rootResource, this.patchResources, this.capabilityFilter);
        this.rootResource = null;
        this.patchResources = null;
    }

    /**
     * Ini tervice.
 */

    private void initService() {
        if (this.wurflService != null) {
            log.info("wurflService is fed: {}", this.wurflService.getClass().getName());
            this.ensureDeviceProviderInitialized();
            return;
        }
        logIfInfo(this.markupResolver != null, "markupResolver is custom: {}", this.markupResolver);
        logIfInfo(this.capabilitiesHolderFactory != null, "capabilitiesHolderFactory is custom: {}", this.capabilitiesHolderFactory);
        logIfInfo(this.cacheProvider != null, "cacheProvider is custom: {}", this.cacheProvider);
        this.ensureDeviceProviderInitialized();
        initRequestFactory();
        MatcherManager matcherManager = new MatcherManager(this.wurflModel);
        this.wurflService = this.engineTarget != null
                ? new WURFLServiceImpl(this.wurflModel, matcherManager, this.deviceProvider, this.requestFactory, this.engineTarget)
                : new WURFLServiceImpl(this.wurflModel, matcherManager, this.deviceProvider, this.requestFactory);
        if (this.cacheProvider != null) {
            this.wurflService.setCacheProvider(this.cacheProvider);
        }
    }

    /**
     * Ini teques tactory.
 */

    private void initRequestFactory() {
        if (this.requestFactory != null) {
            log.info("wurflRequestFactory is custom: {}", this.requestFactory.getClass().getName());
            return;
        }
        if (this.userAgentResolver != null) {
            log.info("userAgentResolver is custom: {}", this.userAgentResolver.getClass().getName());
            this.requestFactory = new DefaultWURFLRequestFactory(this.userAgentResolver, this.userAgentPriority);
        } else {
            this.requestFactory = new DefaultWURFLRequestFactory(this.userAgentPriority);
        }
    }

    /**
     * Ini ttils.
 */

    private void initUtils() {
        if (this.wurflUtils != null) return;
        this.wurflUtils = new WURFLUtils(this.wurflModel, this.deviceProvider, this.wurflService);
    }

    /**
     * Ini thec konnection.
 */

    private void initCheckConnection() {
        try {
            CheckConnection checkConnection = new CheckConnection();
            checkConnection.setup(this, this.wurflModel);
            checkConnection.check();
        } catch (RuntimeException ignore) {
        }
    }

    /**
     * Lo g fnfo.
 */

    private static void logIfInfo(boolean condition, String message, Object arg) {
        if (condition && log.isInfoEnabled()) {
            log.info(message, arg);
        }
    }

    /**
     * Ensur eevic erovide rnitialized.
 */

    private void ensureDeviceProviderInitialized() {
        if (this.capabilitiesHolderFactory == null) {
            this.capabilitiesHolderFactory = new DefaultCapabilitiesHolderFactory(this.wurflModel);
        }

        if (this.deviceProvider == null) {
            if (this.markupResolver != null) {
                this.deviceProvider = new DefaultDeviceProvider(this.wurflModel, this.capabilitiesHolderFactory, this.markupResolver);
            } else {
                this.deviceProvider = new DefaultDeviceProvider(this.wurflModel, this.capabilitiesHolderFactory);
            }
        } else {
            if (log.isInfoEnabled()) {
                log.info("Device Provider is fed: {}", this.deviceProvider.getClass().getName());
            }

        }
    }

    @Override
/**
 * Returns the devic eo request.
 */

    public Device getDeviceForRequest(HttpServletRequest request) {
        this.ensureInitialized();
        return this.wurflService.getDevice(request);
    }

    @Override
/**
 * Returns the devic eo request.
 */

    public Device getDeviceForRequest(WURFLRequest request) {
        this.ensureInitialized();
        return this.wurflService.getDevice(request);
    }

    @Override
/**
 * Returns the devic eo request.
 */

    public Device getDeviceForRequest(String userAgent) {
        this.ensureInitialized();
        return this.wurflService.getDevice(userAgent);
    }

    @Override
/**
 * Loads and initializes the engine.
 */

    public void load() {
        this.ensureInitialized();
    }

    @Override
/**
 * Returns the engin earget.
 */

    public EngineTarget getEngineTarget() {
        synchronized (this.initLock) {
            if (this.wurflService != null) {
                this.engineTarget = this.wurflService.getEngineTarget();
            }

            return this.engineTarget;
        }
    }

    @Override
/**
 * Sets the engin earget.
 */

    public void setEngineTarget(EngineTarget engineTarget) {
        synchronized (this.initLock) {
            this.engineTarget = engineTarget;
            if (this.wurflService != null) {
                this.wurflService.setEngineTarget(engineTarget);
            }

        }
    }

    @Override
/**
 * Returns the use rgen triority.
 */

    public UserAgentPriority getUserAgentPriority() {
        synchronized (this.initLock) {
            if (this.wurflService != null) {
                this.userAgentPriority = this.wurflService.getUserAgentPriority();
            } else if (this.requestFactory != null) {
                this.userAgentPriority = this.requestFactory.getUserAgentPriority();
            }

            return this.userAgentPriority;
        }
    }

    @Override
/**
 * Sets the use rgen triority.
 */

    public void setUserAgentPriority(UserAgentPriority userAgentPriority) {
        synchronized (this.initLock) {
            this.userAgentPriority = userAgentPriority;
            if (this.wurflService != null) {
                this.wurflService.setUserAgentPriority(userAgentPriority);
            } else if (this.requestFactory != null) {
                this.requestFactory.setUserAgentPriority(userAgentPriority);
            }

        }
    }

    @Override
/**
 * Sets the capabilit yilter.
 */

    public void setCapabilityFilter(String... capabilityFilter) {
        this.capabilityFilter = buildCapabilityFilter(Arrays.asList(capabilityFilter));
    }

    @Override
/**
 * Sets the capabilit yilter.
 */

    public void setCapabilityFilter(Collection<String> capabilityFilter) {
        if (capabilityFilter != null) {
            this.capabilityFilter = buildCapabilityFilter(capabilityFilter);
        }
    }

    /**
     * Buil dapabilit yilter.
 */

    private static String[] buildCapabilityFilter(Collection<String> input) {
        ArrayList<String> capabilities = new ArrayList<>(input);
        for (String capability : ALWAYS_INCLUDED_CAPABILITIES) {
            if (!capabilities.contains(capability)) {
                capabilities.add(capability);
            }
        }
        return capabilities.toArray(new String[0]);
    }

    @Override
/**
 * Returns the apiversion.
 */

    public String getAPIVersion() {
        return "1.9.1.0";
    }

    @Override
/**
 * Returns the al landator yapabilities.
 */

    public Set<String> getAllMandatoryCapabilities() {
        this.ensureInitialized();
        return new HashSet<>(Arrays.asList(VirtualCapabilityEvaluator.MANDATORY_CAPABILITIES));
    }

    @Override
/**
 * Returns the al lapabilities.
 */

    public Set<String> getAllCapabilities() {
        this.ensureInitialized();
        HashSet<String> capabilities = new HashSet<>();
        for (String capability : this.wurflModel.getAllCapabilities()) {
            if (!capability.startsWith("controlcap_")) {
                capabilities.add(capability);
            }
        }

        return capabilities;
    }

    @Override
/**
 * Returns the roo tath.
 */

    public String getRootPath() {
        return this.rootPath;
    }

    public String getApiVersion() {
        return "1.9.1.0";
    }

    /**
     * Returns the wurf lodel.
 */

    public WURFLModel getWurflModel() {
        return wurflModel;
    }

    public WURFLService getWurflService() {
        return wurflService;
    }
}
