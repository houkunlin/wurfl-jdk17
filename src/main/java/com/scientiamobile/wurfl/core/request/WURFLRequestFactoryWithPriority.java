package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.UserAgentPriority;

/**
 * 支持优先级策略的 WURFL 请求工厂接口。
 * <p>在 {@link WURFLRequestFactory} 的基础上，增加了对 {@link UserAgentPriority} 的支持，
 * 允许调用方指定当存在多个 User-Agent（如设备 UA 和浏览器 UA 不同）时的优先选择策略。</p>
 */
public interface WURFLRequestFactoryWithPriority extends WURFLRequestFactory {
    UserAgentPriority getUserAgentPriority();

    void setUserAgentPriority(UserAgentPriority userAgentPriority);
}
