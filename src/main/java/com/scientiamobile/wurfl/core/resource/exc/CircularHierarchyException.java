package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.io.Serial;
import java.util.List;

/**
 * 设备继承关系循环引用异常。
 * <p>在校验设备继承关系时，如果发现设备的 fall_back 形成了循环引用
 * （例如 A→B→C→A），则抛出此异常。循环引用会导致无限递归，
 * 必须被检测并阻止。</p>
 */

public class CircularHierarchyException extends HierarchyConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 构造循环引用异常。
     *
     * @param hierarchy 形成循环引用的设备继承链片段
     */
    public CircularHierarchyException(List<ModelDevice> hierarchy) {
        super(hierarchy, "Circular hierarchy detected: [ " + StringMatchUtils.hierarchyAsString(hierarchy) + "]");
    }
}
