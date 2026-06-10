package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * 设备被重新定义异常。
 * <p>在应用补丁时，如果补丁中的设备与基础设备具有相同的设备 ID，
 * 但关键字段（如 User-Agent 或 fall_back）不一致，则抛出此异常。
 * 补丁只能增强设备的功能点，不能改变其基本标识属性。</p>
 */

public class RedefinedDeviceException extends WURFLConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 使用自定义消息构造异常。
     *
     * @param message 异常描述信息
     */
    public RedefinedDeviceException(String message) {
        super(message);
    }

    /**
     * 构造设备重定义异常，自动生成描述信息。
     *
     * @param newDevice      补丁中试图重定义的新设备
     * @param existingDevice 基础模型中已存在的设备
     * @param redefinedField 被重定义的字段名称（"fall_back" 或 "user agent"）
     */
    public RedefinedDeviceException(ModelDevice newDevice, ModelDevice existingDevice, String redefinedField) {
        this("New device " + newDevice.getID() + " with user-agent " + newDevice.getUserAgent() + " cannot redefine already loaded device with the same WURFL ID and " + redefinedField + " " + (redefinedField.equals("fall_back") ? existingDevice.getFallBack() : existingDevice.getUserAgent()));
    }
}
