package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.List;

public abstract class HierarchyConsistencyException extends WURFLConsistencyException {
   private static final long serialVersionUID = 10L;
   private List a;

   public HierarchyConsistencyException(List var1, String var2) {
      super(var2);
      this.a = var1;
   }

   public HierarchyConsistencyException(List var1) {
      super((new StringBuilder("Consistency exception in hierarchy: [")).append(StringMatchUtils.hierarchyAsString(var1)).append("]").toString());
      this.a = var1;
   }

   public List getHierarchy() {
      return this.a;
   }
}
