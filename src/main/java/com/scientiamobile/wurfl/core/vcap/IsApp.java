package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsApp extends a {
  private static final long serialVersionUID = -2020126634302389944L;
  
  private static final Map j = new HashMap<Object, Object>(2);
  
  private static final Pattern k = Pattern.compile("iP(hone|od|ad)[\\d],[\\d]");
  
  private static final Pattern l = Pattern.compile("com(?:\\.[a-z]+){2,}");
  
  private static final Pattern m = Pattern.compile("net(?:\\.[a-z]+){2,}");
  
  static final Pattern a = Pattern.compile("^.+Mozilla/5.0 \\(Linux; Android ");
  
  static final Pattern b = Pattern.compile(" (?:Mobile )?Safari/[\\d\\.+]+[^\\d\\.+]+");
  
  public String eval(Device paramDevice, WURFLRequest paramWURFLRequest) {
    String str1;
    if (StringMatchUtils.containsAnyOf(str1 = paramWURFLRequest.isUrlEncoded() ? paramWURFLRequest.getCleanedDeviceUserAgent() : paramWURFLRequest.getOriginalUserAgent(), (String[])h.toArray((Object[])new String[h.size()])))
      return "false"; 
    String str4 = str1;
    String str2 = paramDevice.getCapability("device_os");
    String str3 = str2;
    if (("Android".equals(str3) && str4.contains("; wv) ")))
      return "true"; 
    if (a(str2, str1) || a(paramDevice, str1, paramWURFLRequest))
      return "true"; 
    if (a("Android", str2, paramWURFLRequest))
      return "true"; 
    if (c.matcher(str1).find()) {
      Matcher matcher1 = a.matcher(str1);
      Matcher matcher2 = b.matcher(str1);
      if (matcher1.find() || matcher2.find()) {
        Matcher matcher;
        return ((matcher = e.matcher(str1)).find() && a(matcher.group(1)) < 30) ? "false" : "true";
      } 
    } 
    Iterator<String> iterator = i.iterator();
    while (iterator.hasNext()) {
      String str;
      if ((str = iterator.next()).startsWith("#")) {
        if (((Pattern)j.get(str)).matcher(str1).find())
          return "true"; 
        continue;
      } 
      int i = str.length();
      if (str.startsWith("^")) {
        if (str1.startsWith(str.substring(1)))
          return "true"; 
        continue;
      } 
      if (str.charAt(i - 1) == '$') {
        if (str1.indexOf(str.substring(0, --i)) == str1.length() - i)
          return "true"; 
        continue;
      } 
      if (str1.contains(str))
        return "true"; 
    } 
    return "false";
  }
  
  private static int a(String paramString) {
    try {
      return Integer.parseInt(paramString);
    } catch (NumberFormatException numberFormatException) {
      return -1;
    } 
  }
  
  public String getHandledVirtualCapabilityName() {
    return "is_app";
  }
  
  static {
    j.put("#iP(hone|od|ad)[\\d],[\\d]", k);
    j.put("#com(?:\\.[a-z]+){2,}", l);
    j.put("#net(?:\\.[a-z]+){2,}", m);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\IsApp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */