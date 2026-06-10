package com.scientiamobile.wurfl.core.exc;

import java.io.Serial;

/**
 * 当请求的功能组（Group）在 WURFL 数据中未定义时抛出的异常。
 * <p>WURFL 中的功能组用于对相关能力（Capability）进行逻辑分组，
 * 例如 "display" 组包含所有与显示相关的功能。当访问不存在的功能组时抛出此异常。</p>
 */

public class GroupNotDefinedException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 未定义的功能组 ID
     */
    private final String groupId;

    /**
     * 使用指定的功能组 ID 和错误信息创建异常实例。
     *
     * @param groupId 未定义的功能组 ID
     * @param message 异常描述信息
     */
    public GroupNotDefinedException(String groupId, String message) {
        super(message);
        this.groupId = groupId;
    }

    /**
     * 使用指定的功能组 ID 创建异常实例，错误信息自动生成。
     *
     * @param groupId 未定义的功能组 ID
     */
    public GroupNotDefinedException(String groupId) {
        this(groupId, "Group: " + groupId + " is not defined in WURFL");
    }

    /**
     * 获取未定义的功能组 ID。
     *
     * @return 功能组 ID
     */
    public String getGroupId() {
        return this.groupId;
    }
}
