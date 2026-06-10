package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.EngineTarget;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.Map;

/**
 * Implementation of WURFL Request.
 */

public interface WURFLRequest {
    /**
     * 获取设备 User-Agent 字符串。
     * <p>设备 User-Agent 会按优先级依次检查 {@code Device-Stock-UA}、
     * {@code X-OperaMini-Phone-UA}、{@code X-UCBrowser-Device-UA} 和
     * 标准 {@code User-Agent} 请求头，取第一个有效的值。</p>
     *
     * @return 设备 User-Agent 字符串
     */
    String getDeviceUserAgent();

    /**
     * 获取浏览器 User-Agent 字符串。
     * <p>通常为标准 {@code User-Agent} 请求头的值，但在某些特殊场景下
     * （如 UC 浏览器代理模式）可能与设备 User-Agent 不同。</p>
     *
     * @return 浏览器 User-Agent 字符串
     */
    String getBrowserUserAgent();

    /**
     * 获取经过通用规范化处理后的设备 User-Agent 字符串。
     * <p>通用规范化由 {@link #performGenericNormalization()} 触发，
     * 主要处理 URL 编码、字符集清理等基础层面的规范化操作。</p>
     *
     * @return 通用规范化后的设备 User-Agent
     */
    String getCleanedDeviceUserAgent();

    /**
     * 获取经过完整规范化处理后的设备 User-Agent 字符串。
     * <p>完整规范化在通用规范化的基础上，进一步通过 {@link com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer}
     * 链式处理，用于设备识别前的最终形态。</p>
     *
     * @return 规范化后的设备 User-Agent
     */
    String getNormalizedDeviceUserAgent();

    /**
     * 获取原始的 User-Agent 字符串。
     * <p>返回的值根据 {@link com.scientiamobile.wurfl.core.UserAgentPriority} 策略决定
     * 是设备 User-Agent 还是浏览器 User-Agent。</p>
     *
     * @return 原始 User-Agent 字符串
     */
    String getOriginalUserAgent();

    /**
     * 使用指定的规范化器对 User-Agent 进行规范化处理。
     * <p>规范化结果会存储到 {@link #getNormalizedDeviceUserAgent()} 中，
     * 供后续设备匹配使用。</p>
     *
     * @param normalizer User-Agent 规范化器，若为 null 则直接将当前 cleaned User-Agent 作为规范化结果
     */
    void normalizeUserAgent(UserAgentNormalizer normalizer);

    /**
     * 执行通用规范化处理。
     * <p>使用构造函数传入的通用规范化器对原始 User-Agent 进行处理，
     * 结果更新到 cleaned Device User-Agent 字段中。</p>
     */
    void performGenericNormalization();

    /**
     * 获取 User-Agent Profile (UAProf) URL。
     * <p>UAProf 是 WAP 论坛定义的设备能力描述文件地址，部分移动设备会在请求中携带此信息。</p>
     *
     * @return UAProfile URL 字符串，可能为 null
     */
    String getUserAgentProfile();

    /**
     * 根据名称获取请求头的值。
     *
     * @param headerName 请求头名称
     * @return 请求头的值，若不存在则返回 null
     */
    String getHeader(String headerName);

    /**
     * 获取所有请求头的只读视图。
     *
     * @return 包含所有请求头的不可变 Map
     */
    Map<String, String> getHeaders();

    /**
     * 获取 WURFL 引擎的匹配目标。
     * <p>用于指示引擎在进行设备匹配时采用何种策略或行为模式。</p>
     *
     * @return 引擎匹配目标
     */
    EngineTarget getEngineTarget();

    /**
     * 内部方法：判断是否为移动浏览器。
     * <p>判断逻辑依次检查：是否为桌面浏览器、是否包含移动关键字、是否检测到屏幕尺寸特征。
     * 结果会被缓存以避免重复计算。</p>
     *
     * @return true 表示为移动浏览器
     */
    boolean _internalIsMobileBrowser();

    /**
     * 内部方法：判断是否为桌面浏览器。
     *
     * @return true 表示为桌面浏览器
     */
    boolean _internalIsDesktopBrowser();

    /**
     * 内部方法：通过更深入的分析判断是否为桌面浏览器。
     * <p>相比 {@link #_internalIsDesktopBrowser()}，该方法会综合更多特征进行判断，
     * 如 User-Agent 中是否包含特定的桌面标记组合、是否为智能电视等。</p>
     *
     * @return true 确定为桌面浏览器
     */
    boolean _internalIsDesktopBrowserHeavyDutyAnalysis();

    /**
     * 内部方法：判断是否为爬虫/机器人。
     *
     * @return true 表示为爬虫或机器人
     */
    boolean _internalIsBot();

    /**
     * 内部方法：判断是否为智能电视浏览器。
     *
     * @return true 表示为智能电视浏览器
     */
    boolean _internalIsSmartTvBrowser();

    /**
     * 内部方法：判断是否为邮件客户端。
     *
     * @return true 表示为邮件客户端
     */
    boolean _internalIsEmailClient();

    /**
     * 判断 User-Agent 是否经过 URL 编码。
     *
     * @return true 表示原始 User-Agent 经过了 URL 编码
     */
    boolean isUrlEncoded();

    /**
     * 设置 User-Agent 是否经过 URL 编码。
     *
     * @param urlEncoded 是否经过 URL 编码
     */
    void setUrlEncoded(boolean urlEncoded);
}
