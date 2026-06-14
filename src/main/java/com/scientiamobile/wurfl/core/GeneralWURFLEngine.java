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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 通用 WURFL 引擎实现，是 WURFL 设备检测的核心入口。
 * <p>负责加载 WURFL 数据模型、初始化设备提供者、请求工厂、匹配器等核心组件，
 * 并提供设备检测、引擎重载、补丁应用和配置管理等功能。
 * 支持通过能力过滤器限制加载的能力集合，以优化内存占用。</p>
 * <p>使用读写锁保证引擎重载和查询操作的线程安全性。</p>
 */

public class GeneralWURFLEngine implements WURFLEngine {
    private static final Logger log = LoggerFactory.getLogger(GeneralWURFLEngine.class);
    /**
     * 始终包含的能力列表，即使设置了能力过滤器也不会被排除
     */
    /**
     * 始终包含的必备能力名称列表。
     * <p>这些能力是虚拟能力评估器所必需的，无论用户是否通过
     * {@link #setCapabilityFilter(String...)} 指定了过滤器，都会被自动加入能力过滤器。</p>
     */
    private static final List<String> ALWAYS_INCLUDED_CAPABILITIES = Arrays.asList("device_os", "device_os_version", "is_tablet", "is_wireless_device", "pointing_method", "preferred_markup", "resolution_height", "resolution_width", "ux_full_desktop", "xhtml_support_level", "is_smarttv", "can_assign_phone_number", "brand_name", "model_name", "marketing_name", "mobile_browser_version");
    /**
     * 读写锁，用于保证重载和查询操作的线程安全
     */
    private final ReadWriteLock lock;
    /**
     * 初始化锁，防止并发初始化
     */
    private final Object initLock;
    /**
     * 能力过滤器，用于限制引擎加载的能力集合
     */
    private String[] capabilityFilter;
    /**
     * WURFL 根资源（数据文件）
     */
    private WURFLResource rootResource;
    /**
     * 补丁资源集合
     */
    private WURFLResources patchResources;
    /**
     * WURFL 根数据文件的原始路径
     */
    private String rootPath;
    /**
     * 标记语言解析器
     */
    private MarkupResolver markupResolver;
    /**
     * 能力持有器工厂
     */
    private CapabilitiesHolderFactory capabilitiesHolderFactory;
    /**
     * 设备提供者
     */
    private DeviceProvider deviceProvider;
    /**
     * 缓存提供者
     */
    private CacheProvider cacheProvider;
    /**
     * WURFL 服务实例
     */
    private WURFLService wurflService;
    /**
     * User-Agent 解析器
     */
    private UserAgentResolver userAgentResolver;
    /**
     * WURFL 工具类实例
     */
    private WURFLUtils wurflUtils;
    /**
     * WURFL 数据模型
     */
    private WURFLModel wurflModel;
    /**
     * 是否已初始化
     */
    private volatile boolean initialized;
    /**
     * 带优先级支持的请求工厂
     */
    private WURFLRequestFactoryWithPriority requestFactory;
    /**
     * 引擎目标匹配模式
     */
    private EngineTarget engineTarget;
    /**
     * User-Agent 优先级策略
     */
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
     * 根据路径字符串创建 XML 资源对象。
     *
     * @param path 资源路径
     * @return XML 资源对象
     */

    private static WURFLResource createXmlResource(String path) {
        Validate.notEmpty(path, "The path is null");
        return new XMLResource(path);
    }

    /**
     * 根据路径字符串数组创建 XML 资源对象集合。
     *
     * @param paths 资源路径数组
     * @return XML 资源集合
     */

    private static WURFLResources createXmlResources(String[] paths) {
        WURFLResources resources = new WURFLResources();

        for (int i = 0; paths != null && i < paths.length; ++i) {
            resources.add(new XMLResource(paths[i]));
        }

        return resources;
    }

    /**
     * 使用新的根数据文件路径重新加载引擎。
     *
     * @param rootPath 新的根数据文件路径
     */
    @Override
    public void reload(String rootPath) {
        WURFLResource wurflResource = createXmlResource(rootPath);
        this.reload(wurflResource, (WURFLResources) null);
    }

    /**
     * 应用指定的补丁文件。
     * <p>如果补丁路径数组为 {@code null}，则忽略该操作。</p>
     *
     * @param patchPaths 补丁文件路径数组
     */
    @Override
    public void applyPatches(String... patchPaths) {
        if (patchPaths == null) {
            log.warn("null patches, do nothing...");
        } else {
            this.applyPatches(createXmlResources(patchPaths));
        }
    }

    /**
     * 应用指定的补丁资源。
     * <p>如果补丁资源数组为 {@code null}，则忽略该操作。</p>
     *
     * @param patchResources 补丁资源数组
     */
    @Override
    public void applyPatches(WURFLResource... patchResources) {
        this.applyPatches(new WURFLResources(patchResources));
    }

    /**
     * 应用指定集合中的补丁资源。
     * <p>先确保引擎已初始化（外部无锁），再在写锁保护下执行补丁应用操作。</p>
     *
     * @param patchResources 补丁资源集合
     */
    @Override
    public void applyPatches(WURFLResources patchResources) {
        if (patchResources == null) {
            log.warn("null patches, do nothing...");
        } else {
            this.ensureInitialized();
            this.lock.writeLock().lock();

            try {
                this.wurflService.applyPatches(patchResources, this.capabilityFilter);
            } finally {
                this.lock.writeLock().unlock();
            }

        }
    }

    /**
     * 使用新的根数据文件和补丁文件路径重新加载引擎。
     *
     * @param rootPath   新的根数据文件路径
     * @param patchPaths 新的补丁文件路径数组
     */
    @Override
    public void reload(String rootPath, String[] patchPaths) {
        WURFLResource wurflResource = createXmlResource(rootPath);
        WURFLResources wurflResources = createXmlResources(patchPaths);
        this.reload(wurflResource, wurflResources);
    }

    /**
     * 使用新的根资源和补丁资源重新加载引擎。
     *
     * @param rootResource   新的根资源
     * @param patchResources 新的补丁资源数组
     */
    @Override
    public void reload(WURFLResource rootResource, WURFLResource... patchResources) {
        this.reload(rootResource, new WURFLResources(patchResources));
    }

    /**
     * 使用新的根资源和补丁资源集合重新加载引擎。
     * <p>先确保引擎已初始化（外部无锁），再在写锁保护下执行重载操作。</p>
     *
     * @param rootResource   新的根资源
     * @param patchResources 新的补丁资源集合
     */
    @Override
    public void reload(WURFLResource rootResource, WURFLResources patchResources) {
        this.ensureInitialized();
        if (patchResources == null) {
            patchResources = new WURFLResources();
        }

        this.lock.writeLock().lock();

        try {
            this.rootPath = rootResource.getOriginalPath();
            this.wurflService.reload(rootResource, patchResources, this.capabilityFilter);
        } finally {
            this.lock.writeLock().unlock();
        }

    }

    /**
     * 替换引擎的根数据文件。
     * <p>将新路径的 WURFL 数据文件复制到当前根路径，然后重新加载引擎。
     * 当前根路径对应的文件必须可写。</p>
     *
     * @param newRootPath 新的根数据文件路径
     * @return 如果替换成功返回 {@code true}
     */
    @Override
    public boolean replaceRoot(String newRootPath) {
        try {
            if (StringUtils.isBlank(newRootPath)) {
                log.warn("Empty value has been provided for replacing root, skipping");
                return false;
            } else if (!(new File(this.rootPath).getCanonicalFile()).canWrite()) {
                log.error("Engine root at {} is not writable, cannot replace it", this.rootPath);
                return false;
            } else {
                // 1. 验证新数据文件能正常加载（仅内存操作，不影响旧引擎）
                GeneralWURFLEngine newEngine;
                if (this.patchResources != null && this.patchResources.size() != 0) {
                    newEngine = new GeneralWURFLEngine(new XMLResource(newRootPath), this.patchResources);
                } else {
                    newEngine = new GeneralWURFLEngine(newRootPath);
                }
                newEngine.load();

                Path rootPathFile = new File(this.rootPath).getCanonicalFile().toPath();
                Path newRootPathFile = new File(newRootPath).getCanonicalFile().toPath();

                // 2a. 备份原文件（用于 reload 失败时回滚）
                Path backupPath = Files.createTempFile(rootPathFile.getParent(), "wurfl-backup-", ".tmp");
                try {
                    Files.copy(rootPathFile, backupPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    Files.deleteIfExists(backupPath);
                    throw e;
                }

                try {
                    // 2b. 先复制到临时文件，再用原子 move 替换原文件
                    //     ATOMIC_MOVE 在同一文件系统上是原子操作：要么完全成功，要么原文件不受影响
                    Path tmpPath = Files.createTempFile(rootPathFile.getParent(), "wurfl-replace-", ".tmp");
                    try {
                        Files.copy(newRootPathFile, tmpPath, StandardCopyOption.REPLACE_EXISTING);
                        Files.move(tmpPath, rootPathFile, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception e) {
                        Files.deleteIfExists(tmpPath);
                        throw e;
                    }

                    // 3. 文件已原子替换，重载引擎使用新数据
                    this.reload(this.rootPath);
                } catch (Exception e) {
                    // reload 失败：磁盘文件已替换为新版本但引擎无法加载，回滚备份
                    log.error("Failed to reload engine after file replacement, restoring backup", e);
                    try {
                        Files.move(backupPath, rootPathFile, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
                        this.reload(this.rootPath);
                        log.info("Successfully restored original file and reloaded engine");
                    } catch (Exception restoreEx) {
                        log.error("CRITICAL: Engine is in an inconsistent state - failed to restore original file after reload error!", restoreEx);
                    }
                    throw e;
                } finally {
                    Files.deleteIfExists(backupPath);
                }

                return true;
            }
        } catch (Exception e) {
            log.error("An error has occurred replacing {} root with {}", this.rootPath, newRootPath, e);
            return false;
        }
    }

    /**
     * 设置标记语言解析器。
     *
     * @param markupResolver 标记语言解析器
     */
    @Override
    public void setMarkupResolver(MarkupResolver markupResolver) {
        this.markupResolver = markupResolver;
    }

    /**
     * 设置能力持有器工厂。
     *
     * @param capabilitiesHolderFactory 能力持有器工厂
     */
    @Override
    public void setCapabilitiesHolderFactory(CapabilitiesHolderFactory capabilitiesHolderFactory) {
        this.capabilitiesHolderFactory = capabilitiesHolderFactory;
    }

    /**
     * 设置 WURFL 请求工厂。
     * <p>如果自定义的请求工厂未实现 {@link WURFLRequestFactoryWithPriority} 接口，
     * 则抛出 {@link UnsupportedOperationException}，因为 User-Agent 优先级策略需要该接口的支持。</p>
     *
     * @param requestFactory WURFL 请求工厂
     * @throws UnsupportedOperationException 如果不支持 User-Agent 优先级
     */
    @Override
    public void setWurflRequestFactory(WURFLRequestFactory requestFactory) {
        if (!(requestFactory instanceof WURFLRequestFactoryWithPriority)) {
            throw new UnsupportedOperationException("User-Agent priority is not supported if the custom request factory does not implement WURFLRequestFactoryWithPriority");
        } else {
            this.lock.writeLock().lock();
            try {
                this.requestFactory = (WURFLRequestFactoryWithPriority) requestFactory;
                this.userAgentPriority = this.requestFactory.getUserAgentPriority();
                if (this.wurflService != null) {
                    this.wurflService.setRequestFactory(this.requestFactory);
                }
            } finally {
                this.lock.writeLock().unlock();
            }
        }
    }

    /**
     * 设置 User-Agent 解析器。
     *
     * @param userAgentResolver User-Agent 解析器
     */
    @Override
    public void setUserAgentResolver(UserAgentResolver userAgentResolver) {
        this.userAgentResolver = userAgentResolver;
    }

    /**
     * 设置设备提供者。
     *
     * @param deviceProvider 设备提供者
     */
    @Override
    public void setDeviceProvider(DeviceProvider deviceProvider) {
        this.deviceProvider = deviceProvider;
    }

    /**
     * 设置缓存提供者。
     *
     * @param cacheProvider 缓存提供者
     */
    @Override
    public void setCacheProvider(CacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    /**
     * 获取 WURFL 工具类实例，用于查询模型中的设备、能力和版本信息。
     *
     * @return WURFL 工具类
     */
    @Override
    public WURFLUtils getWURFLUtils() {
        this.ensureInitialized();
        this.lock.readLock().lock();
        try {
            return this.wurflUtils;
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * 根据设备 ID 获取设备实例（使用默认请求）。
     *
     * @param deviceId 设备 ID
     * @return 设备实例
     */
    @Override
    public Device getDeviceById(String deviceId) {
        return this.getWURFLUtils().getDeviceById(deviceId);
    }

    /**
     * 根据设备 ID 和 WURFL 请求获取设备实例。
     *
     * @param deviceId 设备 ID
     * @param request  WURFL 请求
     * @return 设备实例
     */
    @Override
    public Device getDeviceById(String deviceId, WURFLRequest request) {
        return this.getWURFLUtils().getDeviceById(deviceId, request);
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
        this.ensureInitialized();
        return this.getWURFLUtils().getDeviceById(deviceId, this.requestFactory.createRequest(request, this.wurflService.getEngineTarget()));
    }

    /**
     * 获取所有虚拟能力的名称集合。
     *
     * @return 虚拟能力名称集合
     */
    @Override
    public Set<String> getAllVirtualCapabilities() {
        this.ensureInitialized();
        return new HashSet<>(VirtualCapabilityHandler.getAllVirtualCapabilities());
    }

    /**
     * 确保引擎已初始化，如果未初始化则执行初始化流程。
     * <p>在写锁保护下执行完整的初始化流程，包括加载模型、初始化服务、
     * 初始化工具类和虚拟能力工具。使用双重检查锁定模式避免重复初始化。</p>
     * <p>不再嵌套 {@link #initLock}，统一使用 {@link #lock} 的写锁，
     * 消除与 {@code reload()} 之间锁顺序不一致的风险。</p>
     */

    private void ensureInitialized() {
        if (this.initialized) return;
        this.lock.writeLock().lock();
        try {
            if (this.initialized) return;
            initModel();
            initService();
            initUtils();
            VirtualCapabilityUserAgentTool.getInstance()
                    .assignProperties(this.requestFactory.createRequest("", this.engineTarget),
                            this.deviceProvider.getInternalDevice("generic"));
            initCheckConnection();
            this.initialized = true;
        } catch (WURFLRuntimeException e) {
            log.error("cannot initialize: {}", e.getMessage(), e);
            resetPartialInit();
            throw e;
        } catch (Exception e) {
            log.error("cannot initialize: {}", e.getMessage(), e);
            resetPartialInit();
            throw new WURFLRuntimeException(e);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * 重置部分初始化状态，使引擎可重试初始化。
     * <p>当 {@link #initModel()} 或 {@link #initService()} 等步骤失败时，
     * 它们可能已经给部分字段赋值（如 {@link #wurflModel}），
     * 导致下次重试时因非 null 检查而跳过。重置这些字段为 null
     * 可确保下一次 {@link #ensureInitialized()} 能从干净状态开始。</p>
     */
    private void resetPartialInit() {
        this.wurflModel = null;
        this.wurflService = null;
        this.deviceProvider = null;
        this.requestFactory = null;
    }

    /**
     * 初始化 WURFL 数据模型。
     * <p>如果数据模型尚未加载，则创建 {@link DefaultWURFLModel} 实例并释放根资源和补丁资源引用。</p>
     */

    private void initModel() {
        if (this.wurflModel != null) return;
        this.wurflModel = new DefaultWURFLModel(this.rootResource, this.patchResources, this.capabilityFilter);
        this.rootResource = null;
        this.patchResources = null;
    }

    /**
     * 初始化 WURFL 服务。
     * <p>确保设备提供者已初始化，创建匹配器管理器并构建 {@link WURFLServiceImpl} 实例。</p>
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
     * 初始化请求工厂。
     * <p>如果自定义请求工厂已设置，则直接使用；否则根据是否配置了 User-Agent 解析器创建默认工厂。</p>
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
     * 初始化 WURFL 工具类。
     */

    private void initUtils() {
        if (this.wurflUtils != null) return;
        this.wurflUtils = new WURFLUtils(this.wurflModel, this.deviceProvider, this.wurflService);
    }

    /**
     * 初始化连通性检查。
     * <p>仅在启用连通性检查时发送使用统计信息到 ScientiaMobile 后台。</p>
     */

    private void initCheckConnection() {
        try {
            CheckConnection checkConnection = new CheckConnection();
            checkConnection.setup(this, this.wurflModel);
            checkConnection.check();
        } catch (RuntimeException e) {
            log.warn("CheckConnection failed to initialize or execute, skipping", e);
        }
    }

    /**
     * 在指定条件下记录信息级别的日志。
     *
     * @param condition 是否满足记录条件
     * @param message   日志消息模板
     * @param arg       日志参数
     */

    private static void logIfInfo(boolean condition, String message, Object arg) {
        if (condition && log.isInfoEnabled()) {
            log.info(message, arg);
        }
    }

    /**
     * 确保设备提供者已初始化。
     * <p>如果能力持有器工厂或设备提供者未设置，则使用默认实现进行初始化。</p>
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

    /**
     * 根据 HTTP Servlet 请求进行设备检测。
     *
     * @param request HTTP Servlet 请求
     * @return 检测到的设备实例
     */
    @Override
    public Device getDeviceForRequest(HttpServletRequest request) {
        this.ensureInitialized();
        this.lock.readLock().lock();
        try {
            return this.wurflService.getDevice(request);
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * 根据 WURFL 请求进行设备检测。
     *
     * @param request WURFL 请求
     * @return 检测到的设备实例
     */
    @Override
    public Device getDeviceForRequest(WURFLRequest request) {
        this.ensureInitialized();
        this.lock.readLock().lock();
        try {
            return this.wurflService.getDevice(request);
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * 根据 User-Agent 字符串进行设备检测。
     *
     * @param userAgent User-Agent 字符串
     * @return 检测到的设备实例
     */
    @Override
    public Device getDeviceForRequest(String userAgent) {
        this.ensureInitialized();
        this.lock.readLock().lock();
        try {
            return this.wurflService.getDevice(userAgent);
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * 加载并初始化引擎。
     * <p>如果引擎尚未初始化，则执行完整的初始化流程（加载模型、注册服务等）。</p>
     */
    @Override
    public void load() {
        this.ensureInitialized();
    }

    /**
     * 获取当前的引擎目标匹配模式。
     *
     * @return 引擎目标模式
     */
    @Override
    public EngineTarget getEngineTarget() {
        this.lock.readLock().lock();
        try {
            if (this.wurflService != null) {
                return this.wurflService.getEngineTarget();
            }

            return this.engineTarget;
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * 设置引擎目标匹配模式。
     * <p>如果 WURFL 服务已初始化，则同时将模式设置到服务中。</p>
     *
     * @param engineTarget 引擎目标模式
     */
    @Override
    public void setEngineTarget(EngineTarget engineTarget) {
        this.lock.writeLock().lock();
        try {
            this.engineTarget = engineTarget;
            if (this.wurflService != null) {
                this.wurflService.setEngineTarget(engineTarget);
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * 获取当前的 User-Agent 优先级策略。
     *
     * @return User-Agent 优先级策略
     */
    @Override
    public UserAgentPriority getUserAgentPriority() {
        this.lock.readLock().lock();
        try {
            if (this.wurflService != null) {
                return this.wurflService.getUserAgentPriority();
            } else if (this.requestFactory != null) {
                return this.requestFactory.getUserAgentPriority();
            }

            return this.userAgentPriority;
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * 设置 User-Agent 优先级策略。
     * <p>如果 WURFL 服务已初始化，则同时将策略设置到服务中；
     * 否则设置到请求工厂中。</p>
     *
     * @param userAgentPriority User-Agent 优先级策略
     */
    @Override
    public void setUserAgentPriority(UserAgentPriority userAgentPriority) {
        this.lock.writeLock().lock();
        try {
            this.userAgentPriority = userAgentPriority;
            if (this.wurflService != null) {
                this.wurflService.setUserAgentPriority(userAgentPriority);
            } else if (this.requestFactory != null) {
                this.requestFactory.setUserAgentPriority(userAgentPriority);
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * 设置能力过滤器，限制引擎加载的能力集合。
     * <p>传入的能力名数组会自动补充始终包含的必备能力。</p>
     *
     * @param capabilityFilter 需要包含的能力名称数组
     * @see #ALWAYS_INCLUDED_CAPABILITIES
     */
    @Override
    public void setCapabilityFilter(String... capabilityFilter) {
        this.capabilityFilter = buildCapabilityFilter(Arrays.asList(capabilityFilter));
    }

    /**
     * 设置能力过滤器，限制引擎加载的能力集合。
     * <p>传入的能力名集合会自动补充始终包含的必备能力。</p>
     *
     * @param capabilityFilter 需要包含的能力名称集合
     * @see #ALWAYS_INCLUDED_CAPABILITIES
     */
    @Override
    public void setCapabilityFilter(Collection<String> capabilityFilter) {
        if (capabilityFilter != null) {
            this.capabilityFilter = buildCapabilityFilter(capabilityFilter);
        }
    }

    /**
     * 构建能力过滤器，确保始终包含必备的能力。
     * <p>在用户指定的能力列表基础上，补充 {@link #ALWAYS_INCLUDED_CAPABILITIES} 中定义的能力。
     * 使用 {@link LinkedHashSet} 去重同时保持输入顺序，
     * 将 {@code contains} 检查从 {@code O(n&times;m)}（ArrayList 线性扫描）降为 {@code O(1)} 哈希查找。</p>
     *
     * @param input 用户指定的能力名称集合
     * @return 完整的能力过滤器数组
     */

    private static String[] buildCapabilityFilter(Collection<String> input) {
        LinkedHashSet<String> capabilities = new LinkedHashSet<>(input);
        capabilities.addAll(ALWAYS_INCLUDED_CAPABILITIES);
        return capabilities.toArray(new String[0]);
    }

    /**
     * 获取 WURFL API 版本号。
     *
     * @return API 版本号
     */
    @Override
    public String getAPIVersion() {
        return "1.9.1.0";
    }

    /**
     * 获取所有必备能力的名称集合。
     *
     * @return 必备能力名称集合
     */
    @Override
    public Set<String> getAllMandatoryCapabilities() {
        this.ensureInitialized();
        return new HashSet<>(VirtualCapabilityEvaluator.getMandatoryCapabilities());
    }

    /**
     * 获取模型中定义的所有能力名称（排除控制能力）。
     *
     * @return 能力名称集合
     */
    @Override
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

    /**
     * 获取引擎根数据文件的路径。
     *
     * @return 根数据文件路径
     */
    @Override
    public String getRootPath() {
        return this.rootPath;
    }

    /**
     * 获取 WURFL 数据模型实例。
     *
     * @return WURFL 数据模型
     */

    public WURFLModel getWurflModel() {
        return wurflModel;
    }

    /**
     * 获取 WURFL 服务实例。
     *
     * @return WURFL 服务
     */

    public WURFLService getWurflService() {
        return wurflService;
    }
}
