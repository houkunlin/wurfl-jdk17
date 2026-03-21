package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;
import java.util.regex.Matcher;

public class IsAppWebview extends a implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = 165298984131843694L;
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    String str1 = paramWURFLRequest.isUrlEncoded() ? paramWURFLRequest.getCleanedDeviceUserAgent() : paramWURFLRequest.getOriginalUserAgent();
    for (String str : h) {
      if (str1.contains(str))
        return "false"; 
    } 
    String str2 = paramDevice.getCapability("device_os");
    String str4 = str1;
    String str3 = paramDevice.getCapability("device_os");
    if (("Android".equals(str3) && str4.contains("; wv) ")))
      return "true"; 
    if ("Android".equals(str2) && str1.contains("Chrome") && !str1.contains("Version"))
      return "false"; 
    if (a(str2, str1) || a(paramDevice, str1, paramWURFLRequest))
      return "true"; 
    str3 = paramWURFLRequest.getHeader("X-Requested-With");
    if (a("Android", str2, str3))
      return "true"; 
    if (g.contains(str3))
      return "false"; 
    if (c.matcher(paramWURFLRequest.getDeviceUserAgent()).find()) {
      Matcher matcher1 = IsApp.a.matcher(str1);
      Matcher matcher2 = IsApp.b.matcher(str1);
      if (matcher1.find() || matcher2.find()) {
        String str;
        return ((matcher1 = e.matcher(str1)).find() && (str = matcher1.group(1).replaceFirst("^0+(?!$)", "")).length() > 0 && str.length() <= 2 && str.charAt(0) < '3') ? "false" : "true";
      } 
      if (f.matcher(str1).find() && !d.matcher(str1).matches())
        return "true"; 
    } 
    return "false";
  }
  
  public String getHandledVirtualCapabilityName() {
    return "is_app_webview";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\IsAppWebview.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */