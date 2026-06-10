package com.scientiamobile.wurfl.core.exc;

import java.io.Serial;

/**
 * 当请求的虚拟能力（Virtual Capability）在 WURFL 中未定义时抛出的异常。
 * <p>虚拟能力与普通能力的区别在于：虚拟能力不是直接从 WURFL 数据中读取的，
 * 而是通过一个或多个基础能力计算推导得出的衍生属性（如设备分类、综合评分等）。
 * 当访问未注册或以不支持的计算方式定义的虚拟能力时抛出此异常。</p>
 */

public class VirtualCapabilityNotDefinedException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 2L;
    /**
     * 未定义的虚拟能力名称
     */
    private final String capabilityName;

    /**
     * 使用指定的虚拟能力名称创建异常实例，错误信息自动生成。
     *
     * @param capabilityName 未定义的虚拟能力名称
     */
    public VirtualCapabilityNotDefinedException(String capabilityName) {
        this(capabilityName, (new StringBuilder("Capability: ")).append(capabilityName).append(" is not defined in WURFL").toString());
    }

    /**
     * 使用指定的虚拟能力名称和错误信息创建异常实例。
     *
     * @param capabilityName 未定义的虚拟能力名称
     * @param message        异常描述信息
     */
    public VirtualCapabilityNotDefinedException(String capabilityName, String message) {
        super("Virtual Capability: " + capabilityName + " - " + message);
        this.capabilityName = capabilityName;
    }

    /**
     * 获取未定义的虚拟能力名称。
     *
     * @return 虚拟能力名称
     */
    public String getCapabilityName() {
        return this.capabilityName;
    }
}
