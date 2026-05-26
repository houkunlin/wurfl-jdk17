package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import java.util.List;

public abstract class HierarchyConsistencyException extends WURFLConsistencyException {
   private static final long serialVersionUID = 10L;
   @SuppressWarnings("serial")
   private List<ModelDevice> hierarchy;

   public HierarchyConsistencyException(List<ModelDevice> hierarchy, String message) {
      super(message);
      this.hierarchy = hierarchy;
   }

   public HierarchyConsistencyException(List<ModelDevice> hierarchy) {
      super((new StringBuilder("Consistency exception in hierarchy: [")).append(StringMatchUtils.hierarchyAsString(hierarchy)).append("]").toString());
      this.hierarchy = hierarchy;
   }

   public List<ModelDevice> getHierarchy() {
      return this.hierarchy;
   }
}
