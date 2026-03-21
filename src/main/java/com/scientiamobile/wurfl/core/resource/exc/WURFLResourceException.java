package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.resource.WURFLResource;
import org.apache.commons.lang.text.StrBuilder;

public class WURFLResourceException extends WURFLRuntimeException {
  private static final long serialVersionUID = 10L;
  
  private WURFLResource a;
  
  public WURFLResourceException(WURFLResource paramWURFLResource) {
    super((new StrBuilder("WURFL resource exception in: ")).append(paramWURFLResource.getInfo()).toString());
    this.a = paramWURFLResource;
  }
  
  public WURFLResourceException(WURFLResource paramWURFLResource, Throwable paramThrowable) {
    super(paramThrowable);
    this.a = paramWURFLResource;
  }
  
  public WURFLResourceException(WURFLResource paramWURFLResource, String paramString) {
    super(paramString);
    this.a = paramWURFLResource;
  }
  
  public WURFLResourceException(WURFLResource paramWURFLResource, String paramString, Throwable paramThrowable) {
    super(paramString, paramThrowable);
    this.a = paramWURFLResource;
  }
  
  public WURFLResource getResource() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\exc\WURFLResourceException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */