package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.List;
import org.apache.commons.lang.text.StrBuilder;

public abstract class HierarchyConsistencyException extends WURFLConsistencyException {
  private static final long serialVersionUID = 10L;
  
  private List a;
  
  public HierarchyConsistencyException(List paramList, String paramString) {
    super(paramString);
    this.a = paramList;
  }
  
  public HierarchyConsistencyException(List paramList) {
    super((new StrBuilder("Consistency exception in hierarchy: [")).append(StringMatchUtils.hierarchyAsString(paramList)).append("]").toString());
    this.a = paramList;
  }
  
  public List getHierarchy() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\HierarchyConsistencyException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */