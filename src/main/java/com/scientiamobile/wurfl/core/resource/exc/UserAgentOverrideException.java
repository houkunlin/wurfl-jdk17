package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * User-Agent 覆盖异常。
 * <p>在应用补丁文件时，如果补丁设备与基础设备具有相同的设备 ID，但 User-Agent 不相同，
 * 则抛出此异常。补丁操作仅允许修改功能点和回退关系，不允许修改 User-Agent，
 * 因为 User-Agent 与设备 ID 的绑定关系必须保持稳定。</p>
 */

public class UserAgentOverrideException extends UserAgentConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 基础设备中原有的 User-Agent
     */
    private final String existingUserAgent;

    /**
     * 构造 User-Agent 覆盖异常。
     *
     * @param device              被覆盖的基础设备
     * @param overridingUserAgent 补丁设备试图使用的 User-Agent
     * @param existingUserAgent   基础设备中原有的 User-Agent
     */
    public UserAgentOverrideException(ModelDevice device, String overridingUserAgent, String existingUserAgent) {
        super(device, overridingUserAgent, (new StringBuilder("Device: ")).append(device).append(" override defined user-agent: ").append(existingUserAgent).append(" with overriding user-agent:").append(overridingUserAgent).toString());
        this.existingUserAgent = existingUserAgent;
    }

    /**
     * 获取基础设备中原有的 User-Agent。
     *
     * @return 原有的 User-Agent 字符串
     */

    public String getExistUserAgent() {
        return this.existingUserAgent;
    }
}
