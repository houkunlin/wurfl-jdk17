package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.cache.CacheProvider;
import com.scientiamobile.wurfl.core.request.UserAgentResolver;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequestFactory;
import com.scientiamobile.wurfl.core.resource.WURFLResource;
import com.scientiamobile.wurfl.core.resource.WURFLResources;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collection;
import java.util.Set;

/**
 * WURFL 引擎接口，定义设备检测的核心 API。
 * <p>提供了根据 {@link HttpServletRequest}、{@link WURFLRequest} 或 User-Agent 字符串
 * 进行设备检测的方法，以及引擎生命周期管理（加载、重载、补丁应用）、
 * 配置管理（引擎目标、UA 优先级、能力过滤器、缓存策略）和模型查询等功能。</p>
 */

public interface WURFLEngine {
    /**
     * WURFL API 版本号
     */
    String API_VERSION = "1.9.1.0";

    /**
     * 根据 HTTP Servlet 请求进行设备检测。
     *
     * @param request HTTP Servlet 请求
     * @return 检测到的设备实例
     */
    Device getDeviceForRequest(HttpServletRequest request);

    /**
     * 根据 WURFL 请求进行设备检测。
     *
     * @param request WURFL 请求
     * @return 检测到的设备实例
     */
    Device getDeviceForRequest(WURFLRequest request);

    /**
     * 根据 User-Agent 字符串进行设备检测。
     *
     * @param userAgent User-Agent 字符串
     * @return 检测到的设备实例
     */
    Device getDeviceForRequest(String userAgent);

    /**
     * 加载并初始化引擎。
     */
    void load();

    /**
     * 使用新的根数据文件路径重新加载引擎。
     *
     * @param wurflPath 新的根数据文件路径
     */
    void reload(String wurflPath);

    /**
     * 使用新的根数据文件和补丁文件路径重新加载引擎。
     *
     * @param wurflPath       新的根数据文件路径
     * @param wurflPatchPaths 新的补丁文件路径数组
     */
    void reload(String wurflPath, String[] wurflPatchPaths);

    /**
     * 使用新的根资源和补丁资源重新加载引擎。
     *
     * @param wurflResource  新的根资源
     * @param patchResources 新的补丁资源数组
     */
    void reload(WURFLResource wurflResource, WURFLResource... patchResources);

    /**
     * 使用新的根资源和补丁资源集合重新加载引擎。
     *
     * @param wurflResource  新的根资源
     * @param patchResources 新的补丁资源集合
     */
    void reload(WURFLResource wurflResource, WURFLResources patchResources);

    /**
     * 替换引擎的根数据文件。
     *
     * @param wurflPath 新的根数据文件路径
     * @return 如果替换成功返回 {@code true}
     */
    boolean replaceRoot(String wurflPath);

    /**
     * 应用指定的补丁文件。
     *
     * @param patchPaths 补丁文件路径数组
     */
    void applyPatches(String... patchPaths);

    /**
     * 应用指定的补丁资源。
     *
     * @param patchResources 补丁资源数组
     */
    void applyPatches(WURFLResource... patchResources);

    /**
     * 应用指定集合中的补丁资源。
     *
     * @param patchResources 补丁资源集合
     */
    void applyPatches(WURFLResources patchResources);

    /**
     * 设置标记语言解析器。
     *
     * @param markupResolver 标记语言解析器
     */
    void setMarkupResolver(MarkupResolver markupResolver);

    /**
     * 设置能力持有器工厂。
     *
     * @param capabilitiesHolderFactory 能力持有器工厂
     */
    void setCapabilitiesHolderFactory(CapabilitiesHolderFactory capabilitiesHolderFactory);

    /**
     * 设置 WURFL 请求工厂。
     *
     * @param wurflRequestFactory WURFL 请求工厂
     */
    void setWurflRequestFactory(WURFLRequestFactory wurflRequestFactory);

    /**
     * 设置 User-Agent 解析器。
     *
     * @param userAgentResolver User-Agent 解析器
     */
    void setUserAgentResolver(UserAgentResolver userAgentResolver);

    /**
     * 设置设备提供者。
     *
     * @param deviceProvider 设备提供者
     */
    void setDeviceProvider(DeviceProvider deviceProvider);

    /**
     * 设置缓存提供者。
     *
     * @param cacheProvider 缓存提供者
     */
    void setCacheProvider(CacheProvider cacheProvider);

    /**
     * 设置能力过滤器，限制引擎加载的能力集合。
     *
     * @param capabilityFilter 需要包含的能力名称数组
     */
    void setCapabilityFilter(String... capabilityFilter);

    /**
     * 设置能力过滤器，限制引擎加载的能力集合。
     *
     * @param capabilityFilter 需要包含的能力名称集合
     */
    void setCapabilityFilter(Collection<String> capabilityFilter);

    /**
     * 获取当前的引擎目标匹配模式。
     *
     * @return 引擎目标模式
     */
    EngineTarget getEngineTarget();

    /**
     * 设置引擎目标匹配模式。
     *
     * @param engineTarget 引擎目标模式
     */
    void setEngineTarget(EngineTarget engineTarget);

    /**
     * 获取当前的 User-Agent 优先级策略。
     *
     * @return User-Agent 优先级策略
     */
    UserAgentPriority getUserAgentPriority();

    /**
     * 设置 User-Agent 优先级策略。
     *
     * @param userAgentPriority User-Agent 优先级策略
     */
    void setUserAgentPriority(UserAgentPriority userAgentPriority);

    /**
     * 获取 WURFL 工具类实例。
     *
     * @return WURFL 工具类
     */
    WURFLUtils getWURFLUtils();

    /**
     * 获取所有虚拟能力的名称集合。
     *
     * @return 虚拟能力名称集合
     */
    Set<String> getAllVirtualCapabilities();

    /**
     * 根据设备 ID 获取设备实例。
     *
     * @param deviceId 设备 ID
     * @return 设备实例
     */
    Device getDeviceById(String deviceId);

    /**
     * 根据设备 ID 和 WURFL 请求获取设备实例。
     *
     * @param deviceId 设备 ID
     * @param request  WURFL 请求
     * @return 设备实例
     */
    Device getDeviceById(String deviceId, WURFLRequest request);

    /**
     * 根据设备 ID 和 HTTP Servlet 请求获取设备实例。
     *
     * @param deviceId 设备 ID
     * @param request  HTTP Servlet 请求
     * @return 设备实例
     */
    Device getDeviceById(String deviceId, HttpServletRequest request);

    /**
     * 获取 WURFL API 版本号。
     *
     * @return API 版本号
     */
    String getAPIVersion();

    /**
     * 获取所有必备能力的名称集合。
     *
     * @return 必备能力名称集合
     */
    Set<String> getAllMandatoryCapabilities();

    /**
     * 获取模型中定义的所有能力名称。
     *
     * @return 能力名称集合
     */
    Set<String> getAllCapabilities();

    /**
     * 获取引擎根数据文件的路径。
     *
     * @return 根数据文件路径
     */
    String getRootPath();
}
