package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

final class I1 extends AbstractA {
  private static String b = "firefox_os_ver1_3_tablet";

  private static String c = "generic_firefox_os";

  private static final Pattern d = Pattern.compile("\\brv:\\d+\\.\\d+(.)");

  private static final Pattern e = Pattern.compile("\\brv:(\\d+\\.\\d+)");

  private static final Map f = new HashMap<Object, Object>();

  private static final List g = new ArrayList();

  public I1(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }

  protected final Set a() {
    HashSet<?> hashSet;
    (hashSet = new HashSet()).addAll(g);
    hashSet.add(b);
    hashSet.add(c);
    return hashSet;
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str;
    return (StringUtils.contains(str = paramWURFLRequest.getCleanedDeviceUserAgent(), "Firefox/") && StringMatchUtils.containsAnyOf(str, new String[] { "Mobile", "Tablet" }));
  }

  protected final String a(String paramString) {
    Matcher matcher;
    return (matcher = d.matcher(paramString)).find() ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, matcher.end(1)) : null;
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    // Byte code:
    //   0: aload_1
    //   1: invokeinterface getNormalizedDeviceUserAgent : ()Ljava/lang/String;
    //   6: dup
    //   7: astore_1
    //   8: astore_2
    //   9: getstatic com/scientiamobile/wurfl/core/matchers/I.e : Ljava/util/regex/Pattern;
    //   12: aload_2
    //   13: invokevirtual matcher : (Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   16: dup
    //   17: astore_2
    //   18: invokevirtual find : ()Z
    //   21: ifeq -> 57
    //   24: aload_2
    //   25: iconst_1
    //   26: invokevirtual group : (I)Ljava/lang/String;
    //   29: astore_2
    //   30: getstatic com/scientiamobile/wurfl/core/matchers/I.f : Ljava/util/Map;
    //   33: aload_2
    //   34: invokeinterface containsKey : (Ljava/lang/Object;)Z
    //   39: ifeq -> 57
    //   42: getstatic com/scientiamobile/wurfl/core/matchers/I.f : Ljava/util/Map;
    //   45: aload_2
    //   46: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   51: checkcast java/lang/String
    //   54: goto -> 59
    //   57: ldc '1.0'
    //   59: ldc '.'
    //   61: ldc '_'
    //   63: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   66: ldc '_0'
    //   68: ldc ''
    //   70: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   73: astore_2
    //   74: new java/lang/StringBuilder
    //   77: dup
    //   78: ldc 'firefox_os_ver'
    //   80: invokespecial <init> : (Ljava/lang/String;)V
    //   83: aload_2
    //   84: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: invokevirtual toString : ()Ljava/lang/String;
    //   90: astore_2
    //   91: aload_1
    //   92: ldc 'Tablet'
    //   94: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   97: ifeq -> 138
    //   100: new java/lang/StringBuilder
    //   103: dup
    //   104: invokespecial <init> : ()V
    //   107: aload_2
    //   108: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: ldc '_tablet'
    //   113: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: invokevirtual toString : ()Ljava/lang/String;
    //   119: astore_1
    //   120: getstatic com/scientiamobile/wurfl/core/matchers/I.g : Ljava/util/List;
    //   123: aload_1
    //   124: invokeinterface contains : (Ljava/lang/Object;)Z
    //   129: ifeq -> 134
    //   132: aload_1
    //   133: areturn
    //   134: getstatic com/scientiamobile/wurfl/core/matchers/I.b : Ljava/lang/String;
    //   137: areturn
    //   138: getstatic com/scientiamobile/wurfl/core/matchers/I.g : Ljava/util/List;
    //   141: aload_2
    //   142: invokeinterface contains : (Ljava/lang/Object;)Z
    //   147: ifeq -> 152
    //   150: aload_2
    //   151: areturn
    //   152: getstatic com/scientiamobile/wurfl/core/matchers/I.c : Ljava/lang/String;
    //   155: areturn
  }

  public final String getMatcherName() {
    return "FirefoxOSMatcher";
  }

  public final String getBucketMatcherName() {
    return "FirefoxOS";
  }

  static {
    f.put("18.0", "1.0");
    f.put("18.1", "1.1");
    f.put("26.0", "1.2");
    f.put("28.0", "1.3");
    f.put("30.0", "1.4");
    f.put("32.0", "2.0");
    f.put("33.0", "2.1");
    f.put("34.0", "2.1");
    f.put("37.0", "2.2");
    f.put("43.0", "2.5");
    g.add("firefox_os_ver1");
    g.add("firefox_os_ver1_1");
    g.add("firefox_os_ver1_2");
    g.add("firefox_os_ver1_3");
    g.add("firefox_os_ver1_4");
    g.add("firefox_os_ver1_4_tablet");
    g.add("firefox_os_ver2_0");
    g.add("firefox_os_ver2_0_tablet");
    g.add("firefox_os_ver2_1");
    g.add("firefox_os_ver2_1_tablet");
    g.add("firefox_os_ver2_2");
    g.add("firefox_os_ver2_2_tablet");
    g.add("firefox_os_ver2_5");
    g.add("firefox_os_ver2_5_tablet");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\I.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
