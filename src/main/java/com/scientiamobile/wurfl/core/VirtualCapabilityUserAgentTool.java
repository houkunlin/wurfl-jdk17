package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.util.regex.Pattern;

public final class VirtualCapabilityUserAgentTool {
  private final Pattern a = Pattern.compile("^Mozilla/[0-9]\\.0 \\(Macintosh;(?: U;)?([a-zA-Z_ \\.0-9]+)(?:;)?.+? DesktopApp ([A-Za-z0-9]+)/([\\d\\.]+)\\.?");
  
  private final Pattern b = Pattern.compile("^Mozilla/[0-9]\\.0 \\((?:Windows;|X11;)?(?: U; )?([a-zA-Z_ \\.0-9]+)(?:;)?.+? DesktopApp ([A-Za-z0-9]+)/([\\d\\.]+)\\.?");
  
  private final Pattern c = Pattern.compile("Android(?: |/)([\\d\\.]+).+");
  
  private final Pattern d = Pattern.compile(" Adr(?: |/)([\\d\\.]+).+");
  
  private final Pattern e = Pattern.compile("Android ([\\d\\.]+)");
  
  private final Pattern f = Pattern.compile("^Mozilla/[45]\\.0.+?Android.+?AppleWebKit.+FB(?:AN/|_IAB/|4A)");
  
  private final Pattern g = Pattern.compile("OPR/([\\d\\.]+)");
  
  private final Pattern h = Pattern.compile("^Mozilla\\/[0-9]\\.0 .+?((?:Windows|Linux|PPC|Intel) [a-zA-Z0-9 _\\.\\-]+).+(?:360Browser|360SE)");
  
  private final Pattern i = Pattern.compile("Version\\/.+?Chrome\\/([\\d\\.]+)");
  
  private final Pattern j = Pattern.compile("Version\\/\\d");
  
  private final Pattern k = Pattern.compile("Chrome\\/([\\d\\.]+)");
  
  private final Pattern l = Pattern.compile("(?:Firefox|Fennec)\\/([\\d\\.]+)");
  
  private final Pattern m = Pattern.compile("Opera Mobi\\/.*Version\\/([\\d\\.]+)");
  
  private final Pattern n = Pattern.compile("Opera Mini\\/([\\d\\.]+)");
  
  private final Pattern o = Pattern.compile("Opera Tablet\\/.*Version\\/([\\d\\.]+)");
  
  private final Pattern p = Pattern.compile("UCBrowser\\/([\\d\\.]+)");
  
  private final Pattern q = Pattern.compile("^JUC.*UCWEB([0-9])");
  
  private final Pattern r = Pattern.compile(" Silk/([\\d\\.]+)");
  
  private final Pattern s = Pattern.compile("bdbrowser(?:_i18n)?\\/(\\d+)");
  
  private final Pattern t = Pattern.compile("SamsungBrowser/([\\d\\.]+) Chrome/[\\d\\.]+");
  
  private final Pattern u = Pattern.compile("SamsungBrowser/([\\d\\.]+)");
  
  private final Pattern v = Pattern.compile("^Mozilla\\/[0-9]\\.0 .+?((?:Windows|Linux|PPC|Intel) [a-zA-Z0-9 _\\.\\-]+).+bdbrowser(?:_i18n)?\\/([\\d\\.]+)");
  
  private final Pattern w = Pattern.compile("Mozilla\\/[45]\\.0 \\((iPhone|iPod|iPod touch|iPad);(?: U;)? CPU(?: iPhone|) OS ([\\d_]+) like Mac OS X");
  
  private final Pattern x = Pattern.compile("^[^/]+?/[\\d\\.]+? \\(i[A-Za-z]+; iOS ([\\d\\.]+); Scale/[\\d\\.]+\\)");
  
  private final Pattern y = Pattern.compile("^server-bag \\[iPhone OS,([\\d\\.]+),");
  
  private final Pattern z = Pattern.compile("^i(?:Phone|Pad|Pod|Pod touch)\\d+?,\\d+?/([\\d\\.]+)");
  
  private final Pattern A = Pattern.compile("^Mozilla\\/[45]\\.0.+?like Mac OS X.+?CriOS\\/([\\d\\.]+).+?Mobile\\/[0-9A-Za-z]+ Safari\\/[0-9A-Za-z]+\\.");
  
  private final Pattern B = Pattern.compile("^Mozilla\\/[45]\\.0.+?like Mac OS X.+?FxiOS\\/([\\d\\.]+).+?Mobile\\/[0-9A-Za-z]+ Safari\\/[0-9A-Za-z]+\\.");
  
  private final Pattern C = Pattern.compile("^Mozilla\\/[45]\\.0.+?OS \\d_\\d.+?like Mac OS X.+?AppleWebKit.+?.+UCBrowser\\/([\\d\\.]+)");
  
  private final Pattern D = Pattern.compile("UCWEB/[\\d\\.]+ \\(iOS;.+?OS ([\\d_]+);.+UCBrowser/");
  
  private final Pattern E = Pattern.compile("UCWEB/\\d\\.\\d \\(iOS;.+?OS [\\d_]+;.+UCBrowser/([\\d\\.]+)");
  
  private final Pattern F = Pattern.compile("^Mozilla\\/[45]\\.0.+?like Mac OS X.+?AppleWebKit.+?Mobile\\/[0-9A-Za-z]+.*FBAN");
  
  private final Pattern G = Pattern.compile("^Mozilla.+like Mac OS X.+Version/([\\d\\.]+)");
  
  private final Pattern H = Pattern.compile("Windows Phone(?: OS)? ([\\d\\.]+)");
  
  private final Pattern I = Pattern.compile("UCWEB/\\d\\.\\d \\(Windows;.+?; wds ?([\\d\\.]+?);.+UCBrowser");
  
  private final Pattern J = Pattern.compile("\\bS40OviBrowser\\/([\\d\\.]+)");
  
  private final Pattern K = Pattern.compile("(?:SymbianOS|Series60|S60)/([\\d\\.]+)");
  
  private final Pattern L = Pattern.compile("UCWEB/\\d\\.\\d \\(Symbian;.+?S60 V(\\d+)");
  
  private final Pattern M = Pattern.compile("^Mozilla\\/[45]\\.0 \\(Symbian\\/3");
  
  private final Pattern N = Pattern.compile("NokiaBrowser\\/([\\d\\.]+)");
  
  private final Pattern O = Pattern.compile("Opera Mobi.+Version\\/([\\d\\.]+)");
  
  private final Pattern P = Pattern.compile("UCWEB/[\\d\\.]+ \\(Symbian;.+?UCBrowser/([\\d\\.]+)");
  
  private final Pattern Q = Pattern.compile("(?:BlackBerry)|(?:^Mozilla\\/5.0 \\(BB10; )");
  
  private final Pattern R = Pattern.compile("^BlackBerry[0-9A-Za-z]+?\\/([\\d\\.]+).+?UC Browser\\/?([\\d\\.]+)");
  
  private final Pattern S = Pattern.compile("^UCWEB\\/[0-9]\\.0.+?; [a-zA-Z][a-zA-Z]?\\-[a-zA-Z]?[a-zA-Z]; [0-9]+?\\/([\\d\\.]+).+?UCBrowser\\/?([\\d\\.]+)");
  
  private final Pattern T = Pattern.compile("^BlackBerry[0-9A-Za-z]+?\\/([\\d\\.]+)");
  
  private final Pattern U = Pattern.compile("^Mozilla\\/[45]\\.0 \\(BlackBerry;(?: U;)? BlackBerry.+?Version\\/([\\d\\.]+)");
  
  private final Pattern V = Pattern.compile("^Mozilla/[45]\\.0 \\(BB10; .+?Version/([\\d\\.]+)");
  
  private final Pattern W = Pattern.compile("RIM Tablet OS ([\\d\\.]+).+?Version\\/([\\d\\.]+)");
  
  private final Pattern X = Pattern.compile("NetFront\\/([\\d\\.]+)");
  
  private final Pattern Y = Pattern.compile("Obig[a-zA-Z]+?\\/(Q[0-9\\.ABC]+)");
  
  private final Pattern Z = Pattern.compile("SAMSUNG.+?\\bBada\\/([\\d\\.]+);?.+Dolfin\\/([\\d\\.]+)");
  
  private final Pattern aa = Pattern.compile("UP\\.(?:Browser|Link)\\/([\\d\\.]+)");
  
  private final Pattern ab = Pattern.compile("^Mozilla\\/[45]\\.0 \\((?:Linux; )?webOS\\/([\\d\\.]+)");
  
  private final Pattern ac = Pattern.compile("Opera Mini\\/([\\d\\.]+)");
  
  private final Pattern ad = Pattern.compile("Browser\\/Opera Sync\\/SyncClient.+?Version\\/([\\d\\.]+)");
  
  private final Pattern ae = Pattern.compile("^Mozilla\\/[45]\\.0.+?like Mac OS X.+?OPiOS\\/([\\d\\.]+).+?Mobile\\/[0-9A-Za-z]+ Safari\\/[0-9A-Za-z]+\\.");
  
  private final Pattern af = Pattern.compile("Maemo.+?Firefox\\/([0-9a\\.]+) ");
  
  private final Pattern ag = Pattern.compile("(?:MIDP.+?CLDC)|(?:UNTRUSTED)|(?:MIDP-2.0)");
  
  private final Pattern ah = Pattern.compile("^Mozilla\\/[0-9]\\.0 \\(compatible; MSIE ([\\d\\.a-z]+); ((?:Windows NT [0-9]+\\.[0-9])|(?:Windows [0-9]\\.[0-9])|(?:Windows [0-9]+)|(?:Mac_PowerPC))");
  
  private final Pattern ai = Pattern.compile("^Mozilla/[45]\\.0 \\((Windows NT [\\d\\.]+).+? Edge/([\\d\\.]+)");
  
  private final Pattern aj = Pattern.compile("^Mozilla/[45]\\.0 \\((Windows NT [\\d\\.]+);.+Trident.+; rv:([\\d\\.]+)");
  
  private final Pattern ak = Pattern.compile("^Mozilla\\/[45]\\.[0-9] \\((?:Macintosh; )?([a-zA-Z0-9\\._ ]+)\\) AppleWebKit.+YaBrowser\\/([\\d\\.]+)");
  
  private final Pattern al = Pattern.compile("^Mozilla\\/[0-9]\\.0 \\(Macintosh;(?: U;)?([a-zA-Z_ \\.0-9]+)(?:;)?.+? Chrome\\/([\\d\\.]+)\\.?");
  
  private final Pattern am = Pattern.compile("^Mozilla\\/[0-9]\\.0 \\((?:Windows;|X11;)?(?: U; )?([a-zA-Z_ \\.0-9]+)(?:;)?.+? Chrome\\/([\\d\\.]+)\\.?");
  
  private final Pattern an = Pattern.compile("Mozilla\\/[0-9]\\.0 \\((?:(?:Windows|Macintosh); (?:U; |WOW64; )?)?([a-zA-Z_ \\.0-9]+)(?:;)?.+? Version\\/([\\d\\.]+)\\.?");
  
  private final Pattern ao = Pattern.compile("^Mozilla\\/[0-9]\\.0 .+(Windows [0-9A-Za-z \\.]+;).+?rv:.+?Firefox\\/([\\d\\.]+)");
  
  private final Pattern ap = Pattern.compile("^Mozilla\\/[0-9]\\.0 \\((?:X11|Macintosh); (?:U; |Ubuntu; |)((?:Intel|PPC|Linux) [a-zA-Z0-9\\- \\._\\(\\)]+);.+?rv:.+?Firefox\\/([\\d\\.]+)");
  
  private final Pattern aq = Pattern.compile("^Mozilla\\/[0-9]\\.0 .+?((?:Windows|Linux|PPC|Intel) [a-zA-Z0-9 _\\.\\-]+).+Chrome\\/.+OPR\\/([\\d\\.]+)");
  
  private final Pattern ar = Pattern.compile("^Opera\\/([\\d\\.]+) .+?((?:Windows|Linux|PPC|Intel) [a-zA-Z0-9 _\\.\\-]+) ?;");
  
  private final Pattern as = Pattern.compile("^Opera\\/.+? Version\\/([\\d\\.]+)");
  
  private final Pattern at = Pattern.compile("IEMobile\\/([\\d\\.]+)");
  
  private final Pattern au = Pattern.compile("Edge\\/([\\d\\.]+)");
  
  private final Pattern av = Pattern.compile("UCWEB/\\d\\.\\d \\(Java;.+?UCBrowser/([\\d\\.]+)");
  
  private final Pattern aw = Pattern.compile("^Mozilla\\/[0-9]\\.0 .+(Windows [0-9A-Za-z \\.]+;).+?rv:.+?PaleMoon\\/([\\d\\.]+)");
  
  private final Pattern ax = Pattern.compile("^Mozilla\\/[0-9]\\.0 \\((?:X11|Macintosh); (?:U; |Ubuntu; |)((?:Intel|PPC|Linux) [a-zA-Z0-9\\- \\._\\(\\)]+);.+?rv:.+?PaleMoon\\/([\\d\\.]+)");
  
  private final Pattern ay = Pattern.compile("MobileApp ([A-Za-z0-9 ]+)/([\\d\\.]+)");
  
  private final Pattern az = Pattern.compile("^Mozilla/[45]\\.0.+?Android.+?AppleWebKit.+MobileApp ([A-Za-z0-9 ]+)/([\\d\\.]+)");
  
  private final Pattern aA = Pattern.compile("^Mozilla/[45]\\.0.+?like Mac OS X.+?AppleWebKit.+?Mobile/[0-9A-Za-z]+.*MobileApp ([A-Za-z0-9 ]+)/([\\d\\.]+)");
  
  private final Pattern aB = Pattern.compile(":::Android_(\\d\\.\\d)");
  
  private final Pattern aC = Pattern.compile("^mShop:::Amazon_Android_([\\d\\.]+):::");
  
  public static final Pattern FIREFOX_LINUX_X11 = Pattern.compile("Firefox/([\\d\\.]+)");
  
  public static final Pattern GOOGLE_SEARCH_APP = Pattern.compile("^Mozilla/[45]\\.0.+?like Mac OS X.+?AppleWebKit.+?GSA/([\\d\\.]+) Mobile/");
  
  private static final Pattern aD = Pattern.compile("Version/([\\d\\.A-Z]+)");
  
  private static final Pattern aE = Pattern.compile(" NX/([\\d\\.]+)");
  
  private static final Pattern aF = Pattern.compile("NintendoBrowser/([\\d\\.A-Z]+)");
  
  private final Pattern aG = Pattern.compile("Epiphany/([\\d\\.]+)");
  
  private final Pattern aH = Pattern.compile("Tizen ([\\d\\.]+)");
  
  private static VirtualCapabilityUserAgentTool aI = null;
  
  public static VirtualCapabilityUserAgentTool getInstance() {
    if (aI == null)
      aI = new VirtualCapabilityUserAgentTool(); 
    return aI;
  }
  
  public final VirtualCapabilityDevice assignProperties(WURFLRequest paramWURFLRequest, InternalDevice paramInternalDevice) {
    // Byte code:
    //   0: new com/scientiamobile/wurfl/core/VirtualCapabilityDevice
    //   3: dup
    //   4: aload_1
    //   5: invokespecial <init> : (Lcom/scientiamobile/wurfl/core/request/WURFLRequest;)V
    //   8: astore_1
    //   9: aload_0
    //   10: aload_1
    //   11: aload_2
    //   12: astore #4
    //   14: astore_3
    //   15: astore_2
    //   16: aload_3
    //   17: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   20: aload_3
    //   21: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   24: ldc 'Windows CE'
    //   26: ldc 'Windows Mobile'
    //   28: invokevirtual a : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
    //   31: ifeq -> 46
    //   34: aload_3
    //   35: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   38: ldc 'IE Mobile'
    //   40: invokevirtual a : (Ljava/lang/String;)V
    //   43: goto -> 3682
    //   46: aload_3
    //   47: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   50: iconst_2
    //   51: anewarray java/lang/String
    //   54: dup
    //   55: iconst_0
    //   56: ldc 'Windows Phone'
    //   58: aastore
    //   59: dup
    //   60: iconst_1
    //   61: ldc '; wds'
    //   63: aastore
    //   64: invokestatic containsAnyOf : (Ljava/lang/String;[Ljava/lang/String;)Z
    //   67: ifeq -> 230
    //   70: aload_3
    //   71: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   74: aload_3
    //   75: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   78: aload_2
    //   79: getfield H : Ljava/util/regex/Pattern;
    //   82: ldc 'Windows Phone'
    //   84: iconst_1
    //   85: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   88: ifne -> 112
    //   91: aload_3
    //   92: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   95: aload_3
    //   96: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   99: aload_2
    //   100: getfield I : Ljava/util/regex/Pattern;
    //   103: ldc 'Windows Phone'
    //   105: iconst_1
    //   106: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   109: ifeq -> 230
    //   112: aload_3
    //   113: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   116: aload_3
    //   117: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   120: aload_2
    //   121: getfield ay : Ljava/util/regex/Pattern;
    //   124: iconst_2
    //   125: invokevirtual b : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   128: ifeq -> 166
    //   131: aload_3
    //   132: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   135: new java/lang/StringBuilder
    //   138: dup
    //   139: invokespecial <init> : ()V
    //   142: aload_3
    //   143: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   146: invokevirtual a : ()Ljava/lang/String;
    //   149: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   152: ldc ' Mobile Application'
    //   154: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   157: invokevirtual toString : ()Ljava/lang/String;
    //   160: invokevirtual a : (Ljava/lang/String;)V
    //   163: goto -> 3682
    //   166: aload_3
    //   167: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   170: aload_3
    //   171: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   174: aload_2
    //   175: getfield p : Ljava/util/regex/Pattern;
    //   178: ldc 'UC Browser'
    //   180: iconst_1
    //   181: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   184: ifne -> 3682
    //   187: aload_3
    //   188: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   191: aload_3
    //   192: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   195: aload_2
    //   196: getfield at : Ljava/util/regex/Pattern;
    //   199: ldc 'IE Mobile'
    //   201: iconst_1
    //   202: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   205: ifne -> 3682
    //   208: aload_3
    //   209: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   212: aload_3
    //   213: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   216: aload_2
    //   217: getfield au : Ljava/util/regex/Pattern;
    //   220: ldc 'Edge Mobile'
    //   222: iconst_1
    //   223: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   226: pop
    //   227: goto -> 3682
    //   230: aload_3
    //   231: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   234: ldc 'Nintendo'
    //   236: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   239: ifeq -> 320
    //   242: aload_3
    //   243: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   246: ldc 'Nintendo'
    //   248: invokevirtual a : (Ljava/lang/String;)V
    //   251: aload_3
    //   252: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   255: aload_3
    //   256: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   259: getstatic com/scientiamobile/wurfl/core/VirtualCapabilityUserAgentTool.aD : Ljava/util/regex/Pattern;
    //   262: ldc 'Netfront NX'
    //   264: iconst_1
    //   265: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   268: ifne -> 3682
    //   271: aload_3
    //   272: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   275: aload_3
    //   276: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   279: getstatic com/scientiamobile/wurfl/core/VirtualCapabilityUserAgentTool.aE : Ljava/util/regex/Pattern;
    //   282: ldc 'Netfront NX'
    //   284: iconst_1
    //   285: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   288: ifne -> 3682
    //   291: aload_3
    //   292: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   295: aload_3
    //   296: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   299: getstatic com/scientiamobile/wurfl/core/VirtualCapabilityUserAgentTool.aF : Ljava/util/regex/Pattern;
    //   302: ldc 'Nintendo Browser'
    //   304: iconst_1
    //   305: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   308: ifne -> 3682
    //   311: aload_3
    //   312: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   315: ldc 'Nintendo Browser'
    //   317: invokevirtual a : (Ljava/lang/String;)V
    //   320: aload_3
    //   321: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   324: iconst_3
    //   325: anewarray java/lang/String
    //   328: dup
    //   329: iconst_0
    //   330: ldc 'Android'
    //   332: aastore
    //   333: dup
    //   334: iconst_1
    //   335: ldc 'android'
    //   337: aastore
    //   338: dup
    //   339: iconst_2
    //   340: ldc ' Adr '
    //   342: aastore
    //   343: invokestatic containsAnyOf : (Ljava/lang/String;[Ljava/lang/String;)Z
    //   346: ifeq -> 899
    //   349: aload_3
    //   350: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   353: ldc 'Android'
    //   355: invokevirtual a : (Ljava/lang/String;)V
    //   358: aload_3
    //   359: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   362: aload_3
    //   363: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   366: aload_2
    //   367: getfield c : Ljava/util/regex/Pattern;
    //   370: ldc 'Android'
    //   372: iconst_1
    //   373: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   376: pop
    //   377: aload_3
    //   378: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   381: aload_3
    //   382: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   385: aload_2
    //   386: getfield aB : Ljava/util/regex/Pattern;
    //   389: ldc 'Android'
    //   391: iconst_1
    //   392: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   395: pop
    //   396: aload_3
    //   397: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   400: aload_3
    //   401: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   404: aload_2
    //   405: getfield d : Ljava/util/regex/Pattern;
    //   408: ldc 'Android'
    //   410: iconst_1
    //   411: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   414: pop
    //   415: aload_3
    //   416: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   419: ldc 'Dalvik'
    //   421: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   424: iflt -> 456
    //   427: aload_3
    //   428: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   431: ldc 'Android App'
    //   433: invokevirtual a : (Ljava/lang/String;)V
    //   436: aload_3
    //   437: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   440: aload_3
    //   441: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   444: aload_2
    //   445: getfield e : Ljava/util/regex/Pattern;
    //   448: aconst_null
    //   449: iconst_1
    //   450: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   453: ifne -> 3682
    //   456: aload_3
    //   457: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   460: aload_3
    //   461: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   464: aload_2
    //   465: getfield f : Ljava/util/regex/Pattern;
    //   468: ldc 'Facebook on Android'
    //   470: aload_3
    //   471: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   474: invokevirtual b : ()Ljava/lang/String;
    //   477: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;Ljava/lang/String;)Z
    //   480: ifne -> 3682
    //   483: aload_3
    //   484: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   487: aload_3
    //   488: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   491: aload_2
    //   492: getfield aC : Ljava/util/regex/Pattern;
    //   495: ldc 'Amazon Shopping App'
    //   497: iconst_1
    //   498: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   501: ifne -> 3682
    //   504: aload_3
    //   505: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   508: aload_3
    //   509: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   512: aload_2
    //   513: getfield az : Ljava/util/regex/Pattern;
    //   516: iconst_2
    //   517: invokevirtual b : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   520: ifeq -> 558
    //   523: aload_3
    //   524: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   527: new java/lang/StringBuilder
    //   530: dup
    //   531: invokespecial <init> : ()V
    //   534: aload_3
    //   535: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   538: invokevirtual a : ()Ljava/lang/String;
    //   541: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   544: ldc ' Mobile Application'
    //   546: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   549: invokevirtual toString : ()Ljava/lang/String;
    //   552: invokevirtual a : (Ljava/lang/String;)V
    //   555: goto -> 3682
    //   558: aload_3
    //   559: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   562: aload_3
    //   563: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   566: aload_2
    //   567: getfield g : Ljava/util/regex/Pattern;
    //   570: ldc 'Opera'
    //   572: iconst_1
    //   573: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   576: ifne -> 896
    //   579: aload_3
    //   580: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   583: iconst_2
    //   584: anewarray java/lang/String
    //   587: dup
    //   588: iconst_0
    //   589: ldc 'Aphone Browser'
    //   591: aastore
    //   592: dup
    //   593: iconst_1
    //   594: ldc '360browser'
    //   596: aastore
    //   597: invokestatic containsAnyOf : (Ljava/lang/String;[Ljava/lang/String;)Z
    //   600: ifeq -> 615
    //   603: aload_3
    //   604: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   607: ldc '360 Browser'
    //   609: invokevirtual a : (Ljava/lang/String;)V
    //   612: goto -> 3682
    //   615: aload_3
    //   616: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   619: aload_3
    //   620: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   623: aload_2
    //   624: getfield l : Ljava/util/regex/Pattern;
    //   627: ldc 'Firefox Mobile'
    //   629: iconst_1
    //   630: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   633: ifne -> 896
    //   636: aload_3
    //   637: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   640: aload_3
    //   641: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   644: aload_2
    //   645: getfield m : Ljava/util/regex/Pattern;
    //   648: ldc 'Opera Mobile'
    //   650: iconst_1
    //   651: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   654: ifne -> 896
    //   657: aload_3
    //   658: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   661: aload_3
    //   662: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   665: aload_2
    //   666: getfield n : Ljava/util/regex/Pattern;
    //   669: ldc 'Opera Mini'
    //   671: iconst_1
    //   672: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   675: ifne -> 896
    //   678: aload_3
    //   679: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   682: aload_3
    //   683: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   686: aload_2
    //   687: getfield o : Ljava/util/regex/Pattern;
    //   690: ldc 'Opera Tablet'
    //   692: iconst_1
    //   693: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   696: ifne -> 896
    //   699: aload_3
    //   700: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   703: aload_3
    //   704: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   707: aload_2
    //   708: getfield p : Ljava/util/regex/Pattern;
    //   711: ldc 'UC Browser'
    //   713: iconst_1
    //   714: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   717: ifne -> 896
    //   720: aload_3
    //   721: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   724: aload_3
    //   725: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   728: aload_2
    //   729: getfield q : Ljava/util/regex/Pattern;
    //   732: ldc 'UC Browser'
    //   734: iconst_1
    //   735: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   738: ifne -> 896
    //   741: aload_3
    //   742: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   745: aload_3
    //   746: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   749: aload_2
    //   750: getfield r : Ljava/util/regex/Pattern;
    //   753: ldc 'Amazon Silk Browser'
    //   755: iconst_1
    //   756: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   759: ifne -> 896
    //   762: aload_3
    //   763: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   766: aload_3
    //   767: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   770: aload_2
    //   771: getfield s : Ljava/util/regex/Pattern;
    //   774: ldc 'Baidu Browser'
    //   776: iconst_1
    //   777: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   780: ifne -> 896
    //   783: aload_3
    //   784: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   787: aload_3
    //   788: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   791: aload_2
    //   792: getfield t : Ljava/util/regex/Pattern;
    //   795: ldc 'Samsung Browser'
    //   797: iconst_1
    //   798: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   801: ifne -> 896
    //   804: aload_3
    //   805: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   808: aload_3
    //   809: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   812: aload_2
    //   813: getfield i : Ljava/util/regex/Pattern;
    //   816: ldc 'Chromium'
    //   818: iconst_1
    //   819: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   822: ifne -> 896
    //   825: aload_3
    //   826: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   829: aload_3
    //   830: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   833: aload_2
    //   834: getfield k : Ljava/util/regex/Pattern;
    //   837: ldc 'Chrome Mobile'
    //   839: iconst_1
    //   840: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   843: ifne -> 896
    //   846: aload_3
    //   847: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   850: aload_3
    //   851: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   854: aload_2
    //   855: getfield j : Ljava/util/regex/Pattern;
    //   858: ldc 'Android Webkit'
    //   860: aload_3
    //   861: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   864: invokevirtual b : ()Ljava/lang/String;
    //   867: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;Ljava/lang/String;)Z
    //   870: ifne -> 896
    //   873: aload_3
    //   874: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   877: ldc 'Android'
    //   879: invokevirtual a : (Ljava/lang/String;)V
    //   882: aload_3
    //   883: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   886: aload_3
    //   887: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   890: invokevirtual b : ()Ljava/lang/String;
    //   893: invokevirtual b : (Ljava/lang/String;)V
    //   896: goto -> 3682
    //   899: aload_3
    //   900: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   903: ldc 'Silk'
    //   905: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   908: iflt -> 953
    //   911: aload_3
    //   912: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   915: aload_3
    //   916: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   919: aload_2
    //   920: getfield r : Ljava/util/regex/Pattern;
    //   923: ldc 'Amazon Silk Browser'
    //   925: iconst_1
    //   926: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   929: ifeq -> 953
    //   932: aload_3
    //   933: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   936: ldc 'Android'
    //   938: invokevirtual a : (Ljava/lang/String;)V
    //   941: aload_3
    //   942: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   945: ldc ''
    //   947: invokevirtual b : (Ljava/lang/String;)V
    //   950: goto -> 3682
    //   953: aload_3
    //   954: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   957: iconst_5
    //   958: anewarray java/lang/String
    //   961: dup
    //   962: iconst_0
    //   963: ldc 'iPhone'
    //   965: aastore
    //   966: dup
    //   967: iconst_1
    //   968: ldc 'iPad'
    //   970: aastore
    //   971: dup
    //   972: iconst_2
    //   973: ldc 'iPod'
    //   975: aastore
    //   976: dup
    //   977: iconst_3
    //   978: ldc 'iPod touch'
    //   980: aastore
    //   981: dup
    //   982: iconst_4
    //   983: ldc '(iOS;'
    //   985: aastore
    //   986: invokestatic containsAnyOf : (Ljava/lang/String;[Ljava/lang/String;)Z
    //   989: ifeq -> 1401
    //   992: aload_3
    //   993: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   996: ldc 'iOS'
    //   998: invokevirtual a : (Ljava/lang/String;)V
    //   1001: aload_3
    //   1002: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1005: aload_3
    //   1006: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1009: aload_2
    //   1010: getfield w : Ljava/util/regex/Pattern;
    //   1013: ldc 'iOS'
    //   1015: iconst_2
    //   1016: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1019: ifne -> 1085
    //   1022: aload_3
    //   1023: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1026: aload_3
    //   1027: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1030: aload_2
    //   1031: getfield x : Ljava/util/regex/Pattern;
    //   1034: ldc 'iOS'
    //   1036: iconst_1
    //   1037: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1040: ifne -> 1085
    //   1043: aload_3
    //   1044: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1047: aload_3
    //   1048: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1051: aload_2
    //   1052: getfield y : Ljava/util/regex/Pattern;
    //   1055: ldc 'iOS'
    //   1057: iconst_1
    //   1058: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1061: ifne -> 1085
    //   1064: aload_3
    //   1065: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1068: aload_3
    //   1069: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1072: aload_2
    //   1073: getfield z : Ljava/util/regex/Pattern;
    //   1076: ldc 'iOS'
    //   1078: iconst_1
    //   1079: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1082: ifeq -> 1106
    //   1085: aload_3
    //   1086: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1089: aload_3
    //   1090: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1093: invokevirtual b : ()Ljava/lang/String;
    //   1096: ldc '_'
    //   1098: ldc '.'
    //   1100: invokevirtual replaceAll : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1103: invokevirtual b : (Ljava/lang/String;)V
    //   1106: aload_3
    //   1107: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1110: aload_3
    //   1111: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1114: aload_2
    //   1115: getfield D : Ljava/util/regex/Pattern;
    //   1118: ldc 'iOS'
    //   1120: iconst_1
    //   1121: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1124: ifeq -> 1148
    //   1127: aload_3
    //   1128: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1131: aload_3
    //   1132: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1135: invokevirtual b : ()Ljava/lang/String;
    //   1138: ldc '_'
    //   1140: ldc '.'
    //   1142: invokevirtual replaceAll : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1145: invokevirtual b : (Ljava/lang/String;)V
    //   1148: aload_3
    //   1149: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1152: aload_3
    //   1153: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   1156: aload_2
    //   1157: getfield A : Ljava/util/regex/Pattern;
    //   1160: ldc 'Chrome Mobile on iOS'
    //   1162: iconst_1
    //   1163: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1166: ifne -> 1398
    //   1169: aload_3
    //   1170: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1173: aload_3
    //   1174: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   1177: aload_2
    //   1178: getfield B : Ljava/util/regex/Pattern;
    //   1181: ldc 'Firefox on iOS'
    //   1183: iconst_1
    //   1184: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1187: ifne -> 1398
    //   1190: aload_3
    //   1191: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1194: aload_3
    //   1195: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   1198: aload_2
    //   1199: getfield ae : Ljava/util/regex/Pattern;
    //   1202: ldc 'Opera Mini on iOS'
    //   1204: iconst_1
    //   1205: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1208: ifne -> 1398
    //   1211: aload_3
    //   1212: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1215: aload_3
    //   1216: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   1219: aload_2
    //   1220: getfield C : Ljava/util/regex/Pattern;
    //   1223: ldc 'UC Web Browser on iOS'
    //   1225: iconst_1
    //   1226: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1229: ifne -> 1398
    //   1232: aload_3
    //   1233: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1236: aload_3
    //   1237: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   1240: aload_2
    //   1241: getfield E : Ljava/util/regex/Pattern;
    //   1244: ldc 'UC Web Browser on iOS'
    //   1246: iconst_1
    //   1247: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1250: ifne -> 1398
    //   1253: aload_3
    //   1254: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1257: aload_3
    //   1258: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   1261: aload_2
    //   1262: getfield F : Ljava/util/regex/Pattern;
    //   1265: ldc 'Facebook on iOS'
    //   1267: aload_3
    //   1268: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1271: invokevirtual b : ()Ljava/lang/String;
    //   1274: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;Ljava/lang/String;)Z
    //   1277: ifne -> 3682
    //   1280: aload_3
    //   1281: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1284: aload_3
    //   1285: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   1288: aload_2
    //   1289: getfield aA : Ljava/util/regex/Pattern;
    //   1292: iconst_2
    //   1293: invokevirtual b : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   1296: ifeq -> 1334
    //   1299: aload_3
    //   1300: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1303: new java/lang/StringBuilder
    //   1306: dup
    //   1307: invokespecial <init> : ()V
    //   1310: aload_3
    //   1311: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1314: invokevirtual a : ()Ljava/lang/String;
    //   1317: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1320: ldc ' Mobile Application'
    //   1322: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1325: invokevirtual toString : ()Ljava/lang/String;
    //   1328: invokevirtual a : (Ljava/lang/String;)V
    //   1331: goto -> 3682
    //   1334: aload_3
    //   1335: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1338: aload_3
    //   1339: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   1342: getstatic com/scientiamobile/wurfl/core/VirtualCapabilityUserAgentTool.GOOGLE_SEARCH_APP : Ljava/util/regex/Pattern;
    //   1345: ldc 'Google Search Application'
    //   1347: iconst_1
    //   1348: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1351: ifne -> 3682
    //   1354: aload_3
    //   1355: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1358: aload_3
    //   1359: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   1362: aload_2
    //   1363: getfield G : Ljava/util/regex/Pattern;
    //   1366: ldc 'Mobile Safari'
    //   1368: iconst_1
    //   1369: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1372: ifne -> 1398
    //   1375: aload_3
    //   1376: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1379: ldc 'Mobile Safari'
    //   1381: invokevirtual a : (Ljava/lang/String;)V
    //   1384: aload_3
    //   1385: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1388: aload_3
    //   1389: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1392: invokevirtual b : ()Ljava/lang/String;
    //   1395: invokevirtual b : (Ljava/lang/String;)V
    //   1398: goto -> 3682
    //   1401: aload_3
    //   1402: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1405: ldc 'Tizen'
    //   1407: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   1410: ifeq -> 1479
    //   1413: aload_3
    //   1414: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1417: aload_3
    //   1418: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1421: aload_2
    //   1422: getfield aH : Ljava/util/regex/Pattern;
    //   1425: ldc 'Tizen'
    //   1427: iconst_1
    //   1428: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1431: pop
    //   1432: aload_3
    //   1433: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1436: aload_3
    //   1437: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   1440: aload_2
    //   1441: getfield u : Ljava/util/regex/Pattern;
    //   1444: ldc 'Samsung Browser'
    //   1446: iconst_1
    //   1447: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1450: ifne -> 3682
    //   1453: aload_3
    //   1454: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1457: ldc 'Tizen Browser'
    //   1459: invokevirtual a : (Ljava/lang/String;)V
    //   1462: aload_3
    //   1463: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1466: aload_3
    //   1467: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1470: invokevirtual b : ()Ljava/lang/String;
    //   1473: invokevirtual b : (Ljava/lang/String;)V
    //   1476: goto -> 3682
    //   1479: aload_3
    //   1480: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1483: ldc 'OviBrowser'
    //   1485: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   1488: iflt -> 1524
    //   1491: aload_3
    //   1492: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1495: aload_3
    //   1496: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   1499: aload_2
    //   1500: getfield J : Ljava/util/regex/Pattern;
    //   1503: ldc 'S40 Ovi Browser'
    //   1505: iconst_1
    //   1506: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1509: ifeq -> 1524
    //   1512: aload_3
    //   1513: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1516: ldc 'Nokia Series 40'
    //   1518: invokevirtual a : (Ljava/lang/String;)V
    //   1521: goto -> 3682
    //   1524: aload_3
    //   1525: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1528: aload_3
    //   1529: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1532: aload_2
    //   1533: getfield K : Ljava/util/regex/Pattern;
    //   1536: ldc 'Symbian S60'
    //   1538: iconst_1
    //   1539: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1542: ifne -> 1566
    //   1545: aload_3
    //   1546: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1549: aload_3
    //   1550: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1553: aload_2
    //   1554: getfield L : Ljava/util/regex/Pattern;
    //   1557: ldc 'Symbian S60'
    //   1559: iconst_1
    //   1560: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1563: ifeq -> 1661
    //   1566: aload_3
    //   1567: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1570: aload_3
    //   1571: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1574: aload_2
    //   1575: getfield M : Ljava/util/regex/Pattern;
    //   1578: ldc 'Symbian'
    //   1580: ldc '^3'
    //   1582: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;Ljava/lang/String;)Z
    //   1585: pop
    //   1586: aload_3
    //   1587: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1590: aload_3
    //   1591: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   1594: aload_2
    //   1595: getfield N : Ljava/util/regex/Pattern;
    //   1598: ldc 'Symbian S60 Browser'
    //   1600: iconst_1
    //   1601: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1604: ifne -> 3682
    //   1607: aload_3
    //   1608: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1611: aload_3
    //   1612: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   1615: aload_2
    //   1616: getfield O : Ljava/util/regex/Pattern;
    //   1619: ldc 'Opera Mobi'
    //   1621: iconst_1
    //   1622: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1625: ifne -> 3682
    //   1628: aload_3
    //   1629: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1632: aload_3
    //   1633: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   1636: aload_2
    //   1637: getfield P : Ljava/util/regex/Pattern;
    //   1640: ldc 'UC Web Browser on Symbian'
    //   1642: iconst_1
    //   1643: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1646: ifne -> 3682
    //   1649: aload_3
    //   1650: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1653: ldc 'Symbian S60 Browser'
    //   1655: invokevirtual a : (Ljava/lang/String;)V
    //   1658: goto -> 3682
    //   1661: aload_3
    //   1662: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1665: ldc 'BlackBerry'
    //   1667: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   1670: ifge -> 1685
    //   1673: aload_3
    //   1674: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1677: ldc '(BB10; '
    //   1679: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   1682: iflt -> 1955
    //   1685: aload_3
    //   1686: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1689: aload_3
    //   1690: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1693: aload_2
    //   1694: getfield Q : Ljava/util/regex/Pattern;
    //   1697: ldc 'BlackBerry'
    //   1699: aconst_null
    //   1700: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;Ljava/lang/String;)Z
    //   1703: pop
    //   1704: aload_3
    //   1705: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1708: aload_3
    //   1709: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1712: aload_2
    //   1713: getfield T : Ljava/util/regex/Pattern;
    //   1716: aconst_null
    //   1717: iconst_1
    //   1718: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1721: pop
    //   1722: aload_3
    //   1723: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1726: aload_3
    //   1727: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1730: aload_2
    //   1731: getfield R : Ljava/util/regex/Pattern;
    //   1734: aconst_null
    //   1735: iconst_1
    //   1736: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1739: ifeq -> 1769
    //   1742: aload_3
    //   1743: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1746: ldc 'UC Web'
    //   1748: invokevirtual a : (Ljava/lang/String;)V
    //   1751: aload_3
    //   1752: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1755: aload_3
    //   1756: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1759: iconst_2
    //   1760: invokevirtual a : (I)Ljava/lang/String;
    //   1763: invokevirtual b : (Ljava/lang/String;)V
    //   1766: goto -> 3682
    //   1769: aload_3
    //   1770: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1773: aload_3
    //   1774: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1777: aload_2
    //   1778: getfield S : Ljava/util/regex/Pattern;
    //   1781: aconst_null
    //   1782: iconst_1
    //   1783: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1786: ifeq -> 1816
    //   1789: aload_3
    //   1790: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1793: ldc 'UC Web'
    //   1795: invokevirtual a : (Ljava/lang/String;)V
    //   1798: aload_3
    //   1799: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1802: aload_3
    //   1803: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1806: iconst_2
    //   1807: invokevirtual a : (I)Ljava/lang/String;
    //   1810: invokevirtual b : (Ljava/lang/String;)V
    //   1813: goto -> 3682
    //   1816: aload_3
    //   1817: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1820: aload_3
    //   1821: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   1824: aload_2
    //   1825: getfield ac : Ljava/util/regex/Pattern;
    //   1828: ldc 'Opera Mini'
    //   1830: iconst_1
    //   1831: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1834: ifne -> 3682
    //   1837: aload_3
    //   1838: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1841: aload_3
    //   1842: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1845: aload_2
    //   1846: getfield U : Ljava/util/regex/Pattern;
    //   1849: aconst_null
    //   1850: iconst_1
    //   1851: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1854: ifeq -> 1883
    //   1857: aload_3
    //   1858: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1861: ldc 'BlackBerry Browser'
    //   1863: invokevirtual a : (Ljava/lang/String;)V
    //   1866: aload_3
    //   1867: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1870: aload_3
    //   1871: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1874: invokevirtual b : ()Ljava/lang/String;
    //   1877: invokevirtual b : (Ljava/lang/String;)V
    //   1880: goto -> 3682
    //   1883: aload_3
    //   1884: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1887: aload_3
    //   1888: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1891: aload_2
    //   1892: getfield V : Ljava/util/regex/Pattern;
    //   1895: aconst_null
    //   1896: iconst_1
    //   1897: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1900: ifeq -> 1929
    //   1903: aload_3
    //   1904: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1907: ldc 'BlackBerry Webkit Browser'
    //   1909: invokevirtual a : (Ljava/lang/String;)V
    //   1912: aload_3
    //   1913: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1916: aload_3
    //   1917: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1920: invokevirtual b : ()Ljava/lang/String;
    //   1923: invokevirtual b : (Ljava/lang/String;)V
    //   1926: goto -> 3682
    //   1929: aload_3
    //   1930: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1933: ldc 'BlackBerry Browser'
    //   1935: invokevirtual a : (Ljava/lang/String;)V
    //   1938: aload_3
    //   1939: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1942: aload_3
    //   1943: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1946: invokevirtual b : ()Ljava/lang/String;
    //   1949: invokevirtual b : (Ljava/lang/String;)V
    //   1952: goto -> 3682
    //   1955: aload_3
    //   1956: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1959: ldc 'RIM Tablet OS'
    //   1961: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   1964: iflt -> 2015
    //   1967: aload_3
    //   1968: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1971: aload_3
    //   1972: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   1975: aload_2
    //   1976: getfield W : Ljava/util/regex/Pattern;
    //   1979: ldc 'RIM Tablet OS'
    //   1981: iconst_1
    //   1982: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   1985: ifeq -> 2015
    //   1988: aload_3
    //   1989: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   1992: ldc 'RIM OS Browser'
    //   1994: invokevirtual a : (Ljava/lang/String;)V
    //   1997: aload_3
    //   1998: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2001: aload_3
    //   2002: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2005: iconst_2
    //   2006: invokevirtual a : (I)Ljava/lang/String;
    //   2009: invokevirtual b : (Ljava/lang/String;)V
    //   2012: goto -> 3682
    //   2015: aload_3
    //   2016: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2019: ldc 'NetFront'
    //   2021: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   2024: iflt -> 2048
    //   2027: aload_3
    //   2028: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2031: aload_3
    //   2032: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   2035: aload_2
    //   2036: getfield X : Ljava/util/regex/Pattern;
    //   2039: ldc 'NetFront'
    //   2041: iconst_1
    //   2042: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   2045: ifne -> 3682
    //   2048: aload_3
    //   2049: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2052: aload_3
    //   2053: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2056: ldc 'Obigo'
    //   2058: ldc 'Teleca Obigo'
    //   2060: invokevirtual a : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
    //   2063: ifeq -> 2086
    //   2066: aload_3
    //   2067: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2070: aload_3
    //   2071: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   2074: aload_2
    //   2075: getfield Y : Ljava/util/regex/Pattern;
    //   2078: aconst_null
    //   2079: iconst_1
    //   2080: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   2083: ifne -> 3682
    //   2086: aload_3
    //   2087: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2090: ldc 'Dolfin'
    //   2092: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   2095: iflt -> 2146
    //   2098: aload_3
    //   2099: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2102: aload_3
    //   2103: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2106: aload_2
    //   2107: getfield Z : Ljava/util/regex/Pattern;
    //   2110: ldc 'Bada'
    //   2112: iconst_1
    //   2113: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   2116: ifeq -> 2146
    //   2119: aload_3
    //   2120: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2123: ldc 'Dolfin Browser'
    //   2125: invokevirtual a : (Ljava/lang/String;)V
    //   2128: aload_3
    //   2129: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2132: aload_3
    //   2133: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2136: iconst_2
    //   2137: invokevirtual a : (I)Ljava/lang/String;
    //   2140: invokevirtual b : (Ljava/lang/String;)V
    //   2143: goto -> 3682
    //   2146: aload_3
    //   2147: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2150: aload_3
    //   2151: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2154: ldc 'MAUI'
    //   2156: ldc 'MAUI Browser'
    //   2158: invokevirtual a : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
    //   2161: ifne -> 3682
    //   2164: aload_3
    //   2165: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2168: ldc 'Dolfin'
    //   2170: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   2173: iflt -> 2197
    //   2176: aload_3
    //   2177: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2180: aload_3
    //   2181: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   2184: aload_2
    //   2185: getfield aa : Ljava/util/regex/Pattern;
    //   2188: ldc 'Openwave Browser'
    //   2190: iconst_1
    //   2191: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   2194: ifne -> 3682
    //   2197: aload_3
    //   2198: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2201: aload_3
    //   2202: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2205: aload_2
    //   2206: getfield ab : Ljava/util/regex/Pattern;
    //   2209: ldc 'webOS'
    //   2211: iconst_1
    //   2212: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   2215: ifeq -> 2244
    //   2218: aload_3
    //   2219: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2222: ldc 'webOS Browser'
    //   2224: invokevirtual a : (Ljava/lang/String;)V
    //   2227: aload_3
    //   2228: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2231: aload_3
    //   2232: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2235: invokevirtual b : ()Ljava/lang/String;
    //   2238: invokevirtual b : (Ljava/lang/String;)V
    //   2241: goto -> 3682
    //   2244: aload_3
    //   2245: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2248: ldc 'Opera'
    //   2250: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   2253: iflt -> 2337
    //   2256: aload_3
    //   2257: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2260: aload_3
    //   2261: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2264: ldc 'Opera Mobi'
    //   2266: ldc 'Opera Mobile'
    //   2268: invokevirtual a : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
    //   2271: ifeq -> 2295
    //   2274: aload_3
    //   2275: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2278: aload_3
    //   2279: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2282: aload_2
    //   2283: getfield O : Ljava/util/regex/Pattern;
    //   2286: aconst_null
    //   2287: iconst_1
    //   2288: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   2291: pop
    //   2292: goto -> 3682
    //   2295: aload_3
    //   2296: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2299: aload_3
    //   2300: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2303: aload_2
    //   2304: getfield ac : Ljava/util/regex/Pattern;
    //   2307: ldc 'Opera Mini'
    //   2309: iconst_1
    //   2310: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   2313: ifne -> 3682
    //   2316: aload_3
    //   2317: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2320: aload_3
    //   2321: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2324: aload_2
    //   2325: getfield ad : Ljava/util/regex/Pattern;
    //   2328: ldc 'Opera Link Sync'
    //   2330: iconst_1
    //   2331: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   2334: ifne -> 3682
    //   2337: aload_3
    //   2338: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2341: ldc 'Maemo'
    //   2343: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   2346: iflt -> 2379
    //   2349: aload_3
    //   2350: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2353: ldc 'Maemo'
    //   2355: invokevirtual a : (Ljava/lang/String;)V
    //   2358: aload_3
    //   2359: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2362: aload_3
    //   2363: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   2366: aload_2
    //   2367: getfield af : Ljava/util/regex/Pattern;
    //   2370: ldc 'Firefox'
    //   2372: iconst_1
    //   2373: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   2376: ifne -> 3682
    //   2379: aload_3
    //   2380: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2383: iconst_2
    //   2384: anewarray java/lang/String
    //   2387: dup
    //   2388: iconst_0
    //   2389: ldc 'Java'
    //   2391: aastore
    //   2392: dup
    //   2393: iconst_1
    //   2394: ldc 'UCBrowser/'
    //   2396: aastore
    //   2397: invokestatic containsAnyOf : (Ljava/lang/String;[Ljava/lang/String;)Z
    //   2400: ifeq -> 2424
    //   2403: aload_3
    //   2404: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2407: aload_3
    //   2408: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   2411: aload_2
    //   2412: getfield av : Ljava/util/regex/Pattern;
    //   2415: ldc 'UCBrowser Java Applet'
    //   2417: iconst_1
    //   2418: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   2421: ifne -> 3682
    //   2424: aload_3
    //   2425: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2428: aload_3
    //   2429: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   2432: aload_2
    //   2433: getfield ag : Ljava/util/regex/Pattern;
    //   2436: ldc 'Java Applet'
    //   2438: aconst_null
    //   2439: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;Ljava/lang/String;)Z
    //   2442: ifne -> 3682
    //   2445: aload_3
    //   2446: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2449: ldc 'DesktopApp'
    //   2451: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   2454: iconst_m1
    //   2455: if_icmpeq -> 2598
    //   2458: aload_3
    //   2459: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2462: aload_3
    //   2463: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2466: aload_2
    //   2467: getfield a : Ljava/util/regex/Pattern;
    //   2470: iconst_1
    //   2471: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   2474: ifeq -> 2528
    //   2477: aload_3
    //   2478: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2481: new java/lang/StringBuilder
    //   2484: dup
    //   2485: invokespecial <init> : ()V
    //   2488: aload_3
    //   2489: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2492: iconst_2
    //   2493: invokevirtual a : (I)Ljava/lang/String;
    //   2496: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2499: ldc ' Desktop Application'
    //   2501: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2504: invokevirtual toString : ()Ljava/lang/String;
    //   2507: invokevirtual a : (Ljava/lang/String;)V
    //   2510: aload_3
    //   2511: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2514: aload_3
    //   2515: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2518: iconst_3
    //   2519: invokevirtual a : (I)Ljava/lang/String;
    //   2522: invokevirtual b : (Ljava/lang/String;)V
    //   2525: goto -> 3682
    //   2528: aload_3
    //   2529: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2532: aload_3
    //   2533: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2536: aload_2
    //   2537: getfield b : Ljava/util/regex/Pattern;
    //   2540: iconst_1
    //   2541: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   2544: ifeq -> 2598
    //   2547: aload_3
    //   2548: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2551: new java/lang/StringBuilder
    //   2554: dup
    //   2555: invokespecial <init> : ()V
    //   2558: aload_3
    //   2559: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2562: iconst_2
    //   2563: invokevirtual a : (I)Ljava/lang/String;
    //   2566: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2569: ldc ' Desktop Application'
    //   2571: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2574: invokevirtual toString : ()Ljava/lang/String;
    //   2577: invokevirtual a : (Ljava/lang/String;)V
    //   2580: aload_3
    //   2581: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2584: aload_3
    //   2585: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2588: iconst_3
    //   2589: invokevirtual a : (I)Ljava/lang/String;
    //   2592: invokevirtual b : (Ljava/lang/String;)V
    //   2595: goto -> 3682
    //   2598: aload_3
    //   2599: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2602: aload_3
    //   2603: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2606: aload_2
    //   2607: getfield v : Ljava/util/regex/Pattern;
    //   2610: iconst_1
    //   2611: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   2614: ifeq -> 2644
    //   2617: aload_3
    //   2618: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2621: ldc 'Baidu Browser'
    //   2623: invokevirtual a : (Ljava/lang/String;)V
    //   2626: aload_3
    //   2627: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2630: aload_3
    //   2631: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2634: iconst_2
    //   2635: invokevirtual a : (I)Ljava/lang/String;
    //   2638: invokevirtual b : (Ljava/lang/String;)V
    //   2641: goto -> 3682
    //   2644: aload_3
    //   2645: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2648: iconst_2
    //   2649: anewarray java/lang/String
    //   2652: dup
    //   2653: iconst_0
    //   2654: ldc '360Browser'
    //   2656: aastore
    //   2657: dup
    //   2658: iconst_1
    //   2659: ldc '360SE'
    //   2661: aastore
    //   2662: invokestatic containsAnyOf : (Ljava/lang/String;[Ljava/lang/String;)Z
    //   2665: ifeq -> 2699
    //   2668: aload_3
    //   2669: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2672: aload_3
    //   2673: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2676: aload_2
    //   2677: getfield h : Ljava/util/regex/Pattern;
    //   2680: iconst_1
    //   2681: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   2684: ifeq -> 2699
    //   2687: aload_3
    //   2688: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2691: ldc '360 Browser'
    //   2693: invokevirtual a : (Ljava/lang/String;)V
    //   2696: goto -> 3682
    //   2699: aload_3
    //   2700: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2703: ldc 'MSIE'
    //   2705: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   2708: iflt -> 2757
    //   2711: aload_3
    //   2712: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2715: aload_3
    //   2716: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2719: aload_2
    //   2720: getfield ah : Ljava/util/regex/Pattern;
    //   2723: iconst_2
    //   2724: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   2727: ifeq -> 2757
    //   2730: aload_3
    //   2731: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2734: ldc 'IE'
    //   2736: invokevirtual a : (Ljava/lang/String;)V
    //   2739: aload_3
    //   2740: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2743: aload_3
    //   2744: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2747: iconst_1
    //   2748: invokevirtual a : (I)Ljava/lang/String;
    //   2751: invokevirtual b : (Ljava/lang/String;)V
    //   2754: goto -> 3682
    //   2757: aload_3
    //   2758: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2761: iconst_2
    //   2762: anewarray java/lang/String
    //   2765: dup
    //   2766: iconst_0
    //   2767: ldc 'Trident'
    //   2769: aastore
    //   2770: dup
    //   2771: iconst_1
    //   2772: ldc ' Edge/'
    //   2774: aastore
    //   2775: invokestatic containsAnyOf : (Ljava/lang/String;[Ljava/lang/String;)Z
    //   2778: ifeq -> 2873
    //   2781: aload_3
    //   2782: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2785: aload_3
    //   2786: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2789: aload_2
    //   2790: getfield aj : Ljava/util/regex/Pattern;
    //   2793: iconst_1
    //   2794: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   2797: ifeq -> 2827
    //   2800: aload_3
    //   2801: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2804: ldc 'IE'
    //   2806: invokevirtual a : (Ljava/lang/String;)V
    //   2809: aload_3
    //   2810: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2813: aload_3
    //   2814: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2817: iconst_2
    //   2818: invokevirtual a : (I)Ljava/lang/String;
    //   2821: invokevirtual b : (Ljava/lang/String;)V
    //   2824: goto -> 3682
    //   2827: aload_3
    //   2828: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2831: aload_3
    //   2832: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2835: aload_2
    //   2836: getfield ai : Ljava/util/regex/Pattern;
    //   2839: iconst_1
    //   2840: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   2843: ifeq -> 2873
    //   2846: aload_3
    //   2847: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2850: ldc 'Edge'
    //   2852: invokevirtual a : (Ljava/lang/String;)V
    //   2855: aload_3
    //   2856: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2859: aload_3
    //   2860: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2863: iconst_2
    //   2864: invokevirtual a : (I)Ljava/lang/String;
    //   2867: invokevirtual b : (Ljava/lang/String;)V
    //   2870: goto -> 3682
    //   2873: aload_3
    //   2874: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2877: ldc 'YaBrowser'
    //   2879: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   2882: iflt -> 2931
    //   2885: aload_3
    //   2886: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2889: aload_3
    //   2890: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2893: aload_2
    //   2894: getfield ak : Ljava/util/regex/Pattern;
    //   2897: iconst_1
    //   2898: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   2901: ifeq -> 2931
    //   2904: aload_3
    //   2905: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2908: ldc 'Yandex browser'
    //   2910: invokevirtual a : (Ljava/lang/String;)V
    //   2913: aload_3
    //   2914: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2917: aload_3
    //   2918: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2921: iconst_2
    //   2922: invokevirtual a : (I)Ljava/lang/String;
    //   2925: invokevirtual b : (Ljava/lang/String;)V
    //   2928: goto -> 3682
    //   2931: aload_3
    //   2932: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2935: ldc 'OPR'
    //   2937: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   2940: iflt -> 2989
    //   2943: aload_3
    //   2944: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2947: aload_3
    //   2948: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2951: aload_2
    //   2952: getfield aq : Ljava/util/regex/Pattern;
    //   2955: iconst_1
    //   2956: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   2959: ifeq -> 2989
    //   2962: aload_3
    //   2963: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2966: ldc 'Opera'
    //   2968: invokevirtual a : (Ljava/lang/String;)V
    //   2971: aload_3
    //   2972: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2975: aload_3
    //   2976: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   2979: iconst_2
    //   2980: invokevirtual a : (I)Ljava/lang/String;
    //   2983: invokevirtual b : (Ljava/lang/String;)V
    //   2986: goto -> 3682
    //   2989: aload_3
    //   2990: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   2993: ldc 'Opera'
    //   2995: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   2998: iflt -> 3065
    //   3001: aload_3
    //   3002: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3005: aload_3
    //   3006: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3009: aload_2
    //   3010: getfield ar : Ljava/util/regex/Pattern;
    //   3013: iconst_2
    //   3014: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   3017: ifeq -> 3065
    //   3020: aload_3
    //   3021: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3024: ldc 'Opera'
    //   3026: invokevirtual a : (Ljava/lang/String;)V
    //   3029: aload_3
    //   3030: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3033: aload_3
    //   3034: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3037: iconst_1
    //   3038: invokevirtual a : (I)Ljava/lang/String;
    //   3041: invokevirtual b : (Ljava/lang/String;)V
    //   3044: aload_3
    //   3045: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3048: aload_3
    //   3049: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   3052: aload_2
    //   3053: getfield as : Ljava/util/regex/Pattern;
    //   3056: aconst_null
    //   3057: iconst_1
    //   3058: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   3061: pop
    //   3062: goto -> 3682
    //   3065: aload_3
    //   3066: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3069: ldc 'Linux x86_64'
    //   3071: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   3074: ifeq -> 3119
    //   3077: aload_3
    //   3078: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3081: ldc 'SamsungBrowser/'
    //   3083: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   3086: ifeq -> 3119
    //   3089: aload_3
    //   3090: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3093: ldc 'Linux'
    //   3095: invokevirtual a : (Ljava/lang/String;)V
    //   3098: aload_3
    //   3099: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3102: aload_3
    //   3103: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3106: aload_2
    //   3107: getfield u : Ljava/util/regex/Pattern;
    //   3110: ldc 'DeX Samsung Browser'
    //   3112: iconst_1
    //   3113: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   3116: ifne -> 3682
    //   3119: aload_3
    //   3120: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3123: ldc 'Chrome'
    //   3125: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   3128: iflt -> 3223
    //   3131: aload_3
    //   3132: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3135: aload_3
    //   3136: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3139: aload_2
    //   3140: getfield al : Ljava/util/regex/Pattern;
    //   3143: iconst_1
    //   3144: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   3147: ifeq -> 3177
    //   3150: aload_3
    //   3151: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3154: ldc 'Chrome'
    //   3156: invokevirtual a : (Ljava/lang/String;)V
    //   3159: aload_3
    //   3160: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3163: aload_3
    //   3164: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3167: iconst_2
    //   3168: invokevirtual a : (I)Ljava/lang/String;
    //   3171: invokevirtual b : (Ljava/lang/String;)V
    //   3174: goto -> 3682
    //   3177: aload_3
    //   3178: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3181: aload_3
    //   3182: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3185: aload_2
    //   3186: getfield am : Ljava/util/regex/Pattern;
    //   3189: iconst_1
    //   3190: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   3193: ifeq -> 3223
    //   3196: aload_3
    //   3197: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3200: ldc 'Chrome'
    //   3202: invokevirtual a : (Ljava/lang/String;)V
    //   3205: aload_3
    //   3206: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3209: aload_3
    //   3210: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3213: iconst_2
    //   3214: invokevirtual a : (I)Ljava/lang/String;
    //   3217: invokevirtual b : (Ljava/lang/String;)V
    //   3220: goto -> 3682
    //   3223: aload_3
    //   3224: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3227: ldc 'Epiphany/'
    //   3229: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   3232: ifeq -> 3265
    //   3235: aload_3
    //   3236: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3239: ldc 'Linux'
    //   3241: invokevirtual a : (Ljava/lang/String;)V
    //   3244: aload_3
    //   3245: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3248: aload_3
    //   3249: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3252: aload_2
    //   3253: getfield aG : Ljava/util/regex/Pattern;
    //   3256: ldc 'Epiphany'
    //   3258: iconst_1
    //   3259: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   3262: ifne -> 3682
    //   3265: aload_3
    //   3266: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3269: ldc 'Safari'
    //   3271: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   3274: iflt -> 3362
    //   3277: aload_3
    //   3278: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3281: aload_3
    //   3282: invokevirtual getCleanedDeviceUserAgent : ()Ljava/lang/String;
    //   3285: aload_2
    //   3286: getfield an : Ljava/util/regex/Pattern;
    //   3289: iconst_1
    //   3290: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   3293: ifeq -> 3362
    //   3296: aload_3
    //   3297: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3300: ldc 'CFNetwork'
    //   3302: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   3305: iflt -> 3335
    //   3308: aload_3
    //   3309: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3312: ldc 'OSX App'
    //   3314: invokevirtual a : (Ljava/lang/String;)V
    //   3317: aload_3
    //   3318: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3321: aload_3
    //   3322: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3325: iconst_2
    //   3326: invokevirtual a : (I)Ljava/lang/String;
    //   3329: invokevirtual b : (Ljava/lang/String;)V
    //   3332: goto -> 3682
    //   3335: aload_3
    //   3336: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3339: ldc 'Safari'
    //   3341: invokevirtual a : (Ljava/lang/String;)V
    //   3344: aload_3
    //   3345: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3348: aload_3
    //   3349: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3352: iconst_2
    //   3353: invokevirtual a : (I)Ljava/lang/String;
    //   3356: invokevirtual b : (Ljava/lang/String;)V
    //   3359: goto -> 3682
    //   3362: aload_3
    //   3363: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3366: ldc 'PaleMoon'
    //   3368: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   3371: iconst_m1
    //   3372: if_icmpeq -> 3467
    //   3375: aload_3
    //   3376: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3379: aload_3
    //   3380: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3383: aload_2
    //   3384: getfield aw : Ljava/util/regex/Pattern;
    //   3387: iconst_1
    //   3388: invokevirtual b : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   3391: ifeq -> 3421
    //   3394: aload_3
    //   3395: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3398: ldc 'PaleMoon'
    //   3400: invokevirtual a : (Ljava/lang/String;)V
    //   3403: aload_3
    //   3404: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3407: aload_3
    //   3408: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3411: iconst_2
    //   3412: invokevirtual a : (I)Ljava/lang/String;
    //   3415: invokevirtual b : (Ljava/lang/String;)V
    //   3418: goto -> 3682
    //   3421: aload_3
    //   3422: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3425: aload_3
    //   3426: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3429: aload_2
    //   3430: getfield ax : Ljava/util/regex/Pattern;
    //   3433: iconst_1
    //   3434: invokevirtual b : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   3437: ifeq -> 3467
    //   3440: aload_3
    //   3441: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3444: ldc 'PaleMoon'
    //   3446: invokevirtual a : (Ljava/lang/String;)V
    //   3449: aload_3
    //   3450: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3453: aload_3
    //   3454: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3457: iconst_2
    //   3458: invokevirtual a : (I)Ljava/lang/String;
    //   3461: invokevirtual b : (Ljava/lang/String;)V
    //   3464: goto -> 3682
    //   3467: aload_3
    //   3468: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3471: ldc 'Firefox'
    //   3473: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   3476: iflt -> 3613
    //   3479: aload_3
    //   3480: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3483: aload_3
    //   3484: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3487: aload_2
    //   3488: getfield ao : Ljava/util/regex/Pattern;
    //   3491: iconst_1
    //   3492: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   3495: ifeq -> 3525
    //   3498: aload_3
    //   3499: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3502: ldc 'Firefox'
    //   3504: invokevirtual a : (Ljava/lang/String;)V
    //   3507: aload_3
    //   3508: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3511: aload_3
    //   3512: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3515: iconst_2
    //   3516: invokevirtual a : (I)Ljava/lang/String;
    //   3519: invokevirtual b : (Ljava/lang/String;)V
    //   3522: goto -> 3682
    //   3525: aload_3
    //   3526: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3529: aload_3
    //   3530: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3533: aload_2
    //   3534: getfield ap : Ljava/util/regex/Pattern;
    //   3537: iconst_1
    //   3538: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;I)Z
    //   3541: ifeq -> 3571
    //   3544: aload_3
    //   3545: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3548: ldc 'Firefox'
    //   3550: invokevirtual a : (Ljava/lang/String;)V
    //   3553: aload_3
    //   3554: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3557: aload_3
    //   3558: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3561: iconst_2
    //   3562: invokevirtual a : (I)Ljava/lang/String;
    //   3565: invokevirtual b : (Ljava/lang/String;)V
    //   3568: goto -> 3682
    //   3571: aload_3
    //   3572: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   3575: ldc '(X11; '
    //   3577: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   3580: ifeq -> 3613
    //   3583: aload_3
    //   3584: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3587: ldc 'Linux'
    //   3589: invokevirtual a : (Ljava/lang/String;)V
    //   3592: aload_3
    //   3593: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3596: aload_3
    //   3597: invokevirtual getDeviceUserAgent : ()Ljava/lang/String;
    //   3600: getstatic com/scientiamobile/wurfl/core/VirtualCapabilityUserAgentTool.FIREFOX_LINUX_X11 : Ljava/util/regex/Pattern;
    //   3603: ldc 'Firefox'
    //   3605: iconst_1
    //   3606: invokevirtual a : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;I)Z
    //   3609: pop
    //   3610: goto -> 3682
    //   3613: aload_3
    //   3614: invokevirtual getBrowserUserAgent : ()Ljava/lang/String;
    //   3617: ldc 'CFNetwork'
    //   3619: invokestatic indexOf : (Ljava/lang/String;Ljava/lang/String;)I
    //   3622: iflt -> 3682
    //   3625: aload_3
    //   3626: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3629: aload #4
    //   3631: ldc 'device_os'
    //   3633: invokeinterface getCapability : (Ljava/lang/String;)Ljava/lang/String;
    //   3638: invokevirtual a : (Ljava/lang/String;)V
    //   3641: aload_3
    //   3642: invokevirtual getOsPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3645: aload #4
    //   3647: ldc 'device_os_version'
    //   3649: invokeinterface getCapability : (Ljava/lang/String;)Ljava/lang/String;
    //   3654: invokevirtual b : (Ljava/lang/String;)V
    //   3657: aload_3
    //   3658: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3661: ldc 'CFNetwork App'
    //   3663: invokevirtual a : (Ljava/lang/String;)V
    //   3666: aload_3
    //   3667: invokevirtual getBrowserPair : ()Lcom/scientiamobile/wurfl/core/l;
    //   3670: aload #4
    //   3672: ldc 'mobile_browser_version'
    //   3674: invokeinterface getCapability : (Ljava/lang/String;)Ljava/lang/String;
    //   3679: invokevirtual b : (Ljava/lang/String;)V
    //   3682: aload_1
    //   3683: invokevirtual normalizeOS : ()V
    //   3686: aload_1
    //   3687: invokevirtual normalizeBrowser : ()V
    //   3690: aload_1
    //   3691: areturn
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\VirtualCapabilityUserAgentTool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */