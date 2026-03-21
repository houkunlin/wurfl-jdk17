package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.slf4j.LoggerFactory;

final class C implements F {
  private A a;
  
  private G b;
  
  public C(A paramA) {
    LoggerFactory.getLogger(getClass());
    this.a = paramA;
    this.b = new G(this);
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return this.a.canHandle(paramWURFLRequest);
  }
  
  public final boolean a(WURFLRequest paramWURFLRequest, String paramString) {
    this.b.a(this.a.normalize(paramWURFLRequest.getCleanedDeviceUserAgent()), paramString);
    return true;
  }
  
  public final G a() {
    return this.b;
  }
  
  public final String getMatcherName() {
    return this.a.getMatcherName();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\C.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */