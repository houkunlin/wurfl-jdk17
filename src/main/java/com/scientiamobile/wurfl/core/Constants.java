package com.scientiamobile.wurfl.core;

/**
 * WURFL 核心常量定义接口。
 * <p>定义了 WURFL 设备检测中使用的通用设备 ID、请求头名、能力名等常量，
 * 以及引擎内部使用的标记常量和分隔符。</p>
 */

public class Constants {
    private Constants() {
    }
    /**
     * 通用（未匹配）设备的 ID
     */
    public static final String GENERIC = "generic";
    /**
     * 通用移动设备的 ID
     */
    public static final String GENERIC_MOBILE = "generic_mobile";
    /**
     * 通用 XHTML 设备的 ID
     */
    public static final String GENERIC_XHTML = "generic_xhtml";
    /**
     * 通用 Web 浏览器的 ID
     */
    public static final String GENERIC_WEB_BROWSER = "generic_web_browser";
    /**
     * 通用智能电视浏览器的 ID
     */
    public static final String GENERIC_SMARTTV_BROWSER = "generic_smarttv_browser";
    /**
     * 通用网络爬虫的 ID
     */
    public static final String GENERIC_WEB_CRAWLER = "generic_web_crawler";
    /**
     * 通用 UCWeb 浏览器的 ID
     */
    public static final String GENERIC_UCWEB = "generic_ucweb";
    /**
     * 通用微软移动设备的 ID
     */
    public static final String GENERIC_MS_MOBILE = "generic_ms_mobile";
    /**
     * User-Agent 参数名
     */
    public static final String PARAMETER_UA = "UA";
    /**
     * User-Agent 请求头名
     */
    public static final String USER_AGENT = "User-Agent";
    /**
     * WAP XHTML XML 的 Accept 头值
     */
    public static final String ACCEPT_HEADER_VND_WAP_XHTML_XML = "application/vnd.wap.xhtml+xml";
    /**
     * XHTML XML 的 Accept 头值
     */
    public static final String ACCEPT_HEADER_XHTML_XML = "application/xhtml+xml";
    /**
     * HTML 文本的 Accept 头值
     */
    public static final String ACCEPT_HEADER_TEXT_HTML = "application/text+html";
    /**
     * XHTML 支持级别的能力名
     */
    public static final String CN_XHTML_SUPPORT_LEVEL = "xhtml_support_level";
    /**
     * 首选标记语言的能力名
     */
    public static final String CN_PREFERRED_MARKUP = "preferred_markup";
    /**
     * Servlet 请求属性名：未检测到的设备集合
     */
    public static final String UNDETECTED_WURFL_DEVICES = "com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES";
    /**
     * Servlet 请求属性名：已检测到的设备集合
     */
    public static final String DETECTED_WURFL_DEVICES = "com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES";
    /**
     * 用于触发父类执行的特殊标记值
     */
    public static final String PLEASE_CALL_SUPER = "PLEASE_CALL_SUPER_EXECUTION";
    /**
     * 设备层次结构信息字符串中的分隔符
     */
    public static final String RIS_DELIMITER = "---";
}
