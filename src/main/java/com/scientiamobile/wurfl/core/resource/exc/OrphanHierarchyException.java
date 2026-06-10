package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.io.Serial;
import java.util.List;

/**
 * 设备继承关系孤儿节点异常。
 * <p>在校验设备继承关系时，如果某个设备的 fall_back 引用了不存在的设备 ID，
 * 则抛出此异常。"孤儿" 节点意味着该设备无法通过继承链找到 generic 根设备，
 * 导致设备的能力继承关系断裂。</p>
 */

public class OrphanHierarchyException extends HierarchyConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 构造孤儿节点异常。
     *
     * @param hierarchy 从当前设备到断裂点之间的设备继承链
     */
    public OrphanHierarchyException(List<ModelDevice> hierarchy) {
        super(hierarchy, "Orphan exception in hierarchy: [" + StringMatchUtils.hierarchyAsString(hierarchy) + "]");
    }
}
