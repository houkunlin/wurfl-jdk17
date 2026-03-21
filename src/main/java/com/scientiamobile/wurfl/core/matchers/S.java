package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class S implements A, F {
  private List a = new LinkedList();
  
  private final Logger b = LoggerFactory.getLogger(S.class);
  
  private List c = new LinkedList();
  
  public final void a(A paramA) {
    this.a.add(paramA);
    this.c.add(paramA.getFilter());
  }
  
  public DeviceInfo match(WURFLRequest paramWURFLRequest) {
    Iterator<A> iterator = this.a.iterator();
    while (iterator.hasNext()) {
      A a;
      (a = iterator.next()).getMatcherName();
      if (a.canHandle(paramWURFLRequest))
        return a.match(paramWURFLRequest); 
    } 
    if (this.b.isWarnEnabled())
      this.b.warn("No any matcher can handle the request: " + paramWURFLRequest + ", returning generic device."); 
    return new DeviceInfo("generic", MatchType.none, getMatcherName(), "MatcherChain", paramWURFLRequest.getOriginalUserAgent(), "");
  }
  
  public boolean canHandle(WURFLRequest paramWURFLRequest) {
    return true;
  }
  
  public String normalize(String paramString) {
    return paramString;
  }
  
  public F getFilter() {
    return this;
  }
  
  public String getMatcherName() {
    return "MatcherChain";
  }
  
  public final boolean a(WURFLRequest paramWURFLRequest, String paramString) {
    Iterator<F> iterator = this.c.iterator();
    while (iterator.hasNext()) {
      F f;
      if ((f = iterator.next()).canHandle(paramWURFLRequest)) {
        f.a(paramWURFLRequest, paramString);
        return true;
      } 
    } 
    return false;
  }
  
  public final G a() {
    this.b.warn("A Filter of type MatcherChain should never be asked for its FilteredDevices set.");
    G g = new G(this);
    Iterator<F> iterator = this.c.iterator();
    while (iterator.hasNext()) {
      F f;
      for (String str : (f = iterator.next()).a().a())
        g.a(str, f.a().a(str)); 
    } 
    return g;
  }
  
  public final void b() {
    Iterator<F> iterator = this.c.iterator();
    while (iterator.hasNext()) {
      F f;
      if (f = iterator.next() instanceof S) {
        ((S)f).b();
        continue;
      } 
      f.a().b();
    } 
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\S.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */