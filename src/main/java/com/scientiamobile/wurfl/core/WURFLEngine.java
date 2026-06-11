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
    String API_VERSION = "1.9.1.0";

    Device getDeviceForRequest(HttpServletRequest request);

    Device getDeviceForRequest(WURFLRequest request);

    Device getDeviceForRequest(String userAgent);

    void load();

    void reload(String wurflPath);

    void reload(String wurflPath, String[] wurflPatchPaths);

    void reload(WURFLResource wurflResource, WURFLResource... patchResources);

    void reload(WURFLResource wurflResource, WURFLResources patchResources);

    boolean replaceRoot(String wurflPath);

    void applyPatches(String... patchPaths);

    void applyPatches(WURFLResource... patchResources);

    void applyPatches(WURFLResources patchResources);

    void setMarkupResolver(MarkupResolver markupResolver);

    void setCapabilitiesHolderFactory(CapabilitiesHolderFactory capabilitiesHolderFactory);

    void setWurflRequestFactory(WURFLRequestFactory wurflRequestFactory);

    void setUserAgentResolver(UserAgentResolver userAgentResolver);

    void setDeviceProvider(DeviceProvider deviceProvider);

    void setCacheProvider(CacheProvider cacheProvider);

    void setCapabilityFilter(String... capabilityFilter);

    void setCapabilityFilter(Collection<String> capabilityFilter);

    EngineTarget getEngineTarget();

    void setEngineTarget(EngineTarget engineTarget);

    UserAgentPriority getUserAgentPriority();

    void setUserAgentPriority(UserAgentPriority userAgentPriority);

    WURFLUtils getWURFLUtils();

    Set<String> getAllVirtualCapabilities();

    Device getDeviceById(String deviceId);

    Device getDeviceById(String deviceId, WURFLRequest request);

    Device getDeviceById(String deviceId, HttpServletRequest request);

    String getAPIVersion();

    Set<String> getAllMandatoryCapabilities();

    Set<String> getAllCapabilities();

    String getRootPath();
}
