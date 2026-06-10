package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * 组（Group）一致性校验异常的抽象基类。
 * <p>在校验设备的分组信息时，如果发现设备引用了未知的组或分组信息不符合规范，
 * 则使用此类或其子类包装异常。该类持有关联的设备和有问题的组 ID。</p>
 */

public abstract class GroupConsistencyException extends DeviceConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 存在一致性问题的组 ID
     */
    private final String groupId;

    /**
     * 使用指定设备和组 ID 构造异常，异常消息会自动包含设备和组信息。
     *
     * @param device  关联的设备
     * @param groupId 有问题的组 ID
     */
    protected GroupConsistencyException(ModelDevice device, String groupId) {
        super(device, "Group: " + groupId + " in device: " + device.getID() + " consistency exception");
        this.groupId = groupId;
    }

    /**
     * 使用指定设备、组 ID 和自定义消息构造异常。
     *
     * @param device  关联的设备
     * @param groupId 有问题的组 ID
     * @param message 自定义异常描述信息
     */
    protected GroupConsistencyException(ModelDevice device, String groupId, String message) {
        super(device, message);
        this.groupId = groupId;
    }

    /**
     * 获取存在一致性问题的组 ID。
     *
     * @return 组 ID
     */

    public String getGroup() {
        return this.groupId;
    }
}
