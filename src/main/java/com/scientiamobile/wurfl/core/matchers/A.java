package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class a implements A {
  protected static final Logger a;
  
  private static final List b;
  
  private F c;
  
  private final UserAgentNormalizer d;
  
  public String toString() {
    return getClass().getSimpleName();
  }
  
  protected Set a() {
    return new HashSet();
  }
  
  private void a(WURFLModel paramWURFLModel) {
    if (paramWURFLModel != null) {
      Set set = paramWURFLModel.getAllDevicesId();
      for (String str : a()) {
        if (!set.contains(str))
          throw new s("wurfl.xml load error - Missing device id " + str + " you may need to update the wurfl.xml file to a more recent version"); 
      } 
    } 
  }
  
  public a() {
    LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
    LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
    this.d = null;
  }
  
  public a(WURFLModel paramWURFLModel) {
    LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
    LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
    this.d = null;
    a(paramWURFLModel);
  }
  
  public a(UserAgentNormalizer paramUserAgentNormalizer) {
    LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
    LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
    this.d = paramUserAgentNormalizer;
  }
  
  public a(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
    LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
    this.d = paramUserAgentNormalizer;
    a(paramWURFLModel);
  }
  
  public final void setFilter(F paramF) {
    this.c = paramF;
  }
  
  public final F getFilter() {
    if (this.c == null)
      this.c = new C(this); 
    return this.c;
  }
  
  public final DeviceInfo match(WURFLRequest paramWURFLRequest) {
    // Byte code:
    //   0: ldc 'generic'
    //   2: astore_2
    //   3: aload_1
    //   4: aload_0
    //   5: getfield d : Lcom/scientiamobile/wurfl/core/request/normalizer/UserAgentNormalizer;
    //   8: invokeinterface normalizeUserAgent : (Lcom/scientiamobile/wurfl/core/request/normalizer/UserAgentNormalizer;)V
    //   13: aload_1
    //   14: invokeinterface getNormalizedDeviceUserAgent : ()Ljava/lang/String;
    //   19: astore_3
    //   20: getstatic com/scientiamobile/wurfl/core/matchers/MatchType.none : Lcom/scientiamobile/wurfl/core/matchers/MatchType;
    //   23: astore #4
    //   25: aload_0
    //   26: invokevirtual getMatcherName : ()Ljava/lang/String;
    //   29: astore #5
    //   31: aload_0
    //   32: invokevirtual getBucketMatcherName : ()Ljava/lang/String;
    //   35: astore #6
    //   37: aload_3
    //   38: invokestatic isBlank : (Ljava/lang/String;)Z
    //   41: ifne -> 306
    //   44: aload_0
    //   45: invokevirtual getFilter : ()Lcom/scientiamobile/wurfl/core/matchers/F;
    //   48: invokeinterface a : ()Lcom/scientiamobile/wurfl/core/matchers/G;
    //   53: aload_3
    //   54: invokevirtual a : (Ljava/lang/String;)Ljava/lang/String;
    //   57: dup
    //   58: astore_2
    //   59: invokestatic b : (Ljava/lang/String;)Z
    //   62: ifeq -> 301
    //   65: aload_0
    //   66: aload_1
    //   67: invokevirtual a : (Lcom/scientiamobile/wurfl/core/request/WURFLRequest;)Ljava/lang/String;
    //   70: dup
    //   71: astore_2
    //   72: invokestatic b : (Ljava/lang/String;)Z
    //   75: ifeq -> 293
    //   78: aload_0
    //   79: aload_1
    //   80: invokevirtual b : (Lcom/scientiamobile/wurfl/core/request/WURFLRequest;)Ljava/lang/String;
    //   83: dup
    //   84: astore_2
    //   85: invokestatic b : (Ljava/lang/String;)Z
    //   88: ifeq -> 285
    //   91: aload_1
    //   92: aload_0
    //   93: invokevirtual getFilter : ()Lcom/scientiamobile/wurfl/core/matchers/F;
    //   96: invokeinterface a : ()Lcom/scientiamobile/wurfl/core/matchers/G;
    //   101: pop
    //   102: dup
    //   103: astore_2
    //   104: invokeinterface _internalIsDesktopBrowserHeavyDutyAnalysis : ()Z
    //   109: ifeq -> 117
    //   112: ldc 'generic_web_browser'
    //   114: goto -> 276
    //   117: aload_2
    //   118: invokeinterface getCleanedDeviceUserAgent : ()Ljava/lang/String;
    //   123: astore #4
    //   125: getstatic com/scientiamobile/wurfl/core/matchers/a.b : Ljava/util/List;
    //   128: invokeinterface iterator : ()Ljava/util/Iterator;
    //   133: astore #7
    //   135: aload #7
    //   137: invokeinterface hasNext : ()Z
    //   142: ifeq -> 181
    //   145: aload #7
    //   147: invokeinterface next : ()Ljava/lang/Object;
    //   152: checkcast com/scientiamobile/wurfl/core/matchers/t
    //   155: astore #8
    //   157: aload #4
    //   159: aload #8
    //   161: getfield a : Ljava/lang/String;
    //   164: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   167: ifeq -> 178
    //   170: aload #8
    //   172: getfield b : Ljava/lang/String;
    //   175: goto -> 276
    //   178: goto -> 135
    //   181: aload #4
    //   183: ldc 'Mozilla/'
    //   185: invokevirtual indexOf : (Ljava/lang/String;)I
    //   188: ifgt -> 228
    //   191: aload #4
    //   193: iconst_5
    //   194: anewarray java/lang/String
    //   197: dup
    //   198: iconst_0
    //   199: ldc 'Obigo'
    //   201: aastore
    //   202: dup
    //   203: iconst_1
    //   204: ldc 'AU-MIC/2'
    //   206: aastore
    //   207: dup
    //   208: iconst_2
    //   209: ldc 'AU-MIC-'
    //   211: aastore
    //   212: dup
    //   213: iconst_3
    //   214: ldc 'AU-OBIGO/'
    //   216: aastore
    //   217: dup
    //   218: iconst_4
    //   219: ldc 'Teleca Q03B1'
    //   221: aastore
    //   222: invokestatic containsAnyOf : (Ljava/lang/String;[Ljava/lang/String;)Z
    //   225: ifeq -> 233
    //   228: ldc 'generic_xhtml'
    //   230: goto -> 276
    //   233: aload #4
    //   235: iconst_2
    //   236: anewarray java/lang/String
    //   239: dup
    //   240: iconst_0
    //   241: ldc 'DoCoMo'
    //   243: aastore
    //   244: dup
    //   245: iconst_1
    //   246: ldc 'KDDI'
    //   248: aastore
    //   249: invokestatic startsWithAnyOf : (Ljava/lang/String;[Ljava/lang/String;)Z
    //   252: ifeq -> 260
    //   255: ldc 'docomo_generic_jap_ver1'
    //   257: goto -> 276
    //   260: aload_2
    //   261: invokeinterface _internalIsMobileBrowser : ()Z
    //   266: ifeq -> 274
    //   269: ldc 'generic_mobile'
    //   271: goto -> 276
    //   274: ldc 'generic'
    //   276: astore_2
    //   277: getstatic com/scientiamobile/wurfl/core/matchers/MatchType.catchAll : Lcom/scientiamobile/wurfl/core/matchers/MatchType;
    //   280: astore #4
    //   282: goto -> 306
    //   285: getstatic com/scientiamobile/wurfl/core/matchers/MatchType.recovery : Lcom/scientiamobile/wurfl/core/matchers/MatchType;
    //   288: astore #4
    //   290: goto -> 306
    //   293: getstatic com/scientiamobile/wurfl/core/matchers/MatchType.conclusive : Lcom/scientiamobile/wurfl/core/matchers/MatchType;
    //   296: astore #4
    //   298: goto -> 306
    //   301: getstatic com/scientiamobile/wurfl/core/matchers/MatchType.exact : Lcom/scientiamobile/wurfl/core/matchers/MatchType;
    //   304: astore #4
    //   306: getstatic com/scientiamobile/wurfl/core/matchers/a.e : Z
    //   309: ifne -> 324
    //   312: aload_2
    //   313: ifnonnull -> 324
    //   316: new java/lang/AssertionError
    //   319: dup
    //   320: invokespecial <init> : ()V
    //   323: athrow
    //   324: new com/scientiamobile/wurfl/core/DeviceInfo
    //   327: dup
    //   328: aload_2
    //   329: aload #4
    //   331: aload #5
    //   333: aload #6
    //   335: aload_1
    //   336: invokeinterface getOriginalUserAgent : ()Ljava/lang/String;
    //   341: aload_3
    //   342: invokespecial <init> : (Ljava/lang/String;Lcom/scientiamobile/wurfl/core/matchers/MatchType;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   345: areturn
  }
  
  protected String a(WURFLRequest paramWURFLRequest) {
    String str1 = paramWURFLRequest.getNormalizedDeviceUserAgent();
    str1 = a(str1);
    String str2 = "generic";
    if (str1 != null)
      str2 = getFilter().a().a(str1); 
    if (str2 == null)
      str2 = "generic"; 
    return str2;
  }
  
  protected String a(String paramString) {
    int i;
    return ((i = StringMatchUtils.firstSlash(paramString)) == -1) ? StringMatchUtils.NULL_STRING : StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
  }
  
  protected String b(WURFLRequest paramWURFLRequest) {
    return "generic";
  }
  
  private static boolean b(String paramString) {
    return (StringUtils.isBlank(paramString) || "generic".equals(paramString));
  }
  
  public final String normalize(String paramString) {
    return (this.d == null) ? paramString : this.d.normalize(paramString);
  }
  
  public String getBucketMatcherName() {
    return "Abstract";
  }
  
  public String getMatcherName() {
    return getClass().getSimpleName();
  }
  
  static {
    a = LoggerFactory.getLogger(a.class);
    (b = new ArrayList<t>()).add(new t("CoreMedia", "apple_iphone_coremedia_ver1"));
    b.add(new t("Windows CE", "generic_ms_mobile"));
    b.add(new t("UP.Browser/7.2", "opwv_v72_generic"));
    b.add(new t("UP.Browser/7", "opwv_v7_generic"));
    b.add(new t("UP.Browser/6.2", "opwv_v62_generic"));
    b.add(new t("UP.Browser/6", "opwv_v6_generic"));
    b.add(new t("UP.Browser/5", "upgui_generic"));
    b.add(new t("UP.Browser/4", "uptext_generic"));
    b.add(new t("UP.Browser/3", "uptext_generic"));
    b.add(new t("Series60", "nokia_generic_series60"));
    b.add(new t("NetFront/3.0", "generic_netfront_ver3"));
    b.add(new t("ACS-NF/3.0", "generic_netfront_ver3"));
    b.add(new t("NetFront/3.1", "generic_netfront_ver3_1"));
    b.add(new t("ACS-NF/3.1", "generic_netfront_ver3_1"));
    b.add(new t("NetFront/3.2", "generic_netfront_ver3_2"));
    b.add(new t("ACS-NF/3.2", "generic_netfront_ver3_2"));
    b.add(new t("NetFront/3.3", "generic_netfront_ver3_3"));
    b.add(new t("ACS-NF/3.3", "generic_netfront_ver3_3"));
    b.add(new t("NetFront/3.4", "generic_netfront_ver3_4"));
    b.add(new t("NetFront/3.5", "generic_netfront_ver3_5"));
    b.add(new t("NetFront/4.0", "generic_netfront_ver4_0"));
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\a.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
