package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

import java.util.HashSet;
import java.util.Set;

/**
 * 智能电视（Smart TV）浏览器匹配器。
 * <p>通过调用 WURFLRequest#_internalIsSmartTvBrowser() 来判断请求是否来自智能电视。恢复匹配支持 Tizen、SmartTV、GoogleTV、AppleTV、BoxeeBox、Chromecast 等平台。</p>
 */

final class SmartTvMatcher extends MatcherBase {
    private static final String GOOGLE_TV_BROWSER = "generic_smarttv_googletv_browser";
    private static final String APPLE_TV_BROWSER = "generic_smarttv_appletv_browser";
    private static final String BOXEEBOX_BROWSER = "generic_smarttv_boxeebox_browser";
    private static final String CHROMECAST = "generic_smarttv_chromecast";
    private static final String TIZEN_3_0 = "generic_tizen_smarttv_3_0";
    private static final String TIZEN_2_4 = "generic_tizen_smarttv_2_4";
    private static final String TIZEN_2_3 = "generic_tizen_smarttv_2_3";
    private static final String TIZEN_GENERIC = "generic_tizen_smarttv";
    private static final String GENERIC_SMARTTV_BROWSER = "generic_smarttv_browser";

    public SmartTvMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_SMARTTV_BROWSER);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return request._internalIsSmartTvBrowser();
    }
    /**
     * SmartTV 匹配器不执行确定匹配，直接返回 {@code null}，让流程进入恢复匹配阶段。
     *
     * @param request WURFL 请求对象
     * @return 始终返回 {@code null}
     */
    @Override
    protected String applyConclusiveMatch(WURFLRequest request) {
        return null;
    }
    /**
     * 恢复匹配策略：根据 User-Agent 中包含的智能电视平台关键字返回对应的通用设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        if (normalizedUserAgent.contains("Tizen 3.0")) {
            return TIZEN_3_0;
        } else if (normalizedUserAgent.contains("Tizen 2.4")) {
            return TIZEN_2_4;
        } else if (normalizedUserAgent.contains("Tizen 2.3")) {
            return TIZEN_2_3;
        } else if (normalizedUserAgent.contains("Tizen")) {
            return TIZEN_GENERIC;
        } else if (normalizedUserAgent.contains("SmartTV")) {
            return GENERIC_SMARTTV_BROWSER;
        } else if (normalizedUserAgent.contains("GoogleTV")) {
            return GOOGLE_TV_BROWSER;
        } else if (normalizedUserAgent.contains("AppleTV")) {
            return APPLE_TV_BROWSER;
        } else if (normalizedUserAgent.contains("Boxee")) {
            return BOXEEBOX_BROWSER;
        } else {
            return normalizedUserAgent.contains("CrKey") ? CHROMECAST : GENERIC_SMARTTV_BROWSER;
        }
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "SmartTvMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "SmartTV";
    }
}
