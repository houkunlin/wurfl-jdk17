package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.io.Serial;
import java.util.List;

/**
 * 设备继承关系一致性校验异常的抽象基类。
 * <p>在验证设备的 fall_back 继承链时，如果发现继承关系存在异常
 * （如循环引用、断裂的引用链等），则使用此类或其子类来包装异常信息。
 * 该类持有从当前设备到异常点之间的完整继承链，便于诊断问题。</p>
 */

public abstract class HierarchyConsistencyException extends WURFLConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 从当前设备到异常点之间的设备继承链
     */
    private final List<ModelDevice> hierarchy;

    /**
     * 使用继承链和自定义消息构造异常。
     *
     * @param hierarchy 设备继承链
     * @param message   自定义异常描述信息
     */
    protected HierarchyConsistencyException(List<ModelDevice> hierarchy, String message) {
        super(message);
        this.hierarchy = hierarchy;
    }

    /**
     * 使用继承链构造异常，异常消息会自动包含继承链的字符串表示。
     *
     * @param hierarchy 设备继承链
     */
    protected HierarchyConsistencyException(List<ModelDevice> hierarchy) {
        super("Consistency exception in hierarchy: [" + StringMatchUtils.hierarchyAsString(hierarchy) + "]");
        this.hierarchy = hierarchy;
    }

    /**
     * 获取有异常的设备继承链。
     *
     * @return 设备继承链列表
     */

    public List<ModelDevice> getHierarchy() {
        return this.hierarchy;
    }
}
