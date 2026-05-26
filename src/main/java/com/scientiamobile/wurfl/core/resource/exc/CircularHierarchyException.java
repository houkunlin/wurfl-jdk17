package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.List;

public class CircularHierarchyException extends HierarchyConsistencyException {
   private static final long serialVersionUID = 10L;

   public CircularHierarchyException(List var1) {
      super(var1, (new StringBuilder("Circular hierarchy detected: [ ")).append(StringMatchUtils.hierarchyAsString(var1)).append("]").toString());
   }
}
