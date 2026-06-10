package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * 引用不存在的组异常。
 * <p>在加载设备时，如果设备引用了在 generic 设备中未定义的组（Group），
 * 则抛出此异常。所有设备使用的组必须在 generic 中预先定义。</p>
 */

public class InexistentGroupException extends GroupConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 构造引用不存在组异常。
     *
     * @param device  引用了未知组的设备
     * @param groupId 不存在的组 ID
     */
    public InexistentGroupException(ModelDevice device, String groupId) {
        super(device, groupId, "Device: " + device.getID() + " define unknow group: " + groupId);
    }
}
