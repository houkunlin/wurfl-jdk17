package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.util.Set;

/**
 * 能力持有器工厂接口。
 * <p>根据给定的设备模型（{@link ModelDevice}）创建对应的 {@link CapabilitiesHolder} 实例，
 * 并能够获取模型中定义的所有能力名称集合。</p>
 */

public interface CapabilitiesHolderFactory {
    /**
     * 为指定的设备模型创建能力持有器。
     *
     * @param modelDevice 设备模型
     * @return 能力持有器实例
     */
    CapabilitiesHolder create(ModelDevice modelDevice);

    /**
     * 获取模型中定义的所有能力名称。
     *
     * @return 能力名称集合
     */
    Set<String> getModelCapabilities();
}
