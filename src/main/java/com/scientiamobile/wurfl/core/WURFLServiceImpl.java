package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.cache.CacheProvider;
import com.scientiamobile.wurfl.core.cache.DoubleLRUMapCacheProvider;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
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
 * WURFL 服务实现，负责设备匹配、缓存和设备信息查询的核心服务层。
 * <p>协调匹配器管理器（{@link MatcherManager}）、设备提供者（{@link DeviceProvider}）
 * 和缓存提供者（{@link CacheProvider}）完成设备检测流程。
 * 支持通过读写锁保证重载和查询操作的线程安全。</p>
 */

class WURFLServiceImpl implements WURFLService {
    private static final Logger log = LoggerFactory.getLogger(WURFLServiceImpl.class);
    /**
     * WURFL 数据模型
     */
    private final WURFLModel wurflModel;
    /**
     * 模型读写锁，保证重载和查询的线程安全
     */
    private ReentrantReadWriteLock modelLock;
    /**
     * 缓存提供者，缓存已检测设备的匹配结果
     */
    private volatile CacheProvider cacheProvider;
    /**
     * 缓存提供者初始化锁（专用锁对象，避免与实例级 synchronized(this) 产生竞争）
     */
    private final Object cacheLock = new Object();
    /**
     * 匹配器管理器
     */
    private final MatcherManager matcherManager;
    /**
     * 设备提供者
     */
    private final DeviceProvider deviceProvider;
    /**
     * 带优先级支持的请求工厂
     */
    private WURFLRequestFactoryWithPriority requestFactory;
    /**
     * 引擎目标匹配模式
     */
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

    /**
     * 获取匹配器管理器实例。
     *
     * @return 匹配器管理器
     */
    @Override
    public MatcherManager getMatcherManager() {
        return this.matcherManager;
    }

    /**
     * 设置缓存提供者，用于缓存已检测设备的匹配结果。
     *
     * @param cacheProvider 缓存提供者
     */
    @Override
    public void setCacheProvider(CacheProvider cacheProvider) {
        log.info("feeding {}", cacheProvider);
        this.cacheProvider = cacheProvider;
    }

    /**
     * 根据 WURFL 请求进行设备检测。
     * <p>优先从缓存中查找设备，未命中时执行匹配并缓存结果。</p>
     *
     * @param request WURFL 请求
     * @return 设备实例
     */
    @Override
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
     * 从缓存数据构建设备实例。
     * <p>匹配类型标记为 {@link MatchType#cached}，匹配器名称为 Cache。</p>
     *
     * @param internalDevice 内部设备实例
     * @param request        WURFL 请求
     * @return 设备实例
     */

    private Device buildCachedDevice(InternalDevice internalDevice, WURFLRequest request) {
        DeviceInfo deviceInfo = new DeviceInfo(internalDevice.getId(), MatchType.cached, "Cache", "Cache",
                request.getOriginalUserAgent(), "");
        return this.deviceProvider.buildDevice(internalDevice, request, deviceInfo.getMatchType(),
                deviceInfo.getMatcherName(), deviceInfo.getBucketMatcherName());
    }

    /**
     * 执行设备匹配并将结果缓存。
     * <p>如果引擎目标为快速桌面浏览器匹配且满足条件，则直接返回通用桌面浏览器设备；
     * 否则通过匹配器管理器进行标准匹配。</p>
     *
     * @param request WURFL 请求
     * @return 设备实例
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
        if (deviceInfo == null) {
            throw new WURFLRuntimeException(
                    "MatcherManager returned null for request: " + request.getOriginalUserAgent());
        }
        InternalDevice internalDevice = this.cacheProvider.getInternalDeviceFromDeviceId(deviceInfo.getId());
        if (internalDevice == null) {
            internalDevice = this.deviceProvider.getInternalDevice(deviceInfo.getId());
        }
        if (internalDevice == null) {
            throw new WURFLRuntimeException(
                    "Device not found for ID: " + deviceInfo.getId());
        }
        this.cacheProvider.putDevice(request.getOriginalUserAgent(), internalDevice);
        return this.deviceProvider.buildDevice(internalDevice, request, deviceInfo.getMatchType(),
                deviceInfo.getMatcherName(), deviceInfo.getBucketMatcherName());
    }

    /**
     * 根据 HTTP Servlet 请求进行设备检测。
     *
     * @param request HTTP Servlet 请求
     * @return 设备实例
     */
    @Override
    public Device getDevice(HttpServletRequest request) {
        Validate.notNull(request, "The request must be not null");
        WURFLRequest wurflRequest = this.requestFactory.createRequest(request, this.engineTarget);
        return this.getDevice(wurflRequest);
    }

    /**
     * 根据 User-Agent 字符串进行设备检测。
     *
     * @param userAgent User-Agent 字符串
     * @return 设备实例
     */
    @Override
    public Device getDevice(String userAgent) {
        Validate.notNull(userAgent, "The userAgent must be not null");
        WURFLRequest wurflRequest = this.requestFactory.createRequest(userAgent, this.engineTarget);
        return this.getDevice(wurflRequest);
    }

    /**
     * 确保缓存提供者已初始化。
     * <p>如果未设置缓存提供者，则使用默认的 {@link DoubleLRUMapCacheProvider}。
     * 使用双重检查锁定（DCL）模式 + 专用锁对象 {@link #cacheLock}，
     * 避免与 {@link #modelLock}（{@code getDevice} 持有读锁、{@code reload} 持有写锁）
     * 产生无关的锁竞争。</p>
     */

    private void ensureCacheProvider() {
        if (this.cacheProvider == null) {
            synchronized (cacheLock) {
                if (this.cacheProvider == null) {
                    log.info("no Cache Provider, using default (DoubleLRUMapCacheProvider)");
                    this.cacheProvider = new DoubleLRUMapCacheProvider();
                }

            }
        }
    }

    /**
     * 获取当前的引擎目标匹配模式。
     *
     * @return 引擎目标模式
     */
    @Override
    public EngineTarget getEngineTarget() {
        return this.engineTarget;
    }

    /**
     * 设置引擎目标匹配模式。
     * <p>仅接受 {@link EngineTarget#fastDesktopBrowserMatch} 作为非默认值，
     * 其他值均视为 {@link EngineTarget#defaultTarget}。</p>
     *
     * @param engineTarget 引擎目标模式
     */
    @Override
    public void setEngineTarget(EngineTarget engineTarget) {
        if (engineTarget != EngineTarget.fastDesktopBrowserMatch) {
            this.engineTarget = EngineTarget.defaultTarget;
        } else {
            this.engineTarget = engineTarget;
        }
    }

    /**
     * 获取当前的 User-Agent 优先级策略。
     *
     * @return User-Agent 优先级策略
     */
    @Override
    public UserAgentPriority getUserAgentPriority() {
        return this.requestFactory.getUserAgentPriority();
    }

    /**
     * 设置 User-Agent 优先级策略。
     *
     * @param priority User-Agent 优先级策略
     */
    @Override
    public void setUserAgentPriority(UserAgentPriority priority) {
        this.requestFactory.setUserAgentPriority(priority);
    }

    /**
     * 根据设备 ID 获取设备实例（使用默认的 User-Agent）。
     *
     * @param deviceId 设备 ID
     * @return 设备实例
     */
    @Override
    public Device getDeviceById(String deviceId) {
        InternalDevice internalDevice = this.deviceProvider.getInternalDevice(deviceId);
        String userAgent = resolveUserAgent(internalDevice);
        WURFLRequest request = this.requestFactory.createRequest(userAgent, this.engineTarget);
        return this.getDeviceById(deviceId, request);
    }

    /**
     * 解析内部设备的 User-Agent。
     * <p>如果设备自身的 UA 以 {@code DO_NOT_MATCH} 开头，则沿继承链向上查找祖先的 UA。</p>
     *
     * @param internalDevice 内部设备实例
     * @return User-Agent 字符串
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

    /**
     * 根据设备 ID 和 HTTP Servlet 请求获取设备实例。
     *
     * @param deviceId 设备 ID
     * @param request  HTTP Servlet 请求
     * @return 设备实例
     */
    @Override
    public Device getDeviceById(String deviceId, HttpServletRequest request) {
        Validate.notNull(request, "The request must be not null");
        WURFLRequest wurflRequest = this.requestFactory.createRequest(request, this.engineTarget);
        return this.getDeviceById(deviceId, wurflRequest);
    }

    /**
     * 根据设备 ID 和 WURFL 请求获取设备实例。
     * <p>使用指定的请求上下文（包含归一化 UA 等信息）构建设备实例。</p>
     *
     * @param deviceId 设备 ID
     * @param request  WURFL 请求
     * @return 设备实例
     */
    @Override
    public Device getDeviceById(String deviceId, WURFLRequest request) {
        Validate.notNull(request, "The request must be not null");
        request.performGenericNormalization();
        return this.deviceProvider.buildDevice(this.deviceProvider.getInternalDevice(deviceId), request, MatchType.none, "Utils", "Utils");
    }

    /**
     * 重新加载 WURFL 数据模型和相关组件。
     * <p>在写锁保护下执行重载，包括重新加载模型、刷新匹配器和清空缓存。</p>
     *
     * @param wurflResource  WURFL 根资源
     * @param wurflResources 补丁资源集合
     * @param patches        能力过滤器
     */
    @Override
    public void reload(WURFLResource wurflResource, WURFLResources wurflResources, String... patches) {
        this.modelLock.writeLock().lock();
        try {
            log.info("reloading service");
            this.wurflModel.reload(wurflResource, wurflResources, patches);
            this.matcherManager.reloadModel(this.wurflModel);
            this.clearCacheProvider();
        } finally {
            this.modelLock.writeLock().unlock();
        }

    }

    /**
     * 清空缓存提供者中的所有缓存数据。
     */

    private void clearCacheProvider() {
        log.info("about to clear cache provider");
        this.ensureCacheProvider();
        this.cacheProvider.clear();
    }

    /**
     * 应用补丁资源到 WURFL 数据模型。
     * <p>在写锁保护下执行，包括应用补丁、刷新匹配器和清空缓存。</p>
     *
     * @param wurflResources 补丁资源集合
     * @param patches        能力过滤器
     */
    @Override
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
     * 设置请求工厂。
     *
     * @param requestFactory 带优先级支持的请求工厂
     */

    public final void setRequestFactory(WURFLRequestFactoryWithPriority requestFactory) {
        this.requestFactory = requestFactory;
    }
}
