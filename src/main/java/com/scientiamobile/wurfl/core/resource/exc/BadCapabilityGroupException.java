package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * 功能点分配到错误组异常。
 * <p>在校验功能点（Capability）一致性时，如果某个设备将功能点分配到了
 * 与 generic 基础设备中不同的组（Group），则抛出此异常。
 * 所有设备中同一功能点必须属于相同的组，以确保分组一致性。</p>
 */

public class BadCapabilityGroupException extends CapabilityConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 实际分配的组 ID
     */
    private final String actualGroupId;
    /**
     * 期望的正确组 ID
     */
    private final String expectedGroupId;

    /**
     * 构造功能点分配错误组异常。
     *
     * @param device          关联的设备
     * @param capabilityName  被错误分组的功能点名称
     * @param actualGroupId   功能点实际所属的组 ID
     * @param expectedGroupId 功能点应当属于的正确组 ID
     */
    public BadCapabilityGroupException(ModelDevice device, String capabilityName, String actualGroupId, String expectedGroupId) {
        super(device, capabilityName, "Capability: " + capabilityName + " is defined in group: " + actualGroupId + " istead in group:" + expectedGroupId + " in Device: " + device.getID());
        this.expectedGroupId = expectedGroupId;
        this.actualGroupId = actualGroupId;
    }

    /**
     * 获取功能点应该属于的正确组 ID。
     *
     * @return 正确的组 ID
     */

    public String getRightGroup() {
        return this.expectedGroupId;
    }

    /**
     * 获取功能点实际被分配到的错误组 ID。
     *
     * @return 实际分配的组 ID
     */
    public String getBadGroup() {
        return this.actualGroupId;
    }
}
