package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.io.Serial;
import java.util.List;

public class CircularHierarchyException extends HierarchyConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CircularHierarchyException(List<ModelDevice> hierarchy) {
        super(hierarchy, "Circular hierarchy detected: [ " + StringMatchUtils.hierarchyAsString(hierarchy) + "]");
    }
}
