package com.scientiamobile.wurfl.core.matchers;

/**
 * User-Agent 回退规则，定义了所有匹配策略失败时的兜底规则。
 * <p>每条规则包含关键字和对应的设备 ID，当 User-Agent 包含该关键字时返回对应的设备 ID。</p>
 */

final class UserAgentFallbackRule {
    /**
     * 用于匹配 User-Agent 的关键字
     */
    final String keyword;
    /** 匹配成功时返回的通用设备 ID */
    final String deviceId;

    /**
     * 创建一条 User-Agent 回退规则。
     *
     * @param keyword  用于匹配 User-Agent 的关键字
     * @param deviceId 匹配成功时返回的设备 ID
     */

    UserAgentFallbackRule(String keyword, String deviceId) {
        this.keyword = keyword;
        this.deviceId = deviceId;
    }
}

