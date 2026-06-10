package com.scientiamobile.wurfl.core.exc;

import java.io.Serial;

/**
 * 当请求的能力（Capability）在 WURFL 数据中未定义时抛出的异常。
 * <p>WURFL 中的能力描述了设备的具体属性（如屏幕宽度、是否支持 JavaScript 等），
 * 每个能力属于一个特定的功能组。当访问不存在的功能名称时抛出此异常。</p>
 */

public class CapabilityNotDefinedException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 未定义的能力名称
     */
    private final String capabilityName;

    /**
     * 使用指定的能力名称创建异常实例，错误信息自动生成。
     *
     * @param capabilityName 未定义的能力名称
     */
    public CapabilityNotDefinedException(String capabilityName) {
        this(capabilityName, (new StringBuilder("Capability: ")).append(capabilityName).append(" is not defined in WURFL").toString());
    }

    /**
     * 使用指定的能力名称和错误信息创建异常实例。
     *
     * @param capabilityName 未定义的能力名称
     * @param message        异常描述信息
     */
    public CapabilityNotDefinedException(String capabilityName, String message) {
        super("Capability: " + capabilityName + " - " + message);
        this.capabilityName = capabilityName;
    }

    /**
     * 获取未定义的能力名称。
     *
     * @return 能力名称
     */
    public String getCapabilityName() {
        return this.capabilityName;
    }
}
