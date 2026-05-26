package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.List;

public abstract class HierarchyConsistencyException extends WURFLConsistencyException {
   private static final long serialVersionUID = 10L;
   private List hierarchy;

   public HierarchyConsistencyException(List hierarchy, String message) {
      super(message);
      this.hierarchy = hierarchy;
   }

   public HierarchyConsistencyException(List hierarchy) {
      super((new StringBuilder("Consistency exception in hierarchy: [")).append(StringMatchUtils.hierarchyAsString(hierarchy)).append("]").toString());
      this.hierarchy = hierarchy;
   }

   public List getHierarchy() {
      return this.hierarchy;
   }
}
