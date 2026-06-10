package com.scientiamobile.wurfl.core;

import java.util.Map;

/**
 * 内部设备接口，表示 WURFL 模型中的设备原始数据。
 * <p>提供了访问设备 ID、User-Agent、能力值、设备树结构等基本信息的方法。
 * 这是设备的内部表示层，不包含匹配元数据信息。</p>
 */

public interface InternalDevice {
    /**
     * 获取设备 ID。
     *
     * @return 设备 ID
     */
    String getId();

    /**
     * 获取 WURFL 数据中定义的设备 User-Agent。
     *
     * @return User-Agent 字符串
     */
    String getWURFLUserAgent();

    /**
     * 获取指定能力名称的值。
     *
     * @param capabilityName 能力名称
     * @return 能力值
     */
    String getCapability(String capabilityName);

    /**
     * 获取能力值并转换为整数。
     *
     * @param capabilityName 能力名称
     * @return 整型的能力值
     */
    int getCapabilityAsInt(String capabilityName);

    /**
     * 获取能力值并转换为布尔值。
     *
     * @param capabilityName 能力名称
     * @return 布尔型的能力值
     */
    boolean getCapabilityAsBool(String capabilityName);

    /**
     * 获取设备的所有能力映射。
     *
     * @return 能力映射
     */
    Map<String, String> getCapabilities();

    /**
     * 判断该设备是否是某个设备树的实际根节点。
     *
     * @return 如果是实际设备根节点返回 {@code true}
     */
    boolean isActualDeviceRoot();

    /**
     * 获取该设备所属设备树的根节点 ID。
     *
     * @return 设备树根节点 ID
     */
    String getDeviceRootId();
}
