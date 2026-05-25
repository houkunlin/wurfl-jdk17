package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public final class VirtualCapabilityUserAgentTool {
   private static final Pattern a = Pattern.compile("^Mozilla/[0-9]\\.0 \\(Macintosh;(?: U;)?([a-zA-Z_ \\.0-9]+)(?:;)?.+? DesktopApp ([A-Za-z0-9]+)/([\\d\\.]+)\\.?");
   private static final Pattern b = Pattern.compile("^Mozilla/[0-9]\\.0 \\((?:Windows;|X11;)?(?: U; )?([a-zA-Z_ \\.0-9]+)(?:;)?.+? DesktopApp ([A-Za-z0-9]+)/([\\d\\.]+)\\.?");
   private static final Pattern c = Pattern.compile("Android(?: |/)([\\d\\.]+).+");
   private static final Pattern d = Pattern.compile(" Adr(?: |/)([\\d\\.]+).+");
   private static final Pattern e = Pattern.compile("Android ([\\d\\.]+)");
   private static final Pattern f = Pattern.compile("^Mozilla/[45]\\.0.+?Android.+?AppleWebKit.+FB(?:AN/|_IAB/|4A)");
   private static final Pattern g = Pattern.compile("OPR/([\\d\\.]+)");
   private static final Pattern h = Pattern.compile("^Mozilla\\/[0-9]\\.0 .+?((?:Windows|Linux|PPC|Intel) [a-zA-Z0-9 _\\.\\-]+).+(?:360Browser|360SE)");
   private static final Pattern i = Pattern.compile("Version\\/.+?Chrome\\/([\\d\\.]+)");
   private static final Pattern j = Pattern.compile("Version\\/\\d");
   private static final Pattern k = Pattern.compile("Chrome\\/([\\d\\.]+)");
   private static final Pattern l = Pattern.compile("(?:Firefox|Fennec)\\/([\\d\\.]+)");
   private static final Pattern m = Pattern.compile("Opera Mobi\\/.*Version\\/([\\d\\.]+)");
   private static final Pattern n = Pattern.compile("Opera Mini\\/([\\d\\.]+)");
   private static final Pattern o = Pattern.compile("Opera Tablet\\/.*Version\\/([\\d\\.]+)");
   private static final Pattern p = Pattern.compile("UCBrowser\\/([\\d\\.]+)");
   private static final Pattern q = Pattern.compile("^JUC.*UCWEB([0-9])");
   private static final Pattern r = Pattern.compile(" Silk/([\\d\\.]+)");
   private static final Pattern s = Pattern.compile("bdbrowser(?:_i18n)?\\/(\\d+)");
   private static final Pattern t = Pattern.compile("SamsungBrowser/([\\d\\.]+) Chrome/[\\d\\.]+");
   private static final Pattern u = Pattern.compile("SamsungBrowser/([\\d\\.]+)");
   private static final Pattern v = Pattern.compile("^Mozilla\\/[0-9]\\.0 .+?((?:Windows|Linux|PPC|Intel) [a-zA-Z0-9 _\\.\\-]+).+bdbrowser(?:_i18n)?\\/([\\d\\.]+)");
   private static final Pattern w = Pattern.compile("Mozilla\\/[45]\\.0 \\((iPhone|iPod|iPod touch|iPad);(?: U;)? CPU(?: iPhone|) OS ([\\d_]+) like Mac OS X");
   private static final Pattern x = Pattern.compile("^[^/]+?/[\\d\\.]+? \\(i[A-Za-z]+; iOS ([\\d\\.]+); Scale/[\\d\\.]+\\)");
   private static final Pattern y = Pattern.compile("^server-bag \\[iPhone OS,([\\d\\.]+),");
   private static final Pattern z = Pattern.compile("^i(?:Phone|Pad|Pod|Pod touch)\\d+?,\\d+?/([\\d\\.]+)");
   private static final Pattern A = Pattern.compile("i(?:Phone|Pad|Pod)\\d+?,\\d+? iOS/([\\d_]+)");
   private static final Pattern B = Pattern.compile("^Mozilla\\/[45]\\.0.+?like Mac OS X.+?CriOS\\/([\\d\\.]+).+?Mobile\\/[0-9A-Za-z]+ Safari\\/[0-9A-Za-z]+\\.");
   private static final Pattern C = Pattern.compile("^Mozilla\\/[45]\\.0.+?like Mac OS X.+?FxiOS\\/([\\d\\.]+).+?Mobile\\/[0-9A-Za-z]+ Safari\\/[0-9A-Za-z]+\\.");
   private static final Pattern D = Pattern.compile("^Mozilla\\/[45]\\.0.+?OS \\d_\\d.+?like Mac OS X.+?AppleWebKit.+?.+UCBrowser\\/([\\d\\.]+)");
   private static final Pattern E = Pattern.compile("UCWEB/[\\d\\.]+ \\(iOS;.+?OS ([\\d_]+);.+UCBrowser/");
   private static final Pattern F = Pattern.compile("UCWEB/\\d\\.\\d \\(iOS;.+?OS [\\d_]+;.+UCBrowser/([\\d\\.]+)");
   private static final Pattern G = Pattern.compile("^Mozilla\\/[45]\\.0.+?like Mac OS X.+?AppleWebKit.+?Mobile\\/[0-9A-Za-z]+.*FBAN");
   private static final Pattern H = Pattern.compile("^Mozilla.+like Mac OS X.+Version/([\\d\\.]+)");
   private static final Pattern I = Pattern.compile("Windows Phone(?: OS)? ([\\d\\.]+)");
   private static final Pattern J = Pattern.compile("UCWEB/\\d\\.\\d \\(Windows;.+?; wds ?([\\d\\.]+?);.+UCBrowser");
   private static final Pattern K = Pattern.compile("Windows Phone(?: OS)?/([\\d\\.]+)");
   private static final Pattern L = Pattern.compile("\\bS40OviBrowser\\/([\\d\\.]+)");
   private static final Pattern M = Pattern.compile("(?:SymbianOS|Series60|S60)/([\\d\\.]+)");
   private static final Pattern N = Pattern.compile("UCWEB/\\d\\.\\d \\(Symbian;.+?S60 V(\\d+)");
   private static final Pattern O = Pattern.compile("^Mozilla\\/[45]\\.0 \\(Symbian\\/3");
   private static final Pattern P = Pattern.compile("NokiaBrowser\\/([\\d\\.]+)");
   private static final Pattern Q = Pattern.compile("Opera Mobi.+Version\\/([\\d\\.]+)");
   private static final Pattern R = Pattern.compile("UCWEB/[\\d\\.]+ \\(Symbian;.+?UCBrowser/([\\d\\.]+)");
   private static final Pattern S = Pattern.compile("(?:BlackBerry)|(?:^Mozilla\\/5.0 \\(BB10; )");
   private static final Pattern T = Pattern.compile("^BlackBerry[0-9A-Za-z]+?\\/([\\d\\.]+).+?UC Browser\\/?([\\d\\.]+)");
   private static final Pattern U = Pattern.compile("^UCWEB\\/[0-9]\\.0.+?; [a-zA-Z][a-zA-Z]?\\-[a-zA-Z]?[a-zA-Z]; [0-9]+?\\/([\\d\\.]+).+?UCBrowser\\/?([\\d\\.]+)");
   private static final Pattern V = Pattern.compile("^BlackBerry[0-9A-Za-z]+?\\/([\\d\\.]+)");
   private static final Pattern W = Pattern.compile("^Mozilla\\/[45]\\.0 \\(BlackBerry;(?: U;)? BlackBerry.+?Version\\/([\\d\\.]+)");
   private static final Pattern X = Pattern.compile("^Mozilla/[45]\\.0 \\(BB10; .+?Version/([\\d\\.]+)");
   private static final Pattern Y = Pattern.compile("RIM Tablet OS ([\\d\\.]+).+?Version\\/([\\d\\.]+)");
   private static final Pattern Z = Pattern.compile("NetFront\\/([\\d\\.]+)");
   private static final Pattern aa = Pattern.compile("Obig[a-zA-Z]+?\\/(Q[0-9\\.ABC]+)");
   private static final Pattern ab = Pattern.compile("SAMSUNG.+?\\bBada\\/([\\d\\.]+);?.+Dolfin\\/([\\d\\.]+)");
   private static final Pattern ac = Pattern.compile("UP\\.(?:Browser|Link)\\/([\\d\\.]+)");
   private static final Pattern ad = Pattern.compile("^Mozilla\\/[45]\\.0 \\((?:Linux; )?webOS\\/([\\d\\.]+)");
   private static final Pattern ae = Pattern.compile("Opera Mini\\/([\\d\\.]+)");
   private static final Pattern af = Pattern.compile("Browser\\/Opera Sync\\/SyncClient.+?Version\\/([\\d\\.]+)");
   private static final Pattern ag = Pattern.compile("^Mozilla\\/[45]\\.0.+?like Mac OS X.+?OPiOS\\/([\\d\\.]+).+?Mobile\\/[0-9A-Za-z]+ Safari\\/[0-9A-Za-z]+\\.");
   private static final Pattern ah = Pattern.compile("Maemo.+?Firefox\\/([0-9a\\.]+) ");
   private static final Pattern ai = Pattern.compile("(?:MIDP.+?CLDC)|(?:UNTRUSTED)|(?:MIDP-2.0)");
   private static final Pattern aj = Pattern.compile("^Mozilla\\/[0-9]\\.0 \\(compatible; MSIE ([\\d\\.a-z]+); ((?:Windows NT [0-9]+\\.[0-9])|(?:Windows [0-9]\\.[0-9])|(?:Windows [0-9]+)|(?:Mac_PowerPC))");
   private static final Pattern ak = Pattern.compile("^Mozilla/[45]\\.0 \\((Windows NT [\\d\\.]+).+? Edge/([\\d\\.]+)");
   private static final Pattern al = Pattern.compile("^Mozilla/[45]\\.0 \\((Windows NT [\\d\\.]+);.+Trident.+; rv:([\\d\\.]+)");
   private static final Pattern am = Pattern.compile("^Mozilla\\/[45]\\.[0-9] \\((?:Macintosh; )?([a-zA-Z0-9\\._ ]+).+\\) AppleWebKit.+YaBrowser\\/([\\d\\.]+)");
   private static final Pattern an = Pattern.compile("^Mozilla\\/[0-9]\\.0 \\(Macintosh;(?: U;)?([a-zA-Z_ \\.0-9]+)(?:;)?.+? Chrome\\/([\\d\\.]+)\\.?");
   private static final Pattern ao = Pattern.compile("^Mozilla\\/[0-9]\\.0 \\((?:Windows;|X11;)?(?: U; )?([a-zA-Z_ \\.0-9]+)(?:;)?.+? Chrome\\/([\\d\\.]+)\\.?");
   private static final Pattern ap = Pattern.compile("Mozilla\\/[0-9]\\.0 \\((?:(?:Windows|Macintosh); (?:U; |WOW64; )?)?([a-zA-Z_ \\.0-9]+)(?:;)?.+? Version\\/([\\d\\.]+)\\.?");
   private static final Pattern aq = Pattern.compile("^Mozilla\\/[0-9]\\.0 .+(Windows [0-9A-Za-z \\.]+;).+?rv:.+?Firefox\\/([\\d\\.]+)");
   private static final Pattern ar = Pattern.compile("^Mozilla\\/[0-9]\\.0 \\((?:X11|Macintosh); (?:U; |Ubuntu; |)((?:Intel|PPC|Linux) [a-zA-Z0-9\\- \\._\\(\\)]+);.+?rv:.+?Firefox\\/([\\d\\.]+)");
   private static final Pattern as = Pattern.compile("^Mozilla\\/[0-9]\\.0 .+?((?:Windows|Linux|PPC|Intel) [a-zA-Z0-9 _\\.\\-]+).+Chrome\\/.+OPR\\/([\\d\\.]+)");
   private static final Pattern at = Pattern.compile("^Opera\\/([\\d\\.]+) .+?((?:Windows|Linux|PPC|Intel) [a-zA-Z0-9 _\\.\\-]+) ?;");
   private static final Pattern au = Pattern.compile("^Opera\\/.+? Version\\/([\\d\\.]+)");
   private static final Pattern av = Pattern.compile("IEMobile\\/([\\d\\.]+)");
   private static final Pattern aw = Pattern.compile("Edge\\/([\\d\\.]+)");
   private static final Pattern ax = Pattern.compile("UCWEB/\\d\\.\\d \\(Java;.+?UCBrowser/([\\d\\.]+)");
   private static final Pattern ay = Pattern.compile("^Mozilla\\/[0-9]\\.0 .+(Windows [0-9A-Za-z \\.]+;).+?rv:.+?PaleMoon\\/([\\d\\.]+)");
   private static final Pattern az = Pattern.compile("^Mozilla\\/[0-9]\\.0 \\((?:X11|Macintosh); (?:U; |Ubuntu; |)((?:Intel|PPC|Linux) [a-zA-Z0-9\\- \\._\\(\\)]+);.+?rv:.+?PaleMoon\\/([\\d\\.]+)");
   private static final Pattern aA = Pattern.compile("MobileApp ([A-Za-z0-9 ]+)/([\\d\\.]+)");
   private static final Pattern aB = Pattern.compile("^Mozilla/[45]\\.0.+?Android.+?AppleWebKit.+MobileApp ([A-Za-z0-9 ]+)/([\\d\\.]+)");
   private static final Pattern aC = Pattern.compile("^Mozilla/[45]\\.0.+?like Mac OS X.+?AppleWebKit.+?Mobile/[0-9A-Za-z]+.*MobileApp ([A-Za-z0-9 ]+)/([\\d\\.]+)");
   private static final Pattern aD = Pattern.compile(":::Android_(\\d\\.\\d)");
   private static final Pattern aE = Pattern.compile("^mShop:::Amazon_Android_([\\d\\.]+):::");
   private static final Pattern aF = Pattern.compile("Firefox/([\\d\\.]+)");
   private static final Pattern aG = Pattern.compile("^Mozilla/[45]\\.0.+?like Mac OS X.+?AppleWebKit.+?GSA/([\\d\\.]+) Mobile/");
   private static final Pattern aH = Pattern.compile("Version/([\\d\\.A-Z]+)");
   private static final Pattern aI = Pattern.compile(" NX/([\\d\\.]+)");
   private static final Pattern aJ = Pattern.compile("NintendoBrowser/([\\d\\.A-Z]+)");
   private static final Pattern aK = Pattern.compile("Epiphany/([\\d\\.]+)");
   private static final Pattern aL = Pattern.compile("Tizen ([\\d\\.]+)");
   private static final Pattern aM = Pattern.compile("EdgA/([\\d\\.]+)");
   private static final Pattern aN = Pattern.compile("EdgiOS/([\\d\\.]+)");
   private static final Pattern aO = Pattern.compile("Focus/([\\d\\.]+)");
   private static final Pattern aP = Pattern.compile("^Mozilla/[45]\\.0.+?like Mac OS X.+?Focus/([\\d\\.]+) Mobile\\/[0-9A-Za-z]+");
   private static final Pattern aQ = Pattern.compile("(Windows [0-9A-Za-z \\.]+)");
   private static final Pattern aR = Pattern.compile("Macintosh;(?: U;)?([a-zA-Z_ \\.0-9]+)(?:;)?");
   private static VirtualCapabilityUserAgentTool aS = null;

   private VirtualCapabilityUserAgentTool() {
   }

   public static VirtualCapabilityUserAgentTool getInstance() {
      if (aS == null) {
         aS = new VirtualCapabilityUserAgentTool();
      }

      return aS;
   }

   public final VirtualCapabilityDevice assignProperties(WURFLRequest var1, InternalDevice var2) {
      VirtualCapabilityDevice var5;
      VirtualCapabilityDevice var10000 = var5 = new VirtualCapabilityDevice(var1);
      InternalDevice var3 = var2;
      VirtualCapabilityDevice var6 = var10000;
      if (var10000.getOsPair().containsAndSetName(var6.getDeviceUserAgent(), "Windows CE", "Windows Mobile")) {
         var6.getBrowserPair().setName("IE Mobile");
      } else if (!StringMatchUtils.containsAnyOf(var6.getDeviceUserAgent(), "Windows Phone", "; wds") || !var6.getOsPair().matchAndSetGroup(I, var6.getDeviceUserAgent(), "Windows Phone", 1) && !var6.getOsPair().matchAndSetGroup(J, var6.getDeviceUserAgent(), "Windows Phone", 1) && !var6.getOsPair().matchAndSetGroup(K, var6.getDeviceUserAgent(), "Windows Phone", 1)) {
         label422: {
            if (var6.getDeviceUserAgent().contains("Nintendo")) {
               var6.getOsPair().setName("Nintendo");
               if (var6.getBrowserPair().matchAndSetGroup(aH, var6.getBrowserUserAgent(), "Netfront NX", 1) || var6.getBrowserPair().matchAndSetGroup(aI, var6.getBrowserUserAgent(), "Netfront NX", 1) || var6.getBrowserPair().matchAndSetGroup(aJ, var6.getBrowserUserAgent(), "Nintendo Browser", 1)) {
                  break label422;
               }

               var6.getBrowserPair().setName("Nintendo Browser");
            }

            if (StringMatchUtils.containsAnyOf(var6.getDeviceUserAgent(), "Android", "android", " Adr ")) {
               label403: {
                  var6.getOsPair().setName("Android");
                  var6.getOsPair().matchAndSetGroup(c, var6.getDeviceUserAgent(), "Android", 1);
                  var6.getOsPair().matchAndSetGroup(aD, var6.getDeviceUserAgent(), "Android", 1);
                  var6.getOsPair().matchAndSetGroup(d, var6.getDeviceUserAgent(), "Android", 1);
                  String var4;
                  if ((var4 = var3.getCapability("device_os")).equals("Fire OS")) {
                     String var7 = var3.getCapability("device_os_version");
                     var6.getOsPair().setName(var4);
                     var6.getOsPair().setVersion(var7);
                  }

                  if (StringMatchUtils.indexOf(var6.getBrowserUserAgent(), "Dalvik") >= 0) {
                     var6.getBrowserPair().setName("Android App");
                     if (var6.getBrowserPair().matchAndSetGroup(e, var6.getBrowserUserAgent(), (String)null, 1)) {
                        break label403;
                     }
                  }

                  if (!var6.getBrowserPair().matchAndSet(f, var6.getBrowserUserAgent(), "Facebook on Android", var6.getOsPair().getVersion()) && !var6.getBrowserPair().matchAndSetGroup(aE, var6.getBrowserUserAgent(), "Amazon Shopping App", 1)) {
                     if (var6.getBrowserPair().matchAndSetNameAndGroup(aB, var6.getBrowserUserAgent(), 2)) {
                        var6.getBrowserPair().setName(var6.getBrowserPair().getName() + " Mobile Application");
                     } else if (!var6.getBrowserPair().matchAndSetGroup(g, var6.getBrowserUserAgent(), "Opera", 1)) {
                        if (StringMatchUtils.containsAnyOf(var6.getBrowserUserAgent(), "Aphone Browser", "360browser")) {
                           var6.getBrowserPair().setName("360 Browser");
                        } else if (!var6.getBrowserPair().matchAndSetGroup(l, var6.getBrowserUserAgent(), "Firefox Mobile", 1) && !var6.getBrowserPair().matchAndSetGroup(aO, var6.getBrowserUserAgent(), "Firefox Focus", 1) && !var6.getBrowserPair().matchAndSetGroup(m, var6.getBrowserUserAgent(), "Opera Mobile", 1) && !var6.getBrowserPair().matchAndSetGroup(n, var6.getBrowserUserAgent(), "Opera Mini", 1) && !var6.getBrowserPair().matchAndSetGroup(o, var6.getBrowserUserAgent(), "Opera Tablet", 1) && !var6.getBrowserPair().matchAndSetGroup(p, var6.getBrowserUserAgent(), "UC Browser", 1) && !var6.getBrowserPair().matchAndSetGroup(q, var6.getBrowserUserAgent(), "UC Browser", 1) && !var6.getBrowserPair().matchAndSetGroup(r, var6.getBrowserUserAgent(), "Amazon Silk Browser", 1) && !var6.getBrowserPair().matchAndSetGroup(s, var6.getBrowserUserAgent(), "Baidu Browser", 1) && !var6.getBrowserPair().matchAndSetGroup(t, var6.getBrowserUserAgent(), "Samsung Browser", 1) && !var6.getBrowserPair().matchAndSetGroup(aM, var6.getBrowserUserAgent(), "Edge", 1) && !var6.getBrowserPair().matchAndSetGroup(i, var6.getBrowserUserAgent(), "Chromium", 1) && !var6.getBrowserPair().matchAndSetGroup(k, var6.getBrowserUserAgent(), "Chrome Mobile", 1) && !var6.getBrowserPair().matchAndSet(j, var6.getBrowserUserAgent(), "Android Webkit", var6.getOsPair().getVersion())) {
                           var6.getBrowserPair().setName("Android");
                           var6.getBrowserPair().setVersion(var6.getOsPair().getVersion());
                        }
                     }
                  }
               }
            } else if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Silk") >= 0 && var6.getBrowserPair().matchAndSetGroup(r, var6.getBrowserUserAgent(), "Amazon Silk Browser", 1)) {
               var6.getOsPair().setName("Android");
               var6.getOsPair().setVersion("");
            } else if (StringMatchUtils.containsAnyOf(var6.getDeviceUserAgent(), "iPhone", "iPad", "iPod", "iPod touch", "(iOS;")) {
               var6.getOsPair().setName("iOS");
               if (var6.getOsPair().matchAndSetGroup(w, var6.getDeviceUserAgent(), "iOS", 2) || var6.getOsPair().matchAndSetGroup(x, var6.getDeviceUserAgent(), "iOS", 1) || var6.getOsPair().matchAndSetGroup(y, var6.getDeviceUserAgent(), "iOS", 1) || var6.getOsPair().matchAndSetGroup(z, var6.getDeviceUserAgent(), "iOS", 1) || var6.getOsPair().matchAndSetGroup(A, var6.getDeviceUserAgent(), "iOS", 1)) {
                  var6.getOsPair().setVersion(var6.getOsPair().getVersion().replaceAll("_", "."));
               }

               if (var6.getOsPair().matchAndSetGroup(E, var6.getDeviceUserAgent(), "iOS", 1)) {
                  var6.getOsPair().setVersion(var6.getOsPair().getVersion().replaceAll("_", "."));
               }

               if (!var6.getBrowserPair().matchAndSetGroup(B, var6.getBrowserUserAgent(), "Chrome Mobile on iOS", 1) && !var6.getBrowserPair().matchAndSetGroup(aP, var6.getBrowserUserAgent(), "Firefox Focus", 1) && !var6.getBrowserPair().matchAndSetGroup(C, var6.getBrowserUserAgent(), "Firefox on iOS", 1) && !var6.getBrowserPair().matchAndSetGroup(ag, var6.getBrowserUserAgent(), "Opera Mini on iOS", 1) && !var6.getBrowserPair().matchAndSetGroup(D, var6.getBrowserUserAgent(), "UC Web Browser on iOS", 1) && !var6.getBrowserPair().matchAndSetGroup(F, var6.getBrowserUserAgent(), "UC Web Browser on iOS", 1) && !var6.getBrowserPair().matchAndSet(G, var6.getBrowserUserAgent(), "Facebook on iOS", var6.getOsPair().getVersion())) {
                  if (var6.getBrowserPair().matchAndSetNameAndGroup(aC, var6.getBrowserUserAgent(), 2)) {
                     var6.getBrowserPair().setName(var6.getBrowserPair().getName() + " Mobile Application");
                  } else if (!var6.getBrowserPair().matchAndSetGroup(aG, var6.getBrowserUserAgent(), "Google Search Application", 1) && !var6.getBrowserPair().matchAndSetGroup(aN, var6.getBrowserUserAgent(), "Edge", 1) && !var6.getBrowserPair().matchAndSetGroup(H, var6.getBrowserUserAgent(), "Mobile Safari", 1)) {
                     var6.getBrowserPair().setName("Mobile Safari");
                     var6.getBrowserPair().setVersion(var6.getOsPair().getVersion());
                  }
               }
            } else if (var6.getDeviceUserAgent().contains("Tizen")) {
               var6.getOsPair().matchAndSetGroup(aL, var6.getDeviceUserAgent(), "Tizen", 1);
               if (!var6.getBrowserPair().matchAndSetGroup(u, var6.getBrowserUserAgent(), "Samsung Browser", 1)) {
                  var6.getBrowserPair().setName("Tizen Browser");
                  var6.getBrowserPair().setVersion(var6.getOsPair().getVersion());
               }
            } else if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "OviBrowser") >= 0 && var6.getBrowserPair().matchAndSetGroup(L, var6.getBrowserUserAgent(), "S40 Ovi Browser", 1)) {
               var6.getOsPair().setName("Nokia Series 40");
            } else if (!var6.getOsPair().matchAndSetGroup(M, var6.getDeviceUserAgent(), "Symbian S60", 1) && !var6.getOsPair().matchAndSetGroup(N, var6.getDeviceUserAgent(), "Symbian S60", 1)) {
               if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "BlackBerry") < 0 && StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "(BB10; ") < 0) {
                  if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "RIM Tablet OS") >= 0 && var6.getOsPair().matchAndSetGroup(Y, var6.getDeviceUserAgent(), "RIM Tablet OS", 1)) {
                     var6.getBrowserPair().setName("RIM OS Browser");
                     var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                  } else if ((StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "NetFront") < 0 || !var6.getBrowserPair().matchAndSetGroup(Z, var6.getBrowserUserAgent(), "NetFront", 1)) && (!var6.getBrowserPair().containsAndSetName(var6.getDeviceUserAgent(), "Obigo", "Teleca Obigo") || !var6.getBrowserPair().matchAndSetGroup(aa, var6.getBrowserUserAgent(), (String)null, 1))) {
                     if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Dolfin") >= 0 && var6.getOsPair().matchAndSetGroup(ab, var6.getDeviceUserAgent(), "Bada", 1)) {
                        var6.getBrowserPair().setName("Dolfin Browser");
                        var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                     } else if (!var6.getBrowserPair().containsAndSetName(var6.getDeviceUserAgent(), "MAUI", "MAUI Browser") && (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Dolfin") < 0 || !var6.getBrowserPair().matchAndSetGroup(ac, var6.getBrowserUserAgent(), "Openwave Browser", 1))) {
                        if (var6.getOsPair().matchAndSetGroup(ad, var6.getDeviceUserAgent(), "webOS", 1)) {
                           var6.getBrowserPair().setName("webOS Browser");
                           var6.getBrowserPair().setVersion(var6.getOsPair().getVersion());
                        } else {
                           label407: {
                              if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Opera") >= 0) {
                                 if (var6.getBrowserPair().containsAndSetName(var6.getDeviceUserAgent(), "Opera Mobi", "Opera Mobile")) {
                                    var6.getBrowserPair().matchAndSetGroup(Q, var6.getDeviceUserAgent(), (String)null, 1);
                                    break label407;
                                 }

                                 if (var6.getBrowserPair().matchAndSetGroup(ae, var6.getDeviceUserAgent(), "Opera Mini", 1) || var6.getBrowserPair().matchAndSetGroup(af, var6.getDeviceUserAgent(), "Opera Link Sync", 1)) {
                                    break label407;
                                 }
                              }

                              if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Maemo") >= 0) {
                                 var6.getOsPair().setName("Maemo");
                                 if (var6.getBrowserPair().matchAndSetGroup(ah, var6.getBrowserUserAgent(), "Firefox", 1)) {
                                    break label407;
                                 }
                              }

                              if ((!StringMatchUtils.containsAnyOf(var6.getDeviceUserAgent(), "Java", "UCBrowser/") || !var6.getBrowserPair().matchAndSetGroup(ax, var6.getBrowserUserAgent(), "UCBrowser Java Applet", 1)) && !var6.getBrowserPair().matchAndSet(ai, var6.getBrowserUserAgent(), "Java Applet", (String)null)) {
                                 label315: {
                                    if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "DesktopApp") != -1) {
                                       if (var6.getOsPair().matchAndSetNameFromGroup(a, var6.getDeviceUserAgent(), 1)) {
                                          var6.getBrowserPair().setName(var6.getOsPair().getGroup(2) + " Desktop Application");
                                          var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(3));
                                          break label315;
                                       }

                                       if (var6.getOsPair().matchAndSetNameFromGroup(b, var6.getDeviceUserAgent(), 1)) {
                                          var6.getBrowserPair().setName(var6.getOsPair().getGroup(2) + " Desktop Application");
                                          var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(3));
                                          break label315;
                                       }
                                    }

                                    if (var6.getOsPair().matchAndSetNameFromGroup(v, var6.getDeviceUserAgent(), 1)) {
                                       var6.getBrowserPair().setName("Baidu Browser");
                                       var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                                    } else if (StringMatchUtils.containsAnyOf(var6.getDeviceUserAgent(), "360Browser", "360SE") && var6.getOsPair().matchAndSetNameFromGroup(h, var6.getDeviceUserAgent(), 1)) {
                                       var6.getBrowserPair().setName("360 Browser");
                                    } else if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "MSIE") >= 0 && var6.getOsPair().matchAndSetNameFromGroup(aj, var6.getDeviceUserAgent(), 2)) {
                                       var6.getBrowserPair().setName("IE");
                                       var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(1));
                                    } else {
                                       label416: {
                                          if (StringMatchUtils.containsAnyOf(var6.getDeviceUserAgent(), "Trident", " Edge/")) {
                                             if (var6.getOsPair().matchAndSetNameFromGroup(al, var6.getDeviceUserAgent(), 1)) {
                                                var6.getBrowserPair().setName("IE");
                                                var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                                                break label416;
                                             }

                                             if (var6.getOsPair().matchAndSetNameFromGroup(ak, var6.getDeviceUserAgent(), 1)) {
                                                var6.getBrowserPair().setName("Edge");
                                                var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                                                break label416;
                                             }
                                          }

                                          if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "YaBrowser") >= 0 && var6.getOsPair().matchAndSetNameFromGroup(am, var6.getDeviceUserAgent(), 1)) {
                                             var6.getBrowserPair().setName("Yandex browser");
                                             var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                                          } else if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "OPR") >= 0 && var6.getOsPair().matchAndSetNameFromGroup(as, var6.getDeviceUserAgent(), 1)) {
                                             var6.getBrowserPair().setName("Opera");
                                             var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                                          } else if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Opera") >= 0 && var6.getOsPair().matchAndSetNameFromGroup(at, var6.getDeviceUserAgent(), 2)) {
                                             var6.getBrowserPair().setName("Opera");
                                             var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(1));
                                             var6.getBrowserPair().matchAndSetGroup(au, var6.getBrowserUserAgent(), (String)null, 1);
                                          } else {
                                             label419: {
                                                if (var6.getDeviceUserAgent().contains("Linux x86_64") && var6.getDeviceUserAgent().contains("SamsungBrowser/")) {
                                                   var6.getOsPair().setName("Linux");
                                                   if (var6.getBrowserPair().matchAndSetGroup(u, var6.getDeviceUserAgent(), "DeX Samsung Browser", 1)) {
                                                      break label419;
                                                   }
                                                }

                                                if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Chrome") >= 0) {
                                                   if (var6.getOsPair().matchAndSetNameFromGroup(an, var6.getDeviceUserAgent(), 1)) {
                                                      var6.getBrowserPair().setName("Chrome");
                                                      var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                                                      break label419;
                                                   }

                                                   if (var6.getOsPair().matchAndSetNameFromGroup(ao, var6.getDeviceUserAgent(), 1)) {
                                                      var6.getBrowserPair().setName("Chrome");
                                                      var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                                                      break label419;
                                                   }
                                                }

                                                if (var6.getDeviceUserAgent().contains("Epiphany/")) {
                                                   var6.getOsPair().setName("Linux");
                                                   if (var6.getBrowserPair().matchAndSetGroup(aK, var6.getDeviceUserAgent(), "Epiphany", 1)) {
                                                      break label419;
                                                   }
                                                }

                                                if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Safari") >= 0 && var6.getOsPair().matchAndSetNameFromGroup(ap, var6.getCleanedDeviceUserAgent(), 1)) {
                                                   if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "CFNetwork") >= 0) {
                                                      var6.getBrowserPair().setName("OSX App");
                                                      var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                                                   } else {
                                                      var6.getBrowserPair().setName("Safari");
                                                      var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                                                   }
                                                } else {
                                                   label420: {
                                                      if (StringUtils.indexOf(var6.getDeviceUserAgent(), "PaleMoon") != -1) {
                                                         if (var6.getOsPair().matchAndSetNameFromGroup(ay, var6.getDeviceUserAgent(), 1)) {
                                                            var6.getBrowserPair().setName("PaleMoon");
                                                            var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                                                            break label420;
                                                         }

                                                         if (var6.getOsPair().matchAndSetNameFromGroup(az, var6.getDeviceUserAgent(), 1)) {
                                                            var6.getBrowserPair().setName("PaleMoon");
                                                            var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                                                            break label420;
                                                         }
                                                      }

                                                      if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Firefox") >= 0) {
                                                         if (var6.getOsPair().matchAndSetNameFromGroup(aq, var6.getDeviceUserAgent(), 1)) {
                                                            var6.getBrowserPair().setName("Firefox");
                                                            var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                                                            break label420;
                                                         }

                                                         if (var6.getOsPair().matchAndSetNameFromGroup(ar, var6.getDeviceUserAgent(), 1)) {
                                                            var6.getBrowserPair().setName("Firefox");
                                                            var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                                                            break label420;
                                                         }

                                                         if (var6.getBrowserUserAgent().contains("(X11; ")) {
                                                            var6.getOsPair().setName("Linux");
                                                            var6.getBrowserPair().matchAndSetGroup(aF, var6.getDeviceUserAgent(), "Firefox", 1);
                                                            break label420;
                                                         }
                                                      }

                                                      if (StringMatchUtils.indexOf(var6.getBrowserUserAgent(), "CFNetwork") >= 0) {
                                                         var6.getOsPair().setName(var3.getCapability("device_os"));
                                                         var6.getOsPair().setVersion(var3.getCapability("device_os_version"));
                                                         var6.getBrowserPair().setName("CFNetwork App");
                                                         var6.getBrowserPair().setVersion(var3.getCapability("mobile_browser_version"));
                                                      } else {
                                                         String var8;
                                                         if (!var6.getOsPair().matchAndSetNameFromGroup(aQ, var6.getDeviceUserAgent(), 1) && !var6.getOsPair().matchAndSetNameFromGroup(aR, var6.getDeviceUserAgent(), 1) && (var8 = var6.getBrowserUserAgent()) != null && (var8.contains("(X11; ") || var8.contains("Linux x86_64"))) {
                                                            var6.getOsPair().setName("Linux");
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               } else {
                  var6.getOsPair().matchAndSet(S, var6.getDeviceUserAgent(), "BlackBerry", (String)null);
                  var6.getOsPair().matchAndSetGroup(V, var6.getDeviceUserAgent(), (String)null, 1);
                  if (var6.getOsPair().matchAndSetGroup(T, var6.getDeviceUserAgent(), (String)null, 1)) {
                     var6.getBrowserPair().setName("UC Web");
                     var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                  } else if (var6.getOsPair().matchAndSetGroup(U, var6.getDeviceUserAgent(), (String)null, 1)) {
                     var6.getBrowserPair().setName("UC Web");
                     var6.getBrowserPair().setVersion(var6.getOsPair().getGroup(2));
                  } else if (!var6.getBrowserPair().matchAndSetGroup(ae, var6.getBrowserUserAgent(), "Opera Mini", 1)) {
                     if (var6.getOsPair().matchAndSetGroup(W, var6.getDeviceUserAgent(), (String)null, 1)) {
                        var6.getBrowserPair().setName("BlackBerry Browser");
                        var6.getBrowserPair().setVersion(var6.getOsPair().getVersion());
                     } else if (var6.getOsPair().matchAndSetGroup(X, var6.getDeviceUserAgent(), (String)null, 1)) {
                        var6.getBrowserPair().setName("BlackBerry Webkit Browser");
                        var6.getBrowserPair().setVersion(var6.getOsPair().getVersion());
                     } else {
                        var6.getBrowserPair().setName("BlackBerry Browser");
                        var6.getBrowserPair().setVersion(var6.getOsPair().getVersion());
                     }
                  }
               }
            } else {
               var6.getOsPair().matchAndSet(O, var6.getDeviceUserAgent(), "Symbian", "^3");
               if (!var6.getBrowserPair().matchAndSetGroup(P, var6.getBrowserUserAgent(), "Symbian S60 Browser", 1) && !var6.getBrowserPair().matchAndSetGroup(Q, var6.getBrowserUserAgent(), "Opera Mobi", 1) && !var6.getBrowserPair().matchAndSetGroup(R, var6.getBrowserUserAgent(), "UC Web Browser on Symbian", 1)) {
                  var6.getBrowserPair().setName("Symbian S60 Browser");
               }
            }
         }
      } else if (var6.getBrowserPair().matchAndSetNameAndGroup(aA, var6.getBrowserUserAgent(), 2)) {
         var6.getBrowserPair().setName(var6.getBrowserPair().getName() + " Mobile Application");
      } else if (!var6.getBrowserPair().matchAndSetGroup(p, var6.getBrowserUserAgent(), "UC Browser", 1) && !var6.getBrowserPair().matchAndSetGroup(av, var6.getBrowserUserAgent(), "IE Mobile", 1) && !var6.getBrowserPair().matchAndSetGroup(aw, var6.getBrowserUserAgent(), "Edge Mobile", 1)) {
      }

      var5.normalizeOS();
      var5.normalizeBrowser();
      return var5;
   }
}
