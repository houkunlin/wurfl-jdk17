package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.EngineTarget;
import com.scientiamobile.wurfl.core.UserAgentPriority;
import com.scientiamobile.wurfl.core.matchers.EmailClientUserAgentMatcher;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.scientiamobile.wurfl.core.Constants.USER_AGENT;

/**
 * WURFL 请求的默认实现，封装了 HTTP 请求中的设备信息和 User-Agent 相关数据。
 * <p>该类是 WURFL 框架的核心数据载体，负责：</p>
 * <ul>
 *   <li>从多种请求头中提取设备 User-Agent（优先级：Device-Stock-UA > X-OperaMini-Phone-UA > X-UCBrowser-Device-UA > User-Agent）</li>
 *   <li>管理原始、清理后、规范化后三个层次的 User-Agent 数据</li>
 *   <li>缓存设备类型判断结果（移动端、桌面端、爬虫等），避免重复计算</li>
 *   <li>支持 {@link UserAgentPriority} 策略来决定使用哪个 UA 进行匹配</li>
 * </ul>
 */
public class DefaultWURFLRequest implements WURFLRequest, Serializable {
    /**
     * 设备 User-Agent 候选请求头列表，按优先级从高到低排列。
     * <ul>
     *   <li>Device-Stock-UA - 设备出厂预置的 UA</li>
     *   <li>X-OperaMini-Phone-UA - Opera Mini 代理模式下的真实设备 UA</li>
     *   <li>X-UCBrowser-Device-UA - UC 浏览器代理模式下的真实设备 UA</li>
     *   <li>User-Agent - 标准请求头</li>
     * </ul>
     */
    private static final String[] USER_AGENT_HEADERS = new String[]{"Device-Stock-UA", "X-OperaMini-Phone-UA", "X-UCBrowser-Device-UA", USER_AGENT};
    /**
     * 序列化版本号，用于确保序列化兼容性。
     */
    @Serial
    private static final long serialVersionUID = 100L;

    /**
     * WURFL 引擎匹配目标，决定引擎使用何种匹配策略。
     */
    private final EngineTarget engineTarget;

    /**
     * 所有请求头的只读副本，用于后续按名称查询具体的请求头值。
     */
    private final Map<String, String> headers;

    /**
     * 设备 User-Agent，从候选请求头中按优先级提取的第一个有效值。
     */
    private String deviceUserAgent;

    /**
     * 经过通用规范化处理后的设备 User-Agent。
     * <p>通过 {@link #performGenericNormalization()} 执行规范化，
     * 主要处理 URL 编码、字符清理等基础操作。</p>
     */
    private String cleanedDeviceUserAgent;

    /**
     * 经过完整规范化链处理后的设备 User-Agent，用于最终的设备匹配。
     * <p>由 {@link #normalizeUserAgent(UserAgentNormalizer)} 设置。</p>
     */
    private String normalizedDeviceUserAgent;

    /**
     * 浏览器 User-Agent，通常来自标准 {@code User-Agent} 请求头的值。
     */
    private String browserUserAgent;

    /**
     * User-Agent 优先级策略，决定当设备 UA 和浏览器 UA 不同时以哪个为准。
     */
    private final UserAgentPriority userAgentPriority;

    /**
     * User-Agent Profile (UAProf) URL，部分移动设备会携带的设备能力描述文件地址。
     */
    private final String userAgentProfile;

    /**
     * 缓存的是否为移动浏览器的判断结果，null 表示尚未计算。
     */
    private Boolean cachedIsMobileBrowser;

    /**
     * 缓存的 UA 中是否包含移动关键字的检测结果，null 表示尚未计算。
     */
    private Boolean cachedMobileKeywordsDetected;

    /**
     * 缓存的 UA 中是否检测到屏幕尺寸特征的结果，null 表示尚未计算。
     */
    private Boolean cachedScreenSizeDetected;

    /**
     * 缓存的是否为爬虫/机器人的判断结果，null 表示尚未计算。
     */
    private Boolean cachedIsBot;

    /**
     * 缓存的是否为桌面浏览器的判断结果，null 表示尚未计算。
     */
    private Boolean cachedIsDesktopBrowser;

    /**
     * 缓存的是否为桌面浏览器的深度分析结果，null 表示尚未计算。
     */
    private Boolean cachedIsDesktopBrowserHeavyDutyAnalysis;

    /**
     * 缓存的是否为智能电视浏览器的判断结果，null 表示尚未计算。
     */
    private Boolean cachedIsSmartTvBrowser;

    /**
     * 缓存的是否为邮件客户端的判断结果，null 表示尚未计算。
     */
    private Boolean cachedIsEmailClient;

    /**
     * 通用规范化器，用于执行基础的 UA 清理操作（如 URL 解码、字符集清理等）。
     * <p>标记为 transient，不参与序列化。</p>
     */
    private final transient UserAgentNormalizer genericNormalizer;

    /**
     * 标记原始 User-Agent 是否经过 URL 编码。
     */
    private boolean urlEncoded;

    public DefaultWURFLRequest(String userAgent, UserAgentNormalizer genericNormalizer, UserAgentPriority userAgentPriority, EngineTarget engineTarget) {
        this(userAgent, (String) null, genericNormalizer, userAgentPriority, engineTarget);
    }

    public DefaultWURFLRequest(String userAgent, String userAgentProfile, UserAgentNormalizer genericNormalizer, UserAgentPriority userAgentPriority, EngineTarget engineTarget) {
        this(userAgent, userAgentProfile, genericNormalizer, new HashMap<>(), userAgentPriority, engineTarget);
    }

    public DefaultWURFLRequest(String userAgent, String userAgentProfile, UserAgentNormalizer genericNormalizer, Map<String, String> headers, UserAgentPriority userAgentPriority, EngineTarget engineTarget) {
        this.cachedIsMobileBrowser = null;
        this.cachedMobileKeywordsDetected = null;
        this.cachedScreenSizeDetected = null;
        this.cachedIsBot = null;
        this.cachedIsDesktopBrowser = null;
        this.cachedIsDesktopBrowserHeavyDutyAnalysis = null;
        this.cachedIsSmartTvBrowser = null;
        this.cachedIsEmailClient = null;
        this.userAgentPriority = userAgentPriority;
        this.engineTarget = engineTarget;
        this.browserUserAgent = truncateUserAgent(headers.get(USER_AGENT));
        if (this.browserUserAgent == null && userAgent != null) {
            this.browserUserAgent = truncateUserAgent(userAgent);
        }

        this.deviceUserAgent = getFirstAvailableUserAgent(headers);
        if (this.deviceUserAgent == null) {
            this.deviceUserAgent = this.browserUserAgent;
        }

        this.userAgentProfile = userAgentProfile;
        this.headers = headers;
        this.genericNormalizer = genericNormalizer;
        this.cleanedDeviceUserAgent = this.userAgentPriority == UserAgentPriority.OverrideSideloadedBrowserUserAgent ? this.deviceUserAgent : this.browserUserAgent;
        this.urlEncoded = UserAgentUtils.isRawUrlEncoded(userAgent) || UserAgentUtils.hasIIsLoggingStyle(userAgent);
        this.applyUcBrowserDeviceUserAgentOverrideIfNeeded();
    }

    /**
     * 通过 {@link WURFLHeaderProvider} 构造请求对象。
     * <p>适用于非 Servlet 环境或需要自定义请求头的场景，自动从 HeaderProvider 中
     * 提取所有请求头并构建内部的 headers 映射。</p>
     *
     * @param genericNormalizer 通用规范化器
     * @param headerProvider    请求头提供者
     * @param userAgentPriority User-Agent 优先级策略
     * @param engineTarget      引擎匹配目标
     */
    public DefaultWURFLRequest(UserAgentNormalizer genericNormalizer, WURFLHeaderProvider headerProvider, UserAgentPriority userAgentPriority, EngineTarget engineTarget) {
        this.cachedIsMobileBrowser = null;
        this.cachedMobileKeywordsDetected = null;
        this.cachedScreenSizeDetected = null;
        this.cachedIsBot = null;
        this.cachedIsDesktopBrowser = null;
        this.cachedIsDesktopBrowserHeavyDutyAnalysis = null;
        this.cachedIsSmartTvBrowser = null;
        this.cachedIsEmailClient = null;
        this.userAgentPriority = userAgentPriority;
        this.engineTarget = engineTarget;
        this.browserUserAgent = truncateUserAgent(headerProvider.getHeader(USER_AGENT));
        this.deviceUserAgent = getFirstAvailableUserAgent(headerProvider);
        if (this.deviceUserAgent == null) {
            this.deviceUserAgent = this.browserUserAgent;
        }

        this.userAgentProfile = UserAgentUtils.getUaProfile(headerProvider);
        this.headers = new HashMap<>();
        Enumeration<String> headerNames = headerProvider.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            this.headers.put(headerName, headerProvider.getHeader(headerName));
        }

        this.genericNormalizer = genericNormalizer;
        this.cleanedDeviceUserAgent = this.userAgentPriority == UserAgentPriority.OverrideSideloadedBrowserUserAgent ? this.deviceUserAgent : this.browserUserAgent;
        this.urlEncoded = UserAgentUtils.isRawUrlEncoded(this.deviceUserAgent) || UserAgentUtils.hasIIsLoggingStyle(this.deviceUserAgent);
        this.applyUcBrowserDeviceUserAgentOverrideIfNeeded();
    }

    /**
     * Returns the firs tvailabl ese rgent.
     */

    private static String getFirstAvailableUserAgent(WURFLHeaderProvider headerProvider) {
        for (String header : USER_AGENT_HEADERS) {
            String headerValue = headerProvider.getHeader(header);
            if (headerValue != null) {
                return truncateUserAgent(headerValue);
            }
        }

        return null;
    }

    /**
     * 从请求头 Map 中按优先级查找第一个有效的设备 User-Agent。
     * <p>依次检查 {@link #USER_AGENT_HEADERS} 中定义的候选请求头，返回第一个非空的值。</p>
     *
     * @param headers 请求头映射
     * @return 找到的第一个有效 User-Agent，若全部为空则返回 null
     */
    private static String getFirstAvailableUserAgent(Map<String, String> headers) {
        for (String header : USER_AGENT_HEADERS) {
            String headerValue = headers.get(header);
            if (headerValue != null) {
                return truncateUserAgent(headerValue);
            }
        }

        return null;
    }

    /**
     * 截断 User-Agent 字符串，确保其长度不超过 255 个字符。
     * <p>WURFL 规范对 UA 字符串长度有限制，超出部分将被丢弃以避免匹配问题。</p>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 截断后的 User-Agent 字符串，若原始值为 null 则返回 null
     */
    private static String truncateUserAgent(String userAgent) {
        return userAgent != null && userAgent.length() > 255 ? userAgent.substring(0, 255) : userAgent;
    }

    /**
     * 应用 UC 浏览器设备 UA 覆盖逻辑。
     * <p>当请求头中包含 {@code X-UCBrowser-Device-UA} 时，说明当前请求经过 UC 浏览器代理，
     * 此时浏览器 UA 应使用设备 UA 的值进行覆盖，确保后续匹配使用正确的 UA。</p>
     */
    private void applyUcBrowserDeviceUserAgentOverrideIfNeeded() {
        if (this.headers.size() > 0 && this.headers.containsKey("X-UCBrowser-Device-UA")) {
            this.browserUserAgent = this.deviceUserAgent;
        }

    }

    /**
     * Returns the devic ese rgent.
     */
    @Override
    public String getDeviceUserAgent() {
        return this.deviceUserAgent;
    }

    /**
     * Returns the cleane devic ese rgent.
     */
    @Override
    public String getCleanedDeviceUserAgent() {
        return this.cleanedDeviceUserAgent;
    }

    /**
     * Returns the normalize devic ese rgent.
     */
    @Override
    public String getNormalizedDeviceUserAgent() {
        return this.normalizedDeviceUserAgent;
    }

    /**
     * Returns the browse rse rgent.
     */
    @Override
    public String getBrowserUserAgent() {
        return this.browserUserAgent;
    }

    /**
     * Returns the origina lse rgent.
     */
    @Override
    public String getOriginalUserAgent() {
        return this.userAgentPriority == UserAgentPriority.OverrideSideloadedBrowserUserAgent ? this.getDeviceUserAgent() : this.getBrowserUserAgent();
    }

    /**
     * Normaliz ese rgent.
     */
    @Override
    public void normalizeUserAgent(UserAgentNormalizer normalizer) {
        if (normalizer == null) {
            this.normalizedDeviceUserAgent = this.cleanedDeviceUserAgent;
        } else {
            this.normalizedDeviceUserAgent = normalizer.normalize(this.cleanedDeviceUserAgent);
        }
    }

    /**
     * Returns whether this i sr lncoded.
     */
    @Override
    public boolean isUrlEncoded() {
        return this.urlEncoded;
    }

    /**
     * Sets the ur lncoded.
     */
    @Override
    public void setUrlEncoded(boolean urlEncoded) {
        this.urlEncoded = urlEncoded;
    }
    /**
     * 执行通用规范化处理。
     * <p>使用构造函数中传入的 genericNormalizer 对原始 User-Agent 进行基础规范化，
     * 结果更新到 cleanedDeviceUserAgent 字段中。
     * 若 genericNormalizer 为 null 则跳过该处理。</p>
     */
    @Override
    public void performGenericNormalization() {
        if (this.genericNormalizer != null) {
            this.cleanedDeviceUserAgent = this.genericNormalizer.normalize(this.getOriginalUserAgent());
        }

    }

    /**
     * Returns the use rgen trofile.
     */
    @Override
    public String getUserAgentProfile() {
        return this.userAgentProfile;
    }

    /**
     * Returns the header.
     */
    @Override
    public String getHeader(String headerName) {
        return this.headers.get(headerName);
    }
    /**
     * 获取所有请求头的只读视图。
     *
     * @return 不可修改的请求头 Map
     */
    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    /**
     * Returns the engin earget.
     */
    @Override
    public EngineTarget getEngineTarget() {
        return this.engineTarget;
    }
    /**
     * 内部方法：判断当前请求是否来自移动浏览器。
     * <p>判断逻辑如下：</p>
     * <ol>
     *   <li>如果判定为桌面浏览器，则直接返回 false</li>
     *   <li>如果检测到移动关键字，则返回 true</li>
     *   <li>否则通过检测屏幕尺寸特征来判断</li>
     * </ol>
     * <p>计算结果会被缓存以避免重复分析。</p>
     *
     * @return true 表示检测为移动浏览器
     */
    @Override
    public boolean _internalIsMobileBrowser() {
        if (this.cachedIsMobileBrowser == null) {
            if (this._internalIsDesktopBrowser()) {
                this.cachedIsMobileBrowser = false;
            } else if (this.mobileKeywordsDetected()) {
                this.cachedIsMobileBrowser = true;
            } else {
                this.cachedIsMobileBrowser = this.screenSizeDetected();
            }
        }

        return this.cachedIsMobileBrowser;
    }

    /**
     * Mobil eeyword setected.
     */

    public boolean mobileKeywordsDetected() {
        if (this.cachedMobileKeywordsDetected == null) {
            this.cachedMobileKeywordsDetected = UserAgentUtils.mobileKeywordsDetected(this.cleanedDeviceUserAgent);
        }

        return this.cachedMobileKeywordsDetected;
    }

    /**
     * Scree niz eetected.
     */

    public boolean screenSizeDetected() {
        if (this.cachedScreenSizeDetected == null) {
            this.cachedScreenSizeDetected = UserAgentUtils.screenSizeDetected(this.cleanedDeviceUserAgent);
        }

        return this.cachedScreenSizeDetected;
    }

    /**
     * _interna l seskto prowser.
     */
    @Override
    public boolean _internalIsDesktopBrowser() {
        if (this.cachedIsDesktopBrowser == null) {
            this.cachedIsDesktopBrowser = UserAgentUtils.isDesktopBrowser(this.cleanedDeviceUserAgent);
        }

        return this.cachedIsDesktopBrowser;
    }
    /**
     * 内部方法：判断当前请求是否来自智能电视浏览器。
     * <p>使用 {@link UserAgentUtils#isSmartTvBrowser(String)} 进行检测，
     * 结果会被缓存。</p>
     *
     * @return true 表示检测为智能电视浏览器
     */
    @Override
    public boolean _internalIsSmartTvBrowser() {
        if (this.cachedIsSmartTvBrowser == null) {
            this.cachedIsSmartTvBrowser = UserAgentUtils.isSmartTvBrowser(this.cleanedDeviceUserAgent);
        }

        return this.cachedIsSmartTvBrowser;
    }
    /**
     * 内部方法：判断当前请求是否来自爬虫/机器人。
     * <p>使用 {@link UserAgentUtils#isBot(String)} 对原始 User-Agent 进行检测，
     * 结果会被缓存。</p>
     *
     * @return true 表示检测为爬虫或机器人
     */
    @Override
    public boolean _internalIsBot() {
        if (this.cachedIsBot == null) {
            this.cachedIsBot = UserAgentUtils.isBot(this.getOriginalUserAgent());
        }

        return this.cachedIsBot;
    }

    /**
     * _interna l seskto prowse reav yut ynalysis.
     */
    @Override
    public boolean _internalIsDesktopBrowserHeavyDutyAnalysis() {
        if (this.cachedIsDesktopBrowserHeavyDutyAnalysis != null) {
            return this.cachedIsDesktopBrowserHeavyDutyAnalysis;
        }
        if (this._internalIsSmartTvBrowser()) {
            return cacheDesktopHeavyDuty(false);
        }
        String ua = this.cleanedDeviceUserAgent;
        if (StringMatchUtils.containsAllOf(ua, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/")) {
            return cacheDesktopHeavyDuty(false);
        }
        if (ua.contains("Chrome") && !StringMatchUtils.containsAnyOf(ua, "Android", "Ventana", "android", "Tizen")) {
            return cacheDesktopHeavyDuty(true);
        }
        if (this.mobileKeywordsDetected() || ua.contains("PPC")) {
            return cacheDesktopHeavyDuty(false);
        }
        if (ua.contains("Firefox") && !ua.contains("Tablet")) {
            return cacheDesktopHeavyDuty(true);
        }
        if (UserAgentUtils.isDesktopPattern(ua)) {
            return cacheDesktopHeavyDuty(true);
        }
        boolean isOperaOnDesktop = ua.startsWith("Opera/9.80 (Windows NT") || ua.startsWith("Opera/9.80 (Macintosh");
        if (isOperaOnDesktop) {
            return cacheDesktopHeavyDuty(true);
        }
        if (this._internalIsDesktopBrowser()) {
            return cacheDesktopHeavyDuty(true);
        }
        return cacheDesktopHeavyDuty(UserAgentUtils.isIEPattern(ua));
    }

    /**
     * Cach eeskto peav yuty.
     */

    private boolean cacheDesktopHeavyDuty(boolean value) {
        this.cachedIsDesktopBrowserHeavyDutyAnalysis = value;
        return value;
    }
    /**
     * 内部方法：判断当前请求是否来自邮件客户端。
     * <p>通过检查 User-Agent 中是否包含 {@link EmailClientUserAgentMatcher#EMAIL_CLIENTS}
     * 中定义的邮件客户端标识字符串来判断。</p>
     *
     * @return true 表示检测为邮件客户端
     */
    @Override
    public boolean _internalIsEmailClient() {
        if (this.cachedIsEmailClient == null) {
            this.cachedIsEmailClient = StringMatchUtils.containsAnyOf(this.cleanedDeviceUserAgent, EmailClientUserAgentMatcher.EMAIL_CLIENTS.toArray(new String[0]));
        }

        return this.cachedIsEmailClient;
    }

    /**
     * Returns whether this has hode.
     */
    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(35, 79);
        hashCodeBuilder.append(this.getClass()).append(this.deviceUserAgent).append(this.userAgentProfile).toHashCode();
        return hashCodeBuilder.toHashCode();
    }
    /**
     * 比较当前对象与另一个对象是否相等。
     * <p>基于设备 User-Agent 和 UAProfile 两个字段进行比较，</p>
     *
     * @param obj 要比较的引用对象
     * @return true 如果两个对象相等
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof DefaultWURFLRequest other)) {
            return false;
        } else {
            return (new EqualsBuilder()).append(this.deviceUserAgent, other.deviceUserAgent).append(this.userAgentProfile, other.userAgentProfile).isEquals();
        }
    }
    /**
     * 返回当前对象的字符串表示，包含原始 User-Agent 和 UAProfile。
     *
     * @return 字符串表示
     */
    @Override
    public String toString() {
        return "[userAgent: " + this.getOriginalUserAgent() + ", userAgentProfile: " + this.userAgentProfile + "]";
    }
}
