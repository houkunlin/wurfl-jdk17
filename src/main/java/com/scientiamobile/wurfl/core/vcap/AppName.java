package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppName implements VirtualCapabilityEvaluator, Serializable {
  private static final List<String> a = new ArrayList<>();

  private static final List<String> b = new ArrayList<>();

  private static final long serialVersionUID = 7704959740704532442L;

  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    String str;
    if (paramWURFLRequest.isUrlEncoded()) {
      str = paramWURFLRequest.getCleanedDeviceUserAgent();
    } else {
      str = paramWURFLRequest.getOriginalUserAgent();
    }
    for (byte b = 0; b < a.size(); b++) {
      if (str.contains(a.get(b)))
        return b.get(b);
    }
    return "Stock Browser";
  }

  public String getHandledVirtualCapabilityName() {
    return "advertised_app_name";
  }

  static {
    a.add("AOLShield");
    a.add(" GSA");
    a.add("Amazon");
    a.add("bdbrowser");
    a.add("FB_IAB");
    a.add("FB4A");
    a.add("FBAV");
    a.add("UCWEB");
    a.add("UCBrowser");
    a.add(" Edge");
    a.add(" Firefox");
    a.add(" FxiOS");
    a.add("Fennec");
    a.add("Flipboard");
    a.add("hola_android");
    a.add("Indeed App");
    a.add("(InstaFollow)");
    a.add("Instagram");
    a.add("Liebao");
    a.add("Line");
    a.add("MicroMessenger");
    a.add("MSIE");
    a.add("NAVER");
    a.add("NokiaBrowser");
    a.add("nyt_android");
    a.add("Onefootball");
    a.add("Opera");
    a.add(" OPR");
    a.add("OviBrowser");
    a.add("nytiphone");
    a.add("Pinterest");
    a.add("Puffin");
    a.add(" Silk");
    a.add("Twitter");
    a.add("uniqlo-app");
    a.add("YaBrowser");
    a.add("YJApp");
    a.add(" CriOS");
    a.add(" Chrome");
    a.add("iCatalog");
    a.add("mobincube");
    a.add("CFNetwork");
    a.add("Dalvik");
    a.add("Darwin");
    a.add("WindowsPhoneAdClient");
    a.add("Windows Phone Ad Client");
    b.add("AOL Shield browser");
    b.add("Google Search");
    b.add("Amazon App");
    b.add("Baidu browser");
    b.add("Facebook");
    b.add("Facebook");
    b.add("Facebook");
    b.add("UCBrowser");
    b.add("UCBrowser");
    b.add("Edge Browser");
    b.add("Firefox browser");
    b.add("Firefox browser");
    b.add("Fennec browser");
    b.add("Flipboard");
    b.add("Hola VPN");
    b.add("Indeed");
    b.add("InstaFollow");
    b.add("Instagram");
    b.add("Liebao");
    b.add("LINE");
    b.add("WeChat");
    b.add("Internet Explorer");
    b.add("Naver Search");
    b.add("Nokia Browser");
    b.add("New York Times");
    b.add("Onefootball");
    b.add("Opera browser");
    b.add("Opera browser");
    b.add("Nokia Ovi Browser");
    b.add("New York Times");
    b.add("Pinterest");
    b.add("Puffin browser");
    b.add("Silk browser");
    b.add("Twitter");
    b.add("Uniqlo");
    b.add("Yandex browser");
    b.add("Yahoo Japan");
    b.add("Chrome browser");
    b.add("Chrome browser");
    b.add("iCatalog");
    b.add("Mobincube app builder");
    b.add("Native CFNetwork application");
    b.add("Native Android application");
    b.add("Native CFNetwork application");
    b.add("Windows Phone native ad client");
    b.add("Windows Phone native ad client");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\AppName.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
