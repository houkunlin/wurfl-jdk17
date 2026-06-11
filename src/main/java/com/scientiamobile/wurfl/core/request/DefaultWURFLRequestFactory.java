package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.EngineTarget;
import com.scientiamobile.wurfl.core.UserAgentPriority;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.*;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.LoggerFactory;

/**
 * 创建 {@link DefaultWURFLRequest} 实例的工厂类。
 * <p>封装了 User-Agent 解析器、规范化器和优先级策略的配置，
 * 提供多种重载的创建方法以适应不同的调用场景。
 * 默认使用 {@link HttpServletRequestUserAgentResolver} 解析器和内置的规范化器链。</p>
 */

public class DefaultWURFLRequestFactory implements WURFLRequestFactoryWithPriority {
    private final UserAgentResolver userAgentResolver;
    private final UserAgentNormalizer userAgentNormalizer;
    private UserAgentPriority userAgentPriority;

    public DefaultWURFLRequestFactory() {
        this(createDefaultNormalizerChain());
    }

    public DefaultWURFLRequestFactory(UserAgentResolver userAgentResolver) {
        this(userAgentResolver, createDefaultNormalizerChain());
    }

    /**
     * 使用自定义规范化器和默认解析器构造工厂实例。
     *
     * @param userAgentNormalizer User-Agent 规范化器
     */
    public DefaultWURFLRequestFactory(UserAgentNormalizer userAgentNormalizer) {
        this(new HttpServletRequestUserAgentResolver(), userAgentNormalizer);
    }

    public DefaultWURFLRequestFactory(UserAgentResolver userAgentResolver, UserAgentNormalizer userAgentNormalizer) {
        this(userAgentResolver, userAgentNormalizer, UserAgentPriority.OverrideSideloadedBrowserUserAgent);
    }

    /**
     * 使用自定义优先级策略和默认规范化链构造工厂实例。
     *
     * @param userAgentPriority User-Agent 优先级策略（决定当设备 UA 和浏览器 UA 不同时以哪个为准）
     */
    public DefaultWURFLRequestFactory(UserAgentPriority userAgentPriority) {
        this(createDefaultNormalizerChain(), userAgentPriority);
    }

    /**
     * 使用自定义解析器和优先级策略构造工厂实例。
     *
     * @param userAgentResolver User-Agent 解析器
     * @param userAgentPriority User-Agent 优先级策略
     */
    public DefaultWURFLRequestFactory(UserAgentResolver userAgentResolver, UserAgentPriority userAgentPriority) {
        this(userAgentResolver, createDefaultNormalizerChain(), userAgentPriority);
    }

    public DefaultWURFLRequestFactory(UserAgentNormalizer userAgentNormalizer, UserAgentPriority userAgentPriority) {
        this(new HttpServletRequestUserAgentResolver(), userAgentNormalizer, userAgentPriority);
    }

    public DefaultWURFLRequestFactory(UserAgentResolver userAgentResolver, UserAgentNormalizer userAgentNormalizer, UserAgentPriority userAgentPriority) {
        LoggerFactory.getLogger(DefaultWURFLRequestFactory.class);
        Validate.notNull(userAgentResolver, "userAgentResolver is null");
        this.userAgentResolver = userAgentResolver;
        this.userAgentNormalizer = userAgentNormalizer;
        this.userAgentPriority = userAgentPriority;
    }

    /**
     * 创建默认的 User-Agent 规范化链。
     * <p>该链组合了多个基础规范化器，按顺序依次处理：</p>
     * <ol>
     *   <li>{@link UCWebNormalizer} - 修正 UC 浏览器格式不规范的 UA</li>
     *   <li>{@link UPLinkNormalizer} - 移除 UP.Link 代理痕迹</li>
     *   <li>{@link SerialNumberNormalizer} - 掩码序列号信息</li>
     *   <li>{@link LocaleNormalizer} - 标准化语言区域信息</li>
     *   <li>{@link CFNetworkNormalizer} - 规整 CFNetwork 版本号</li>
     *   <li>{@link BlackBerryNormalizer} - 标准化 BlackBerry 标识</li>
     *   <li>{@link GenericAndroidNormalizer} - 精简 Android 版本信息</li>
     *   <li>{@link TransferEncodingNormalizer} - 移除传输编码的标记</li>
     * </ol>
     *
     * @return 默认的规范化器链
     */
    private static UserAgentNormalizerChain createDefaultNormalizerChain() {
        return new UserAgentNormalizerChain(new UserAgentNormalizer[]{new UCWebNormalizer(), new UPLinkNormalizer(), new SerialNumberNormalizer(), new LocaleNormalizer(), new CFNetworkNormalizer(), new BlackBerryNormalizer(), new GenericAndroidNormalizer(), new TransferEncodingNormalizer()});
    }

    /**
     * 从 HttpServletRequest 创建 WURFL 请求对象。
     * <p>自动解析请求中的 User-Agent 和 UAProfile，并提取所有请求头。</p>
     *
     * @param request      Servlet HTTP 请求对象，不能为 null
     * @param engineTarget 引擎匹配目标
     * @return WURFL 请求对象
     * @throws NullPointerException 如果 request 为 null
     */
    @Override
    public WURFLRequest createRequest(HttpServletRequest request, EngineTarget engineTarget) {
        Validate.notNull(request, "The sourceRequest must be not null");
        String userAgent = StringUtils.trimToEmpty(this.userAgentResolver.resolve(request));
        String uaProfile = UserAgentUtils.getUaProfile(request);
        return new DefaultWURFLRequest(userAgent, uaProfile, this.userAgentNormalizer, UserAgentUtils.getHeaders(request), this.userAgentPriority, engineTarget);
    }

    /**
     * 从 User-Agent 字符串创建 WURFL 请求对象。
     * <p>适用于纯 UA 字符串分析的场景，不依赖 Servlet 容器。</p>
     *
     * @param userAgent    User-Agent 字符串
     * @param engineTarget 引擎匹配目标
     * @return WURFL 请求对象
     */
    @Override
    public WURFLRequest createRequest(String userAgent, EngineTarget engineTarget) {
        userAgent = StringUtils.trimToEmpty(userAgent);
        return new DefaultWURFLRequest(userAgent, this.userAgentNormalizer, this.userAgentPriority, engineTarget);
    }

    /**
     * 从 User-Agent 字符串和 UAProfile 创建 WURFL 请求对象。
     *
     * @param userAgent    User-Agent 字符串
     * @param uaProfile    UAProfile URL
     * @param engineTarget 引擎匹配目标
     * @return WURFL 请求对象
     */
    public WURFLRequest createRequest(String userAgent, String uaProfile, EngineTarget engineTarget) {
        userAgent = StringUtils.trimToEmpty(userAgent);
        return new DefaultWURFLRequest(userAgent, uaProfile, this.userAgentNormalizer, this.userAgentPriority, engineTarget);
    }

    /**
     * Creat eequest.
     */
    @Override
    public WURFLRequest createRequest(WURFLHeaderProvider headerProvider, EngineTarget engineTarget) {
        return new DefaultWURFLRequest(this.userAgentNormalizer, headerProvider, this.userAgentPriority, engineTarget);
    }

    /**
     * 获取当前的 User-Agent 优先级策略。
     *
     * @return 当前优先级策略
     */
    @Override
    public UserAgentPriority getUserAgentPriority() {
        return this.userAgentPriority;
    }

    /**
     * 设置 User-Agent 优先级策略。
     *
     * @param userAgentPriority 优先级策略
     */
    @Override
    public void setUserAgentPriority(UserAgentPriority userAgentPriority) {
        this.userAgentPriority = userAgentPriority;
    }
}
