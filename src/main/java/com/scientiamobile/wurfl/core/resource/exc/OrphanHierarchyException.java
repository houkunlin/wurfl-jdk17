package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.List;

public class OrphanHierarchyException extends HierarchyConsistencyException {
   private static final long serialVersionUID = 10L;

   public OrphanHierarchyException(List var1) {
      super(var1, (new StringBuilder("Orphan exception in hierarchy: [")).append(StringMatchUtils.hierarchyAsString(var1)).append("]").toString());
   }
}
