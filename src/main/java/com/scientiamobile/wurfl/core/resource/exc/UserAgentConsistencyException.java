package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * User-Agent 一致性校验异常的抽象基类。
 * <p>在校验设备的 User-Agent 字段时，如果发现重复、缺失或覆盖等问题，
 * 则抛出该异常或其子类。该异常携带关联的设备和有问题的 User-Agent 值。</p>
 */

public abstract class UserAgentConsistencyException extends DeviceConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 存在一致性问题的 User-Agent 字符串
     */
    private final String userAgent;

    /**
     * 使用指定设备、User-Agent 和自定义消息构造异常。
     *
     * @param device    关联的设备
     * @param userAgent 存在问题的 User-Agent
     * @param message   自定义异常描述信息
     */
    protected UserAgentConsistencyException(ModelDevice device, String userAgent, String message) {
        super(device, message);
        this.userAgent = userAgent;
    }

    /**
     * 使用指定设备和 User-Agent 构造异常，异常消息会自动包含设备 ID 和 User-Agent 值。
     *
     * @param device    关联的设备
     * @param userAgent 存在问题的 User-Agent
     */
    protected UserAgentConsistencyException(ModelDevice device, String userAgent) {
        super(device, "Device: " + device.getID() + " user-agent: " + userAgent + " consistency exception");
        this.userAgent = userAgent;
    }

    /**
     * 获取存在一致性问题的 User-Agent。
     *
     * @return User-Agent 字符串
     */

    public String getUserAgent() {
        return this.userAgent;
    }
}
