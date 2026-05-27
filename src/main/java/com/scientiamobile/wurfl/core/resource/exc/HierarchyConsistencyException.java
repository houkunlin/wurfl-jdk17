package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.io.Serial;
import java.util.List;

public abstract class HierarchyConsistencyException extends WURFLConsistencyException {
   @Serial
   private static final long serialVersionUID = 10L;
   @SuppressWarnings("serial")
   private final List<ModelDevice> hierarchy;

   public HierarchyConsistencyException(List<ModelDevice> hierarchy, String message) {
      super(message);
      this.hierarchy = hierarchy;
   }

   public HierarchyConsistencyException(List<ModelDevice> hierarchy) {
      super("Consistency exception in hierarchy: [" + StringMatchUtils.hierarchyAsString(hierarchy) + "]");
      this.hierarchy = hierarchy;
   }

   public List<ModelDevice> getHierarchy() {
      return this.hierarchy;
   }
}
