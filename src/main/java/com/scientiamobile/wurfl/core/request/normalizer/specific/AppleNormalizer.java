package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppleNormalizer implements UserAgentNormalizer {
  private static final Pattern a = Pattern.compile("^[^/]+?/[\\d\\.]+? \\(i[A-Za-z]+; iOS ([\\d\\.]+); Scale/[\\d\\.]+\\)");

  private static final Pattern b = Pattern.compile("^server-bag \\[iPhone OS,([\\d\\.]+),");

  private static final Pattern c = Pattern.compile("^i(?:Phone|Pad|Pod)\\d+?,\\d+?/([\\d\\.]+)");

  private static final Pattern d = Pattern.compile("^iOSClientSDK/\\d+\\.+[0-9\\.]+ +?\\((Mozilla.+)\\)$");

  private static final Pattern e = Pattern.compile("CPU iOS \\d+?\\.\\d+?");

  private static final Pattern f = Pattern.compile("(CPU(?: iPhone)? OS [\\d\\.]+ like)");

  private static Matcher a(String paramString, Pattern paramPattern) {
    if (paramString != null && paramPattern != null) {
      Matcher matcher = paramPattern.matcher(paramString);
      if (matcher.matches()) {
        return matcher;
      }
    }
    return null;
  }

  public String normalize(String paramString) {
    Matcher matcher2;
    if ((matcher2 = a(paramString, a)) == null)
      matcher2 = a(paramString, b);
    if (matcher2 == null)
      matcher2 = a(paramString, c);
    if (matcher2 != null) {
      String str1 = matcher2.group(1).replace(".", "_");
      StringBuilder stringBuilder = new StringBuilder(50);
      return paramString.contains("iPad") ? stringBuilder.append("Mozilla/5.0 (iPad; CPU OS ").append(str1).append(" like Mac OS X) AppleWebKit/538.39.2 (KHTML, like Gecko) Version/7.0 Mobile/12A4297e Safari/9537.53 ").append(paramString).toString() : (paramString.contains("iPod touch") ? stringBuilder.append("Mozilla/5.0 (iPod touch; CPU iPhone OS ").append(str1).append(" like Mac OS X) AppleWebKit/538.41 (KHTML, like Gecko) Version/7.0 Mobile/12A307 Safari/9537.53 ").append(paramString).toString() : (paramString.contains("iPod") ? stringBuilder.append("Mozilla/5.0 (iPod; CPU iPhone OS ").append(str1).append(" like Mac OS X) AppleWebKit/538.41 (KHTML, like Gecko) Version/7.0 Mobile/12A307 Safari/9537.53 ").append(paramString).toString() : stringBuilder.append("Mozilla/5.0 (iPhone; CPU iPhone OS ").append(str1).append(" like Mac OS X) AppleWebKit/601.1.10 (KHTML, like Gecko) Version/8.0 Mobile/12E155 Safari/600.1.4 ").append(paramString).toString()));
    }
    Pattern pattern = d;
    String str;
    Matcher matcher1;
    if ((matcher1 = (((str = paramString) != null && pattern != null) ? ((matcher1 = pattern.matcher(str)).matches() ? matcher1 : null) : null)) != null)
      return matcher1.group(1);
    if (a(paramString, e) != null) {
      String str1;
      if ((matcher1 = a(str1 = paramString.contains("iPad") ? paramString.replace("CPU iOS", "CPU OS") : paramString.replace("CPU iOS", "CPU iPhone OS"), f)) != null) {
        String str2 = matcher1.group(1).replace(".", "_");
        return str1.replace(" U;", "").replaceAll("CPU(?: iPhone)? OS ([\\d\\.]+) like", str2);
      }
    }
    return paramString;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\specific\AppleNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
