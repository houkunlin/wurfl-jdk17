package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UcwebU2Normalizer implements UserAgentNormalizer {
  public static final Pattern IPHONE = Pattern.compile("iPh OS (\\d)_?(\\d)?[ _\\d]?.+; iPh(\\d), ?(\\d)\\) U2");
  
  public static final Pattern WINDOWS_PHONE = Pattern.compile("^UCWEB.+; wds (\\d+)\\.([\\d]+);.+; ([ A-Za-z0-9_-]+); ([ A-Za-z0-9_-]+)\\) U2");
  
  public static final Pattern SYMBIAN = Pattern.compile("^UCWEB.+; S60 V(\\d); .+; (.+)\\) U2");
  
  public static final Pattern JAVA = Pattern.compile("^UCWEB[^\\(]+\\(Java; .+; (.+)\\) U2");
  
  private static final Pattern a = Pattern.compile(";(?! )");
  
  private static final Pattern b = Pattern.compile("(NOKIA.RM-.+?)_.*");
  
  public String normalize(String paramString) {
    String str1;
    if ((str1 = UserAgentUtils.getUcBrowserVersion(paramString, true)) == null)
      return paramString; 
    String str2 = null;
    if (paramString.contains("Adr")) {
      String str3 = UserAgentUtils.getUcAndroidModel(paramString, false);
      String str4 = UserAgentUtils.getUcAndroidVersion(paramString, false);
      if (str3 != null && str4 != null)
        str2 = str4 + " U2Android " + str1 + " " + str3 + "---"; 
    } else if (paramString.contains("iPh OS")) {
      Matcher matcher;
      if ((matcher = IPHONE.matcher(paramString)).find()) {
        String str = matcher.group(1) + "." + matcher.group(2);
        str2 = matcher.group(3) + "." + matcher.group(4);
        str2 = str + " U2iPhone " + str1 + " " + str2 + "---";
      } 
    } else if (paramString.contains("wds")) {
      String str = a.matcher(paramString).replaceAll("; ");
      Matcher matcher;
      if ((matcher = WINDOWS_PHONE.matcher(str)).find()) {
        str2 = matcher.group(1) + "." + matcher.group(2);
        str = (matcher.group(3) + "." + matcher.group(4)).replace("_blocked", "");
        str = b.matcher(str).replaceFirst("$1");
        str2 = str2 + " U2WindowsPhone " + str1 + " " + str + "---";
      } 
    } else if (paramString.contains("Symbian")) {
      Matcher matcher;
      if ((matcher = SYMBIAN.matcher(paramString)).find()) {
        String str = "S60 V" + matcher.group(1);
        str2 = matcher.group(2);
        str2 = str + " U2Symbian " + str1 + " " + str2 + "---";
      } 
    } else {
      Matcher matcher;
      if (paramString.contains("Java") && (matcher = JAVA.matcher(paramString)).find()) {
        String str = "Java";
        str2 = matcher.group(1);
        str2 = str + " U2JavaApp " + str1 + " " + str2 + "---";
      } 
    } 
    return (str2 == null) ? paramString : (str2 + paramString);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\specific\UcwebU2Normalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */