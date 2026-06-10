package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * User-Agent 不唯一异常。
 * <p>在加载 WURFL 设备时，如果两个设备定义了相同的 User-Agent，
 * 则抛出此异常。User-Agent 必须全局唯一，以确保设备匹配的确定性。
 * 该异常同时持有当前设备和已定义该 User-Agent 的设备，便于排查冲突。</p>
 */

public class UserAgentNotUniqueException extends UserAgentConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 已先定义了该 User-Agent 的设备
     */
   private final ModelDevice definingDevice;

    /**
     * 构造 User-Agent 不唯一异常。
     *
     * @param device         当前尝试注册重复 User-Agent 的设备
     * @param userAgent      重复的 User-Agent 值
     * @param definingDevice 已定义该 User-Agent 的设备
     */
    public UserAgentNotUniqueException(ModelDevice device, String userAgent, ModelDevice definingDevice) {
        super(device, userAgent, (new StringBuilder("Device: ")).append(device).append(" define duplicate user-agent: ").append(userAgent).append(" defined by device: ").append(definingDevice).toString());
        this.definingDevice = definingDevice;
    }

    /**
     * 获取已先定义了该 User-Agent 的设备。
     *
     * @return 已定义该 User-Agent 的设备
     */

    public ModelDevice getDefiningDevice() {
        return this.definingDevice;
    }
}
