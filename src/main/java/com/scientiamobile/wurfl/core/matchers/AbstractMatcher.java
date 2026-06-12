package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.Constants;
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

    /**
     * Catch-All 回退规则列表，用于 User-Agent 无法被任何匹配器识别时的最终兜底。
     * <p>每条规则包含一个关键字和对应的通用设备 ID，当 User-Agent 包含该关键字时，
     * 回退到对应的通用设备。</p>
     */
    private static final List<UserAgentFallbackRule> CATCH_ALL_FALLBACKS;
    /**
     * 断言校验状态标记，指示 JVM 是否禁用了断言功能。
     * <p>用于在开发/测试环境下触发断言检查，生产环境通常禁用断言。</p>
     */
    private static boolean ASSERTIONS_DISABLED = !AbstractMatcher.class.desiredAssertionStatus();

    // 初始化 Catch-All 回退规则，按关键字匹配 User-Agent 并映射到通用设备 ID
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

    /**
     * User-Agent 规范化器，用于在匹配前对 User-Agent 进行预处理。
     * <p>如果为 {@code null}，则不对 User-Agent 做规范化处理。</p>
     */
    private final UserAgentNormalizer normalizer;

    /**
     * 匹配器过滤器，维护该匹配器能处理的 User-Agent 索引。
     * <p>首次调用 {@link #getFilter()} 时延迟初始化，默认为 {@link DefaultMatcherFilter}。</p>
     */
    private MatcherFilter filter;

    /**
     * 无参构造方法，不绑定 WURFL 模型和规范化器。
     * <p>初始化两个日志记录器（已检测/未检测设备日志）。</p>
     */
    protected AbstractMatcher() {
        LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
        LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
        this.normalizer = null;
    }

    /**
     * 使用 WURFL 模型构造匹配器，验证必需的设备 ID 在模型中存在。
     *
     * @param model WURFL 设备模型，用于校验必需的设备 ID
     */
    protected AbstractMatcher(WURFLModel model) {
        LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
        LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
        this.normalizer = null;
        this.validateRequiredDeviceIds(model);
    }

    /**
     * 使用 User-Agent 规范化器构造匹配器。
     *
     * @param normalizer User-Agent 规范化器，用于匹配前预处理
     */
    protected AbstractMatcher(UserAgentNormalizer normalizer) {
        LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
        LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
        this.normalizer = normalizer;
    }

    /**
     * 使用 User-Agent 规范化器和 WURFL 模型构造匹配器。
     *
     * @param normalizer User-Agent 规范化器，用于匹配前预处理
     * @param model      WURFL 设备模型，用于校验必需的设备 ID
     */
    protected AbstractMatcher(UserAgentNormalizer normalizer, WURFLModel model) {
        LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
        LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
        this.normalizer = normalizer;
        this.validateRequiredDeviceIds(model);
    }

    /**
     * 判断设备 ID 是否为空、空白字符串或等于 "generic"。
     *
     * @param deviceId 设备 ID
     * @return 如果设备 ID 为 blank 或 "generic" 则返回 {@code true}
     */
    private static boolean isBlankOrGeneric(String deviceId) {
        return StringUtils.isBlank(deviceId) || Constants.GENERIC.equals(deviceId);
    }

    /**
     * 返回该对象的字符串表示，即类名。
     *
     * @return 该匹配器的简单类名
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    /**
     * 获取该匹配器需要验证存在的必需设备 ID 集合。
     * <p>子类可以重写此方法返回自身依赖的设备 ID，在构造时由 {@link #validateRequiredDeviceIds} 校验
     * 这些设备 ID 在 WURFL 模型中是否存在。</p>
     *
     * @return 必需设备 ID 集合，默认返回空集合
     */
    protected Set<String> getRequiredDeviceIds() {
        return new HashSet<>();
    }

    /**
     * 校验 WURFL 模型中包含当前匹配器所需的全部设备 ID。
     * <p>如果必需的设备 ID 在模型中不存在，则抛出 {@link MissingDeviceIdConsistencyException}。</p>
     *
     * @param model WURFL 设备模型
     * @throws MissingDeviceIdConsistencyException 如果必需的设备 ID 不存在于模型中
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
     * 获取该匹配器的过滤器实例。
     * <p>如果过滤器尚未初始化，则创建默认的 {@link DefaultMatcherFilter} 实例。</p>
     *
     * @return 匹配器过滤器
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
     * 对请求执行完整的设备匹配流程。
     * <p>匹配流程按优先级依次尝试以下策略：</p>
     * <ol>
     *   <li>规范化 User-Agent</li>
     *   <li>精确匹配（User-Agent 完全命中索引）</li>
     *   <li>确定匹配（基于 RIS 算法的部分匹配）</li>
     *   <li>恢复匹配（基于版本号或关键字的兜底）</li>
     *   <li>Catch-All 回退匹配（基于关键字匹配的最终兜底）</li>
     * </ol>
     *
     * @param request WURFL 请求对象
     * @return 匹配到的设备信息
     */
    @Override
    public DeviceInfo match(WURFLRequest request) {
        // 第一步：规范化 User-Agent
        request.normalizeUserAgent(this.normalizer);
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        String matcherName = this.getMatcherName();
        String bucketMatcherName = this.getBucketMatcherName();
        // 如果规范化为空，直接返回 generic
        if (StringUtils.isBlank(normalizedUserAgent)) {
            return new DeviceInfo(Constants.GENERIC, MatchType.none, matcherName, bucketMatcherName,
                    request.getOriginalUserAgent(), normalizedUserAgent);
        }

        // 第二步：尝试精确匹配（User-Agent 完全命中索引）
        String deviceId = this.getFilter().getIndex().getDeviceIdByUserAgent(normalizedUserAgent);
        if (!isBlankOrGeneric(deviceId)) {
            return new DeviceInfo(deviceId, MatchType.exact, matcherName, bucketMatcherName,
                    request.getOriginalUserAgent(), normalizedUserAgent);
        }

        // 第三步：尝试确定匹配（基于 RIS 算法的最长公共前缀匹配）
        deviceId = this.applyConclusiveMatch(request);
        if (!isBlankOrGeneric(deviceId)) {
            return new DeviceInfo(deviceId, MatchType.conclusive, matcherName, bucketMatcherName,
                    request.getOriginalUserAgent(), normalizedUserAgent);
        }

        // 第四步：尝试恢复匹配（基于版本号或关键字的兜底策略）
        deviceId = this.applyRecoveryMatch(request);
        if (!isBlankOrGeneric(deviceId)) {
            return new DeviceInfo(deviceId, MatchType.recovery, matcherName, bucketMatcherName,
                    request.getOriginalUserAgent(), normalizedUserAgent);
        }

        // 第五步：Catch-All 最终兜底，基于 User-Agent 中的关键字匹配通用设备
        deviceId = resolveFallbackDevice(request);
        return new DeviceInfo(deviceId, MatchType.catchAll, matcherName, bucketMatcherName,
                request.getOriginalUserAgent(), normalizedUserAgent);
    }

    /**
     * 基于 User-Agent 关键字匹配的 Catch-All 回退策略。
     * <p>当所有匹配策略都失败时，通过 User-Agent 中的关键字判断设备类型，</p>
     * <ul>
     *   <li>桌面浏览器 → {@code "generic_web_browser"}</li>
     *   <li>匹配 {@code CATCH_ALL_FALLBACKS} 规则关键字 → 对应通用设备 ID</li>
     *   <li>非 Mozilla 且不含特殊标记 → 日系功能机或通用移动端</li>
     *   <li>其余情况 → {@code "generic_xhtml"}</li>
     * </ul>
     *
     * @param request WURFL 请求对象
     * @return 回退设备 ID
     */
    private static String resolveFallbackDevice(WURFLRequest request) {
        // 桌面浏览器走独立回退逻辑
        if (request._internalIsDesktopBrowserHeavyDutyAnalysis()) {
            return "generic_web_browser";
        }
        String cleanedUa = request.getCleanedDeviceUserAgent();
        // 遍历 Catch-All 规则，匹配关键字则返回对应通用设备
        for (UserAgentFallbackRule fallbackRule : CATCH_ALL_FALLBACKS) {
            if (cleanedUa.contains(fallbackRule.keyword)) {
                return fallbackRule.deviceId;
            }
        }
        // 非 Mozilla 且不含 Obigo 等浏览器标记的，判断为功能机或通用移动端
        if (cleanedUa.indexOf("Mozilla/") <= 0 && !StringMatchUtils.containsAnyOf(cleanedUa, "Obigo", "AU-MIC/2", "AU-MIC-", "AU-OBIGO/", "Teleca Q03B1")) {
            return StringMatchUtils.startsWithAnyOf(cleanedUa, "DoCoMo", "KDDI")
                    ? "docomo_generic_jap_ver1"
                    : (request._internalIsMobileBrowser() ? "generic_mobile" : Constants.GENERIC);
        }
        return "generic_xhtml";
    }

    /**
     * 执行确定匹配，基于 RIS 算法查找最匹配的设备。
     * <p>对请求的规范化 User-Agent 应用 RIS 匹配，在过滤器的 User-Agent 索引中
     * 查找具有最长公共前缀的候选条目。如果匹配结果不为 {@code null}，则返回对应的设备 ID。</p>
     *
     * @param request WURFL 请求对象
     * @return 匹配到的设备 ID，未匹配则返回 "generic"
     */
    protected String applyConclusiveMatch(WURFLRequest request) {
        String normalizedDeviceUserAgent = request.getNormalizedDeviceUserAgent();
        normalizedDeviceUserAgent = this.risMatch(normalizedDeviceUserAgent);
        String deviceId = Constants.GENERIC;
        if (normalizedDeviceUserAgent != null) {
            deviceId = this.getFilter().getIndex().getDeviceIdByUserAgent(normalizedDeviceUserAgent);
        }

        if (deviceId == null) {
            deviceId = Constants.GENERIC;
        }

        return deviceId;
    }

    /**
     * 基于 RIS 算法对 User-Agent 进行部分匹配。
     * <p>找到 User-Agent 中第一个 '/' 字符的索引位置，以此为最小匹配长度阈值，
     * 在匹配器索引的 User-Agent 列表中查找最匹配的候选字符串。</p>
     *
     * @param value 待匹配的 User-Agent 字符串
     * @return 最匹配的候选 User-Agent，无匹配则返回 {@link StringMatchUtils#NULL_STRING}
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
        return Constants.GENERIC;
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

    /**
     * 获取该匹配器的桶（Bucket）匹配器名称。
     *
     * @return 桶匹配器名称，默认返回 "Abstract"
     */
    public String getBucketMatcherName() {
        return "Abstract";
    }

    /**
     * 获取该匹配器的名称。
     *
     * @return 匹配器名称，即当前类的简单类名
     */
    @Override
    public String getMatcherName() {
        return this.getClass().getSimpleName();
    }
}
