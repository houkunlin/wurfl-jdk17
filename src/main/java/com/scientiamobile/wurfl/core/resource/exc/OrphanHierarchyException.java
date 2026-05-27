package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.List;

public class OrphanHierarchyException extends HierarchyConsistencyException {

    public OrphanHierarchyException(List<ModelDevice> hierarchy) {
        super(hierarchy, "Orphan exception in hierarchy: [" + StringMatchUtils.hierarchyAsString(hierarchy) + "]");
    }
}
