package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.DefaultWURFLRequestFactory;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.HTCMacNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.WebOSNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.*;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public final class MatcherManager {
  private S1 a;

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

  private S1 a(WURFLModel paramWURFLModel) {
    S1 s1 = new S1();
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
    T1 t1 = new T1();
    Z1 z = new Z1();
    aa aa = new aa();
    ab ab = new ab();
    ac ac = new ac();
    ad ad = new ad();
    ae ae = new ae();
    af af = new af();
    ag ag = new ag();
    U1 u1 = new U1();
    V1 v1 = new V1();
    W1 w1 = new W1();
    X1 x1 = new X1();
    Y1 y = new Y1();
    y y1 = new y(paramWURFLModel);
    z z1 = new z();
    Firefox firefox = new Firefox(this.h, paramWURFLModel);
    M1 m1 = new M1(paramWURFLModel);
    Q1 q1 = new Q1(paramWURFLModel);
    an an = new an(this.l, paramWURFLModel);
    e e = new e(this.i, paramWURFLModel);
    s1.a(new i(paramWURFLModel));
    s1.a(new N1(paramWURFLModel));
    s1.a(new m((UserAgentNormalizer)new UcwebU3Normalizer(), paramWURFLModel));
    s1.a(new l((UserAgentNormalizer)new UcwebU2Normalizer(), paramWURFLModel));
    s1.a(new EmailClientUserAgentMatcher((UserAgentNormalizer)new ThunderbirdNormalizer(), paramWURFLModel));
    s1.a(new p(this.c, paramWURFLModel));
    s1.a(new ap(paramWURFLModel));
    s1.a(new aq(this.d, paramWURFLModel));
    s1.a(new E1(paramWURFLModel));
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
    s1.a(u1);
    s1.a(new D1(paramWURFLModel));
    s1.a(w1);
    s1.a(new J1(this.j, paramWURFLModel));
    s1.a(new K1(paramWURFLModel));
    s1.a(m1);
    s1.a(ae);
    s1.a(new O1(this.f, paramWURFLModel));
    s1.a(new P1(paramWURFLModel));
    s1.a(new R1(this.g, paramWURFLModel));
    s1.a(ac);
    s1.a(new ai(paramWURFLModel));
    s1.a(new ak(paramWURFLModel));
    s1.a(aa);
    s1.a(new b(paramWURFLModel));
    s1.a(ad);
    s1.a(x1);
    s1.a(ab);
    s1.a(new c(paramWURFLModel));
    s1.a(z);
    s1.a(new g(paramWURFLModel));
    s1.a(ag);
    s1.a(t1);
    s1.a(new h(paramWURFLModel));
    s1.a(new d(paramWURFLModel));
    s1.a(v1);
    s1.a(new n(paramWURFLModel));
    s1.a(new o(this.k, paramWURFLModel));
    s1.a(new ao((UserAgentNormalizer)new OperaMiniNormalizer(), paramWURFLModel));
    s1.a(new I1(paramWURFLModel));
    s1.a(new L1(paramWURFLModel));
    s1.a(new q(paramWURFLModel));
    s1.a(new x(paramWURFLModel));
    s1.a(new r(paramWURFLModel));
    s1.a(new DesktopApplicationMatcher(paramWURFLModel));
    s1.a(q1);
    s1.a(an);
    s1.a(new GoogleChrome((UserAgentNormalizer)new ChromeNormalizer(), paramWURFLModel));
    s1.a(firefox);
    s1.a(e);
    s1.a(y);
    s1.a(y1);
    s1.a(z1);
    List list = paramWURFLModel.getAllDevicesAsList();
    S1 s12 = s1;
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
      if (s12.a(wURFLRequest, str2)) {
        b++;
        continue;
      }
      throw new UnsupportedOperationException("no filter found for " + str2 + "; device=" + modelDevice + "; count was " + b);
    }
    s12.b();
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
