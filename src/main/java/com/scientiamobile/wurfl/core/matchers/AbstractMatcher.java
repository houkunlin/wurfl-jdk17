package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 抽象匹配器基类，提供了 User-Agent 匹配流程的骨架实现。
 * <p>该类实现了模板方法模式，定义了匹配的标准流程：</p>
 * <ol>
 *   <li>规范化 User-Agent</li>
 *   <li>尝试精确匹配（精确命中索引）</li>
 *   <li>尝试确定匹配（RIS 算法）</li>
 *   <li>尝试恢复匹配（基于版本号的兜底）</li>
 *   <li>尝试回退设备匹配（最终兜底，如 Catch-All 规则）</li>
 * </ol>
 * <p>子类通常只需覆盖 {@link #canHandle}、{@link #risMatch}、{@link #applyRecoveryMatch} 等方法
 * 即可实现特定的设备或浏览器匹配逻辑。</p>
 */

abstract class AbstractMatcher implements Matcher {
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractMatcher.class);
    private static final List<UserAgentFallbackRule> CATCH_ALL_FALLBACKS;
    private static boolean ASSERTIONS_DISABLED = !AbstractMatcher.class.desiredAssertionStatus();

    static {
        CATCH_ALL_FALLBACKS = new ArrayList<>();
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("CoreMedia", "apple_iphone_coremedia_ver1"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("Windows CE", "generic_ms_mobile"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("UP.Browser/7.2", "opwv_v72_generic"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("UP.Browser/7", "opwv_v7_generic"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("UP.Browser/6.2", "opwv_v62_generic"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("UP.Browser/6", "opwv_v6_generic"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("UP.Browser/5", "upgui_generic"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("UP.Browser/4", "uptext_generic"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("UP.Browser/3", "uptext_generic"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("Series60", "nokia_generic_series60"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("NetFront/3.0", "generic_netfront_ver3"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("ACS-NF/3.0", "generic_netfront_ver3"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("NetFront/3.1", "generic_netfront_ver3_1"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("ACS-NF/3.1", "generic_netfront_ver3_1"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("NetFront/3.2", "generic_netfront_ver3_2"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("ACS-NF/3.2", "generic_netfront_ver3_2"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("NetFront/3.3", "generic_netfront_ver3_3"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("ACS-NF/3.3", "generic_netfront_ver3_3"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("NetFront/3.4", "generic_netfront_ver3_4"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("NetFront/3.5", "generic_netfront_ver3_5"));
        CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("NetFront/4.0", "generic_netfront_ver4_0"));
    }

    private final UserAgentNormalizer normalizer;
    private MatcherFilter filter;

    protected AbstractMatcher() {
        LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
        LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
        this.normalizer = null;
    }

    protected AbstractMatcher(WURFLModel model) {
        LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
        LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
        this.normalizer = null;
        this.validateRequiredDeviceIds(model);
    }

    protected AbstractMatcher(UserAgentNormalizer normalizer) {
        LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
        LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
        this.normalizer = normalizer;
    }

    protected AbstractMatcher(UserAgentNormalizer normalizer, WURFLModel model) {
        LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
        LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
        this.normalizer = normalizer;
        this.validateRequiredDeviceIds(model);
    }

    /**
     * Returns whether this i slan k reneric.
     */

    private static boolean isBlankOrGeneric(String deviceId) {
        return StringUtils.isBlank(deviceId) || "generic".equals(deviceId);
    }

    /**
     * Returns a string representation of this object.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    protected Set<String> getRequiredDeviceIds() {
        return new HashSet<>();
    }

    /**
     * Validat eequire devic eds.
     */

    private void validateRequiredDeviceIds(WURFLModel model) {
        if (model != null) {
            Set<String> allDeviceIds = model.getAllDevicesId();

            for (String deviceId : this.getRequiredDeviceIds()) {
                if (!allDeviceIds.contains(deviceId)) {
                    throw new MissingDeviceIdConsistencyException("wurfl.xml load error - Missing device id " + deviceId + " you may need to update the wurfl.xml file to a more recent version");
                }
            }
        }

    }

    /**
     * Returns the filter.
     */

    public final MatcherFilter getFilter() {
        if (this.filter == null) {
            this.filter = new DefaultMatcherFilter(this);
        }

        return this.filter;
    }

    /**
     * 设置自定义的匹配器过滤器。
     * <p>允许外部注入替代的过滤器实现，用于特殊场景下的索引管理。</p>
     *
     * @param filter 匹配器过滤器实例
     */

    public final void setFilter(MatcherFilter filter) {
        this.filter = filter;
    }

    /**
     * Attempts to match the given request to a device.
     *
     * @param request the WURFL request
     * @return device info for the matched device
     */
    @Override
    public DeviceInfo match(WURFLRequest request) {
        request.normalizeUserAgent(this.normalizer);
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        String matcherName = this.getMatcherName();
        String bucketMatcherName = this.getBucketMatcherName();
        if (StringUtils.isBlank(normalizedUserAgent)) {
            return new DeviceInfo("generic", MatchType.none, matcherName, bucketMatcherName,
                    request.getOriginalUserAgent(), normalizedUserAgent);
        }

        String deviceId = this.getFilter().getIndex().getDeviceIdByUserAgent(normalizedUserAgent);
        if (!isBlankOrGeneric(deviceId)) {
            return new DeviceInfo(deviceId, MatchType.exact, matcherName, bucketMatcherName,
                    request.getOriginalUserAgent(), normalizedUserAgent);
        }

        deviceId = this.applyConclusiveMatch(request);
        if (!isBlankOrGeneric(deviceId)) {
            return new DeviceInfo(deviceId, MatchType.conclusive, matcherName, bucketMatcherName,
                    request.getOriginalUserAgent(), normalizedUserAgent);
        }

        deviceId = this.applyRecoveryMatch(request);
        if (!isBlankOrGeneric(deviceId)) {
            return new DeviceInfo(deviceId, MatchType.recovery, matcherName, bucketMatcherName,
                    request.getOriginalUserAgent(), normalizedUserAgent);
        }

        deviceId = resolveFallbackDevice(request);
        return new DeviceInfo(deviceId, MatchType.catchAll, matcherName, bucketMatcherName,
                request.getOriginalUserAgent(), normalizedUserAgent);
    }

    /**
     * Resolv eallbac kevice.
     */

    private static String resolveFallbackDevice(WURFLRequest request) {
        if (request._internalIsDesktopBrowserHeavyDutyAnalysis()) {
            return "generic_web_browser";
        }
        String cleanedUa = request.getCleanedDeviceUserAgent();
        for (UserAgentFallbackRule fallbackRule : CATCH_ALL_FALLBACKS) {
            if (cleanedUa.contains(fallbackRule.keyword)) {
                return fallbackRule.deviceId;
            }
        }
        if (cleanedUa.indexOf("Mozilla/") <= 0 && !StringMatchUtils.containsAnyOf(cleanedUa, "Obigo", "AU-MIC/2", "AU-MIC-", "AU-OBIGO/", "Teleca Q03B1")) {
            return StringMatchUtils.startsWithAnyOf(cleanedUa, "DoCoMo", "KDDI")
                    ? "docomo_generic_jap_ver1"
                    : (request._internalIsMobileBrowser() ? "generic_mobile" : "generic");
        }
        return "generic_xhtml";
    }

    /**
     * 执行确定匹配.
     */

    protected String applyConclusiveMatch(WURFLRequest request) {
        String normalizedDeviceUserAgent = request.getNormalizedDeviceUserAgent();
        normalizedDeviceUserAgent = this.risMatch(normalizedDeviceUserAgent);
        String deviceId = "generic";
        if (normalizedDeviceUserAgent != null) {
            deviceId = this.getFilter().getIndex().getDeviceIdByUserAgent(normalizedDeviceUserAgent);
        }

        if (deviceId == null) {
            deviceId = "generic";
        }

        return deviceId;
    }

    /**
     * 执行 RIS 匹配.
     */

    protected String risMatch(String value) {
        int firstSlashIndex;
        firstSlashIndex = StringMatchUtils.firstSlash(value);
        return firstSlashIndex == -1 ? StringMatchUtils.NULL_STRING : StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), value, firstSlashIndex);
    }

    /**
     * 执行恢复匹配（Recovery Match），作为确定匹配失败后的兜底策略。
     * <p>基类默认返回 "generic"，子类应覆盖此方法以提供基于版本号或关键字的恢复匹配逻辑。</p>
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配到的设备 ID，默认返回 "generic"
     */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return "generic";
    }

    /**
     * 规范化给定的 User-Agent 字符串。
     * <p>如果当前匹配器配置了 {@link UserAgentNormalizer}，则调用其规范化方法；
     * 否则直接返回原始值。</p>
     *
     * @param value 原始 User-Agent 字符串
     * @return 规范化后的 User-Agent 字符串
     */
    @Override
    public String normalize(String value) {
        return this.normalizer == null ? value : this.normalizer.normalize(value);
    }

    public String getBucketMatcherName() {
        return "Abstract";
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return this.getClass().getSimpleName();
    }
}
