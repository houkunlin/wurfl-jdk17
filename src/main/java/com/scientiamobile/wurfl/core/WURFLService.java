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
    Device getDevice(HttpServletRequest request);

    Device getDevice(WURFLRequest request);

    Device getDevice(String userAgent);

    EngineTarget getEngineTarget();

    void setEngineTarget(EngineTarget engineTarget);

    UserAgentPriority getUserAgentPriority();

    void setUserAgentPriority(UserAgentPriority priority);

    MatcherManager getMatcherManager();

    void setCacheProvider(CacheProvider cacheProvider);

    void reload(WURFLResource wurflResource, WURFLResources wurflResources, String... patches);

    void applyPatches(WURFLResources wurflResources, String... patches);

    void setRequestFactory(WURFLRequestFactoryWithPriority requestFactory);

    Device getDeviceById(String deviceId);

    Device getDeviceById(String deviceId, HttpServletRequest request);

    Device getDeviceById(String deviceId, WURFLRequest request);
}

