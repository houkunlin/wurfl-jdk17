package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import java.util.List;

public class OrphanHierarchyException extends HierarchyConsistencyException {
   private static final long serialVersionUID = 10L;

   public OrphanHierarchyException(List<ModelDevice> hierarchy) {
      super(hierarchy, "Orphan exception in hierarchy: [" + StringMatchUtils.hierarchyAsString(hierarchy) + "]");
   }
}
