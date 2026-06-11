package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import org.apache.commons.lang3.Validate;

import java.util.Set;

/**
 * 默认的能力持有器工厂实现。
 * <p>接收 WURFL 数据模型，为每个 {@link ModelDevice} 创建带缓存的能力持有器
 * （{@link CachingCapabilitiesHolder}），并通过 {@link DeviceCapabilitiesProvider}
 * 从设备模型中加载能力数据。</p>
 */

class DefaultCapabilitiesHolderFactory implements CapabilitiesHolderFactory {
    private static boolean assertionsDisabled = !DefaultCapabilitiesHolderFactory.class.desiredAssertionStatus();
    /**
     * WURFL 数据模型实例
     */
    private WURFLModel wurflModel;

    public DefaultCapabilitiesHolderFactory(WURFLModel wurflModel) {
        if (!assertionsDisabled && wurflModel == null) {
            throw new AssertionError();
        } else {
            this.wurflModel = wurflModel;
        }
    }

    /**
     * 为指定的设备模型创建带缓存的能力持有器。
     * <p>使用设备的缓存大小阈值初始化 {@link CachingCapabilitiesHolder}。</p>
     *
     * @param modelDevice 设备模型
     * @return 能力持有器实例
     */
    @Override
    public CapabilitiesHolder create(ModelDevice modelDevice) {
        Validate.notNull(modelDevice, "modelDevice is null");
        return new CachingCapabilitiesHolder(new DeviceCapabilitiesProvider(modelDevice, this.wurflModel), this.wurflModel.getCapabilityCount());
    }

    /**
     * 获取 WURFL 模型中定义的所有能力名称集合。
     *
     * @return 能力名称集合
     */
    @Override
    public Set<String> getModelCapabilities() {
        return this.wurflModel.getAllCapabilities();
    }
}

