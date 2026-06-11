package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.cache.CacheProvider;
import com.scientiamobile.wurfl.core.matchers.MatcherManager;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequestFactoryWithPriority;
import com.scientiamobile.wurfl.core.resource.WURFLResource;
import com.scientiamobile.wurfl.core.resource.WURFLResources;
import jakarta.servlet.http.HttpServletRequest;

/**
 * WURFL 服务接口，定义设备检测服务的核心操作。
 * <p>提供设备匹配、缓存管理、引擎目标配置、数据模型重载和补丁应用等功能。
 * 是 {@link WURFLEngine} 的下层服务抽象，负责协调匹配器、设备提供者和缓存提供者。</p>
 */

public interface WURFLService {
    /**
     * 根据 HTTP Servlet 请求进行设备检测。
     *
     * @param request HTTP Servlet 请求
     * @return 设备实例
     */
    Device getDevice(HttpServletRequest request);

    /**
     * 根据 WURFL 请求进行设备检测。
     *
     * @param request WURFL 请求
     * @return 设备实例
     */
    Device getDevice(WURFLRequest request);

    /**
     * 根据 User-Agent 字符串进行设备检测。
     *
     * @param userAgent User-Agent 字符串
     * @return 设备实例
     */
    Device getDevice(String userAgent);

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
     * @param priority User-Agent 优先级策略
     */
    void setUserAgentPriority(UserAgentPriority priority);

    /**
     * 获取匹配器管理器实例。
     *
     * @return 匹配器管理器
     */
    MatcherManager getMatcherManager();

    /**
     * 设置缓存提供者。
     *
     * @param cacheProvider 缓存提供者
     */
    void setCacheProvider(CacheProvider cacheProvider);

    /**
     * 重新加载 WURFL 数据模型和相关组件。
     *
     * @param wurflResource  WURFL 根资源
     * @param wurflResources 补丁资源集合
     * @param patches        能力过滤器
     */
    void reload(WURFLResource wurflResource, WURFLResources wurflResources, String... patches);

    /**
     * 应用补丁资源到 WURFL 数据模型。
     *
     * @param wurflResources 补丁资源集合
     * @param patches        能力过滤器
     */
    void applyPatches(WURFLResources wurflResources, String... patches);

    /**
     * 设置请求工厂。
     *
     * @param requestFactory 带优先级支持的请求工厂
     */
    void setRequestFactory(WURFLRequestFactoryWithPriority requestFactory);

    /**
     * 根据设备 ID 获取设备实例。
     *
     * @param deviceId 设备 ID
     * @return 设备实例
     */
    Device getDeviceById(String deviceId);

    /**
     * 根据设备 ID 和 HTTP Servlet 请求获取设备实例。
     *
     * @param deviceId 设备 ID
     * @param request  HTTP Servlet 请求
     * @return 设备实例
     */
    Device getDeviceById(String deviceId, HttpServletRequest request);

    /**
     * 根据设备 ID 和 WURFL 请求获取设备实例。
     *
     * @param deviceId 设备 ID
     * @param request  WURFL 请求
     * @return 设备实例
     */
    Device getDeviceById(String deviceId, WURFLRequest request);
}