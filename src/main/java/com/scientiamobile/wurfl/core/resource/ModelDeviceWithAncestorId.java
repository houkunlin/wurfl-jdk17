package com.scientiamobile.wurfl.core.resource;

/**
 * 携带祖先 ID 的设备封装。
 * <p>在设备继承链分析过程中，有时需要将设备与其祖先设备的 ID 配对处理。
 * 该类提供了这种配对封装，用于在构建设备继承关系时临时保存祖先引用信息。</p>
 */

public class ModelDeviceWithAncestorId {
    /**
     * 设备对象
     */
    private final ModelDevice modelDevice;
    /**
     * 设备祖先的 ID
     */
    private final String ancestorId;

    /**
     * 创建设备与祖先 ID 的配对。
     *
     * @param modelDevice 设备对象
     * @param ancestorId  祖先设备 ID
     */
    public ModelDeviceWithAncestorId(ModelDevice modelDevice, String ancestorId) {
        this.modelDevice = modelDevice;
        this.ancestorId = ancestorId;
    }

    /**
     * 获取设备对象。
     *
     * @return 设备对象
     */

    public ModelDevice getModelDevice() {
        return this.modelDevice;
    }

    /**
     * 获取祖先设备 ID。
     *
     * @return 祖先设备 ID
     */
    public String getAncestorId() {
        return this.ancestorId;
    }
}
