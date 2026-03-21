package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.List;
import org.apache.commons.lang.text.StrBuilder;

public class OrphanHierarchyException extends HierarchyConsistencyException {
  private static final long serialVersionUID = 10L;
  
  public OrphanHierarchyException(List paramList) {
    super(paramList, (new StrBuilder("Orphan exception in hierarchy: [")).append(StringMatchUtils.hierarchyAsString(paramList)).append("]").toString());
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\OrphanHierarchyException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */