package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.DefaultWURFLRequestFactory;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.HTCMacNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.WebOSNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.AndroidNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.AppleNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.ChromeNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.FirefoxNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.LGNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.MaemoNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.OperaMiniNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.OperaMobiOrTabletOnAndroidNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.OperaNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.SafariNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.ThunderbirdNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.UcwebU2Normalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.UcwebU3Normalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.WindowsPhoneNormalizer;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MatcherManager {
  private S a;
  
  private final transient Logger b = LoggerFactory.getLogger(getClass());
  
  private UserAgentNormalizer c;
  
  private UserAgentNormalizer d;
  
  private UserAgentNormalizer e;
  
  private UserAgentNormalizer f;
  
  private UserAgentNormalizer g;
  
  private UserAgentNormalizer h;
  
  private UserAgentNormalizer i;
  
  private UserAgentNormalizer j;
  
  private UserAgentNormalizer k;
  
  private UserAgentNormalizer l;
  
  public MatcherManager(WURFLModel paramWURFLModel) {
    this.a = a(paramWURFLModel);
  }
  
  private S a(WURFLModel paramWURFLModel) {
    S s1 = new S();
    this.f = (UserAgentNormalizer)new LGNormalizer();
    this.e = (UserAgentNormalizer)new AndroidNormalizer();
    this.d = (UserAgentNormalizer)new OperaMobiOrTabletOnAndroidNormalizer();
    this.h = (UserAgentNormalizer)new FirefoxNormalizer();
    this.i = (UserAgentNormalizer)new SafariNormalizer();
    this.c = (UserAgentNormalizer)new WindowsPhoneNormalizer();
    this.g = (UserAgentNormalizer)new MaemoNormalizer();
    this.j = (UserAgentNormalizer)new HTCMacNormalizer();
    this.k = (UserAgentNormalizer)new WebOSNormalizer();
    this.l = (UserAgentNormalizer)new OperaNormalizer();
    T t = new T();
    Z z = new Z();
    aa aa = new aa();
    ab ab = new ab();
    ac ac = new ac();
    ad ad = new ad();
    ae ae = new ae();
    af af = new af();
    ag ag = new ag();
    U u = new U();
    V v = new V();
    W w = new W();
    X x = new X();
    Y y = new Y();
    y y1 = new y(paramWURFLModel);
    z z1 = new z();
    H h = new H(this.h, paramWURFLModel);
    M m = new M(paramWURFLModel);
    Q q = new Q(paramWURFLModel);
    an an = new an(this.l, paramWURFLModel);
    e e = new e(this.i, paramWURFLModel);
    s1.a(new i(paramWURFLModel));
    s1.a(new N(paramWURFLModel));
    s1.a(new m((UserAgentNormalizer)new UcwebU3Normalizer(), paramWURFLModel));
    s1.a(new l((UserAgentNormalizer)new UcwebU2Normalizer(), paramWURFLModel));
    s1.a(new EmailClientUserAgentMatcher((UserAgentNormalizer)new ThunderbirdNormalizer(), paramWURFLModel));
    s1.a(new p(this.c, paramWURFLModel));
    s1.a(new ap(paramWURFLModel));
    s1.a(new aq(this.d, paramWURFLModel));
    s1.a(new E(paramWURFLModel));
    s1.a(new k(paramWURFLModel));
    s1.a(new aj(paramWURFLModel));
    s1.a(new u(this.e, paramWURFLModel));
    s1.a(new UbuntuTouchOSMatcher(paramWURFLModel));
    s1.a(new TizenMatcher(paramWURFLModel));
    s1.a(new v((UserAgentNormalizer)new AppleNormalizer(), paramWURFLModel));
    s1.a(new am(paramWURFLModel));
    s1.a(new al(paramWURFLModel));
    s1.a(new f(paramWURFLModel));
    s1.a(new w(paramWURFLModel));
    s1.a(new j(paramWURFLModel));
    s1.a(new ah(paramWURFLModel));
    s1.a(af);
    s1.a(u);
    s1.a(new D(paramWURFLModel));
    s1.a(w);
    s1.a(new J(this.j, paramWURFLModel));
    s1.a(new K(paramWURFLModel));
    s1.a(m);
    s1.a(ae);
    s1.a(new O(this.f, paramWURFLModel));
    s1.a(new P(paramWURFLModel));
    s1.a(new R(this.g, paramWURFLModel));
    s1.a(ac);
    s1.a(new ai(paramWURFLModel));
    s1.a(new ak(paramWURFLModel));
    s1.a(aa);
    s1.a(new b(paramWURFLModel));
    s1.a(ad);
    s1.a(x);
    s1.a(ab);
    s1.a(new c(paramWURFLModel));
    s1.a(z);
    s1.a(new g(paramWURFLModel));
    s1.a(ag);
    s1.a(t);
    s1.a(new h(paramWURFLModel));
    s1.a(new d(paramWURFLModel));
    s1.a(v);
    s1.a(new n(paramWURFLModel));
    s1.a(new o(this.k, paramWURFLModel));
    s1.a(new ao((UserAgentNormalizer)new OperaMiniNormalizer(), paramWURFLModel));
    s1.a(new I(paramWURFLModel));
    s1.a(new L(paramWURFLModel));
    s1.a(new q(paramWURFLModel));
    s1.a(new x(paramWURFLModel));
    s1.a(new r(paramWURFLModel));
    s1.a(new DesktopApplicationMatcher(paramWURFLModel));
    s1.a(q);
    s1.a(an);
    s1.a(new B((UserAgentNormalizer)new ChromeNormalizer(), paramWURFLModel));
    s1.a(h);
    s1.a(e);
    s1.a(y);
    s1.a(y1);
    s1.a(z1);
    List list = paramWURFLModel.getAllDevicesAsList();
    S s2 = s1;
    MatcherManager matcherManager = this;
    Validate.notNull(list);
    matcherManager.b.info("model devices: " + list.size());
    byte b = 0;
    Iterator<ModelDevice> iterator = list.iterator();
    while (iterator.hasNext()) {
      ModelDevice modelDevice;
      String str1 = (modelDevice = iterator.next()).getUserAgent();
      String str2 = modelDevice.getID();
      WURFLRequest wURFLRequest;
      (wURFLRequest = (new DefaultWURFLRequestFactory()).createRequest(str1, null)).performGenericNormalization();
      if (s2.a(wURFLRequest, str2)) {
        b++;
        continue;
      } 
      throw new UnsupportedOperationException("no filter found for " + str2 + "; device=" + modelDevice + "; count was " + b);
    } 
    s2.b();
    matcherManager.b.info("model devices filtered: " + b);
    return s1;
  }
  
  public final void reloadModel(WURFLModel paramWURFLModel) {
    this.b.info("reloading the model");
    if (paramWURFLModel == null)
      throw new IllegalArgumentException("no model defined for Matcher Manager"); 
    this.a = a(paramWURFLModel);
  }
  
  public final DeviceInfo matchRequest(WURFLRequest paramWURFLRequest) {
    return this.a.match(paramWURFLRequest);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\MatcherManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
