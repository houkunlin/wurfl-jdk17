package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import java.util.List;

public class CircularHierarchyException extends HierarchyConsistencyException {
   private static final long serialVersionUID = 10L;

   public CircularHierarchyException(List<ModelDevice> hierarchy) {
      super(hierarchy, (new StringBuilder("Circular hierarchy detected: [ ")).append(StringMatchUtils.hierarchyAsString(hierarchy)).append("]").toString());
   }
}
