package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.List;
import org.apache.commons.lang.text.StrBuilder;

public class CircularHierarchyException extends HierarchyConsistencyException {
  private static final long serialVersionUID = 10L;
  
  public CircularHierarchyException(List paramList) {
    super(paramList, (new StrBuilder("Circular hierarchy detected: [ ")).append(StringMatchUtils.hierarchyAsString(paramList)).append("]").toString());
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\CircularHierarchyException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */