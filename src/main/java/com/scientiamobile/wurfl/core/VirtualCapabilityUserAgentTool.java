package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

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
      if (var10000.getOsPair().a(var6.getDeviceUserAgent(), "Windows CE", "Windows Mobile")) {
         var6.getBrowserPair().a("IE Mobile");
      } else if (!StringMatchUtils.containsAnyOf(var6.getDeviceUserAgent(), "Windows Phone", "; wds") || !var6.getOsPair().a(var6.getDeviceUserAgent(), I, "Windows Phone", 1) && !var6.getOsPair().a(var6.getDeviceUserAgent(), J, "Windows Phone", 1) && !var6.getOsPair().a(var6.getDeviceUserAgent(), K, "Windows Phone", 1)) {
         label422: {
            if (var6.getDeviceUserAgent().contains("Nintendo")) {
               var6.getOsPair().a("Nintendo");
               if (var6.getBrowserPair().a(var6.getBrowserUserAgent(), aH, "Netfront NX", 1) || var6.getBrowserPair().a(var6.getBrowserUserAgent(), aI, "Netfront NX", 1) || var6.getBrowserPair().a(var6.getBrowserUserAgent(), aJ, "Nintendo Browser", 1)) {
                  break label422;
               }

               var6.getBrowserPair().a("Nintendo Browser");
            }

            if (StringMatchUtils.containsAnyOf(var6.getDeviceUserAgent(), "Android", "android", " Adr ")) {
               label403: {
                  var6.getOsPair().a("Android");
                  var6.getOsPair().a(var6.getDeviceUserAgent(), c, "Android", 1);
                  var6.getOsPair().a(var6.getDeviceUserAgent(), aD, "Android", 1);
                  var6.getOsPair().a(var6.getDeviceUserAgent(), d, "Android", 1);
                  String var4;
                  if ((var4 = var3.getCapability("device_os")).equals("Fire OS")) {
                     String var7 = var3.getCapability("device_os_version");
                     var6.getOsPair().a(var4);
                     var6.getOsPair().b(var7);
                  }

                  if (StringMatchUtils.indexOf(var6.getBrowserUserAgent(), "Dalvik") >= 0) {
                     var6.getBrowserPair().a("Android App");
                     if (var6.getBrowserPair().a(var6.getBrowserUserAgent(), e, (String)null, 1)) {
                        break label403;
                     }
                  }

                  if (!var6.getBrowserPair().a(var6.getBrowserUserAgent(), f, "Facebook on Android", var6.getOsPair().b()) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), aE, "Amazon Shopping App", 1)) {
                     if (var6.getBrowserPair().b(var6.getBrowserUserAgent(), aB, 2)) {
                        var6.getBrowserPair().a(var6.getBrowserPair().a() + " Mobile Application");
                     } else if (!var6.getBrowserPair().a(var6.getBrowserUserAgent(), g, "Opera", 1)) {
                        if (StringMatchUtils.containsAnyOf(var6.getBrowserUserAgent(), "Aphone Browser", "360browser")) {
                           var6.getBrowserPair().a("360 Browser");
                        } else if (!var6.getBrowserPair().a(var6.getBrowserUserAgent(), l, "Firefox Mobile", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), aO, "Firefox Focus", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), m, "Opera Mobile", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), n, "Opera Mini", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), o, "Opera Tablet", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), p, "UC Browser", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), q, "UC Browser", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), r, "Amazon Silk Browser", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), s, "Baidu Browser", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), t, "Samsung Browser", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), aM, "Edge", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), i, "Chromium", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), k, "Chrome Mobile", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), j, "Android Webkit", var6.getOsPair().b())) {
                           var6.getBrowserPair().a("Android");
                           var6.getBrowserPair().b(var6.getOsPair().b());
                        }
                     }
                  }
               }
            } else if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Silk") >= 0 && var6.getBrowserPair().a(var6.getBrowserUserAgent(), r, "Amazon Silk Browser", 1)) {
               var6.getOsPair().a("Android");
               var6.getOsPair().b("");
            } else if (StringMatchUtils.containsAnyOf(var6.getDeviceUserAgent(), "iPhone", "iPad", "iPod", "iPod touch", "(iOS;")) {
               var6.getOsPair().a("iOS");
               if (var6.getOsPair().a(var6.getDeviceUserAgent(), w, "iOS", 2) || var6.getOsPair().a(var6.getDeviceUserAgent(), x, "iOS", 1) || var6.getOsPair().a(var6.getDeviceUserAgent(), y, "iOS", 1) || var6.getOsPair().a(var6.getDeviceUserAgent(), z, "iOS", 1) || var6.getOsPair().a(var6.getDeviceUserAgent(), A, "iOS", 1)) {
                  var6.getOsPair().b(var6.getOsPair().b().replaceAll("_", "."));
               }

               if (var6.getOsPair().a(var6.getDeviceUserAgent(), E, "iOS", 1)) {
                  var6.getOsPair().b(var6.getOsPair().b().replaceAll("_", "."));
               }

               if (!var6.getBrowserPair().a(var6.getBrowserUserAgent(), B, "Chrome Mobile on iOS", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), aP, "Firefox Focus", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), C, "Firefox on iOS", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), ag, "Opera Mini on iOS", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), D, "UC Web Browser on iOS", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), F, "UC Web Browser on iOS", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), G, "Facebook on iOS", var6.getOsPair().b())) {
                  if (var6.getBrowserPair().b(var6.getBrowserUserAgent(), aC, 2)) {
                     var6.getBrowserPair().a(var6.getBrowserPair().a() + " Mobile Application");
                  } else if (!var6.getBrowserPair().a(var6.getBrowserUserAgent(), aG, "Google Search Application", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), aN, "Edge", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), H, "Mobile Safari", 1)) {
                     var6.getBrowserPair().a("Mobile Safari");
                     var6.getBrowserPair().b(var6.getOsPair().b());
                  }
               }
            } else if (var6.getDeviceUserAgent().contains("Tizen")) {
               var6.getOsPair().a(var6.getDeviceUserAgent(), aL, "Tizen", 1);
               if (!var6.getBrowserPair().a(var6.getBrowserUserAgent(), u, "Samsung Browser", 1)) {
                  var6.getBrowserPair().a("Tizen Browser");
                  var6.getBrowserPair().b(var6.getOsPair().b());
               }
            } else if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "OviBrowser") >= 0 && var6.getBrowserPair().a(var6.getBrowserUserAgent(), L, "S40 Ovi Browser", 1)) {
               var6.getOsPair().a("Nokia Series 40");
            } else if (!var6.getOsPair().a(var6.getDeviceUserAgent(), M, "Symbian S60", 1) && !var6.getOsPair().a(var6.getDeviceUserAgent(), N, "Symbian S60", 1)) {
               if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "BlackBerry") < 0 && StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "(BB10; ") < 0) {
                  if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "RIM Tablet OS") >= 0 && var6.getOsPair().a(var6.getDeviceUserAgent(), Y, "RIM Tablet OS", 1)) {
                     var6.getBrowserPair().a("RIM OS Browser");
                     var6.getBrowserPair().b(var6.getOsPair().a(2));
                  } else if ((StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "NetFront") < 0 || !var6.getBrowserPair().a(var6.getBrowserUserAgent(), Z, "NetFront", 1)) && (!var6.getBrowserPair().a(var6.getDeviceUserAgent(), "Obigo", "Teleca Obigo") || !var6.getBrowserPair().a(var6.getBrowserUserAgent(), aa, (String)null, 1))) {
                     if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Dolfin") >= 0 && var6.getOsPair().a(var6.getDeviceUserAgent(), ab, "Bada", 1)) {
                        var6.getBrowserPair().a("Dolfin Browser");
                        var6.getBrowserPair().b(var6.getOsPair().a(2));
                     } else if (!var6.getBrowserPair().a(var6.getDeviceUserAgent(), "MAUI", "MAUI Browser") && (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Dolfin") < 0 || !var6.getBrowserPair().a(var6.getBrowserUserAgent(), ac, "Openwave Browser", 1))) {
                        if (var6.getOsPair().a(var6.getDeviceUserAgent(), ad, "webOS", 1)) {
                           var6.getBrowserPair().a("webOS Browser");
                           var6.getBrowserPair().b(var6.getOsPair().b());
                        } else {
                           label407: {
                              if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Opera") >= 0) {
                                 if (var6.getBrowserPair().a(var6.getDeviceUserAgent(), "Opera Mobi", "Opera Mobile")) {
                                    var6.getBrowserPair().a(var6.getDeviceUserAgent(), Q, (String)null, 1);
                                    break label407;
                                 }

                                 if (var6.getBrowserPair().a(var6.getDeviceUserAgent(), ae, "Opera Mini", 1) || var6.getBrowserPair().a(var6.getDeviceUserAgent(), af, "Opera Link Sync", 1)) {
                                    break label407;
                                 }
                              }

                              if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Maemo") >= 0) {
                                 var6.getOsPair().a("Maemo");
                                 if (var6.getBrowserPair().a(var6.getBrowserUserAgent(), ah, "Firefox", 1)) {
                                    break label407;
                                 }
                              }

                              if ((!StringMatchUtils.containsAnyOf(var6.getDeviceUserAgent(), "Java", "UCBrowser/") || !var6.getBrowserPair().a(var6.getBrowserUserAgent(), ax, "UCBrowser Java Applet", 1)) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), ai, "Java Applet", (String)null)) {
                                 label315: {
                                    if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "DesktopApp") != -1) {
                                       if (var6.getOsPair().a(var6.getDeviceUserAgent(), a, 1)) {
                                          var6.getBrowserPair().a(var6.getOsPair().a(2) + " Desktop Application");
                                          var6.getBrowserPair().b(var6.getOsPair().a(3));
                                          break label315;
                                       }

                                       if (var6.getOsPair().a(var6.getDeviceUserAgent(), b, 1)) {
                                          var6.getBrowserPair().a(var6.getOsPair().a(2) + " Desktop Application");
                                          var6.getBrowserPair().b(var6.getOsPair().a(3));
                                          break label315;
                                       }
                                    }

                                    if (var6.getOsPair().a(var6.getDeviceUserAgent(), v, 1)) {
                                       var6.getBrowserPair().a("Baidu Browser");
                                       var6.getBrowserPair().b(var6.getOsPair().a(2));
                                    } else if (StringMatchUtils.containsAnyOf(var6.getDeviceUserAgent(), "360Browser", "360SE") && var6.getOsPair().a(var6.getDeviceUserAgent(), h, 1)) {
                                       var6.getBrowserPair().a("360 Browser");
                                    } else if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "MSIE") >= 0 && var6.getOsPair().a(var6.getDeviceUserAgent(), aj, 2)) {
                                       var6.getBrowserPair().a("IE");
                                       var6.getBrowserPair().b(var6.getOsPair().a(1));
                                    } else {
                                       label416: {
                                          if (StringMatchUtils.containsAnyOf(var6.getDeviceUserAgent(), "Trident", " Edge/")) {
                                             if (var6.getOsPair().a(var6.getDeviceUserAgent(), al, 1)) {
                                                var6.getBrowserPair().a("IE");
                                                var6.getBrowserPair().b(var6.getOsPair().a(2));
                                                break label416;
                                             }

                                             if (var6.getOsPair().a(var6.getDeviceUserAgent(), ak, 1)) {
                                                var6.getBrowserPair().a("Edge");
                                                var6.getBrowserPair().b(var6.getOsPair().a(2));
                                                break label416;
                                             }
                                          }

                                          if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "YaBrowser") >= 0 && var6.getOsPair().a(var6.getDeviceUserAgent(), am, 1)) {
                                             var6.getBrowserPair().a("Yandex browser");
                                             var6.getBrowserPair().b(var6.getOsPair().a(2));
                                          } else if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "OPR") >= 0 && var6.getOsPair().a(var6.getDeviceUserAgent(), as, 1)) {
                                             var6.getBrowserPair().a("Opera");
                                             var6.getBrowserPair().b(var6.getOsPair().a(2));
                                          } else if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Opera") >= 0 && var6.getOsPair().a(var6.getDeviceUserAgent(), at, 2)) {
                                             var6.getBrowserPair().a("Opera");
                                             var6.getBrowserPair().b(var6.getOsPair().a(1));
                                             var6.getBrowserPair().a(var6.getBrowserUserAgent(), au, (String)null, 1);
                                          } else {
                                             label419: {
                                                if (var6.getDeviceUserAgent().contains("Linux x86_64") && var6.getDeviceUserAgent().contains("SamsungBrowser/")) {
                                                   var6.getOsPair().a("Linux");
                                                   if (var6.getBrowserPair().a(var6.getDeviceUserAgent(), u, "DeX Samsung Browser", 1)) {
                                                      break label419;
                                                   }
                                                }

                                                if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Chrome") >= 0) {
                                                   if (var6.getOsPair().a(var6.getDeviceUserAgent(), an, 1)) {
                                                      var6.getBrowserPair().a("Chrome");
                                                      var6.getBrowserPair().b(var6.getOsPair().a(2));
                                                      break label419;
                                                   }

                                                   if (var6.getOsPair().a(var6.getDeviceUserAgent(), ao, 1)) {
                                                      var6.getBrowserPair().a("Chrome");
                                                      var6.getBrowserPair().b(var6.getOsPair().a(2));
                                                      break label419;
                                                   }
                                                }

                                                if (var6.getDeviceUserAgent().contains("Epiphany/")) {
                                                   var6.getOsPair().a("Linux");
                                                   if (var6.getBrowserPair().a(var6.getDeviceUserAgent(), aK, "Epiphany", 1)) {
                                                      break label419;
                                                   }
                                                }

                                                if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Safari") >= 0 && var6.getOsPair().a(var6.getCleanedDeviceUserAgent(), ap, 1)) {
                                                   if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "CFNetwork") >= 0) {
                                                      var6.getBrowserPair().a("OSX App");
                                                      var6.getBrowserPair().b(var6.getOsPair().a(2));
                                                   } else {
                                                      var6.getBrowserPair().a("Safari");
                                                      var6.getBrowserPair().b(var6.getOsPair().a(2));
                                                   }
                                                } else {
                                                   label420: {
                                                      if (StringUtils.indexOf(var6.getDeviceUserAgent(), "PaleMoon") != -1) {
                                                         if (var6.getOsPair().b(var6.getDeviceUserAgent(), ay, 1)) {
                                                            var6.getBrowserPair().a("PaleMoon");
                                                            var6.getBrowserPair().b(var6.getOsPair().a(2));
                                                            break label420;
                                                         }

                                                         if (var6.getOsPair().b(var6.getDeviceUserAgent(), az, 1)) {
                                                            var6.getBrowserPair().a("PaleMoon");
                                                            var6.getBrowserPair().b(var6.getOsPair().a(2));
                                                            break label420;
                                                         }
                                                      }

                                                      if (StringMatchUtils.indexOf(var6.getDeviceUserAgent(), "Firefox") >= 0) {
                                                         if (var6.getOsPair().a(var6.getDeviceUserAgent(), aq, 1)) {
                                                            var6.getBrowserPair().a("Firefox");
                                                            var6.getBrowserPair().b(var6.getOsPair().a(2));
                                                            break label420;
                                                         }

                                                         if (var6.getOsPair().a(var6.getDeviceUserAgent(), ar, 1)) {
                                                            var6.getBrowserPair().a("Firefox");
                                                            var6.getBrowserPair().b(var6.getOsPair().a(2));
                                                            break label420;
                                                         }

                                                         if (var6.getBrowserUserAgent().contains("(X11; ")) {
                                                            var6.getOsPair().a("Linux");
                                                            var6.getBrowserPair().a(var6.getDeviceUserAgent(), aF, "Firefox", 1);
                                                            break label420;
                                                         }
                                                      }

                                                      if (StringMatchUtils.indexOf(var6.getBrowserUserAgent(), "CFNetwork") >= 0) {
                                                         var6.getOsPair().a(var3.getCapability("device_os"));
                                                         var6.getOsPair().b(var3.getCapability("device_os_version"));
                                                         var6.getBrowserPair().a("CFNetwork App");
                                                         var6.getBrowserPair().b(var3.getCapability("mobile_browser_version"));
                                                      } else {
                                                         String var8;
                                                         if (!var6.getOsPair().a(var6.getDeviceUserAgent(), aQ, 1) && !var6.getOsPair().a(var6.getDeviceUserAgent(), aR, 1) && (var8 = var6.getBrowserUserAgent()) != null && (var8.contains("(X11; ") || var8.contains("Linux x86_64"))) {
                                                            var6.getOsPair().a("Linux");
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
                  var6.getOsPair().a(var6.getDeviceUserAgent(), S, "BlackBerry", (String)null);
                  var6.getOsPair().a(var6.getDeviceUserAgent(), V, (String)null, 1);
                  if (var6.getOsPair().a(var6.getDeviceUserAgent(), T, (String)null, 1)) {
                     var6.getBrowserPair().a("UC Web");
                     var6.getBrowserPair().b(var6.getOsPair().a(2));
                  } else if (var6.getOsPair().a(var6.getDeviceUserAgent(), U, (String)null, 1)) {
                     var6.getBrowserPair().a("UC Web");
                     var6.getBrowserPair().b(var6.getOsPair().a(2));
                  } else if (!var6.getBrowserPair().a(var6.getBrowserUserAgent(), ae, "Opera Mini", 1)) {
                     if (var6.getOsPair().a(var6.getDeviceUserAgent(), W, (String)null, 1)) {
                        var6.getBrowserPair().a("BlackBerry Browser");
                        var6.getBrowserPair().b(var6.getOsPair().b());
                     } else if (var6.getOsPair().a(var6.getDeviceUserAgent(), X, (String)null, 1)) {
                        var6.getBrowserPair().a("BlackBerry Webkit Browser");
                        var6.getBrowserPair().b(var6.getOsPair().b());
                     } else {
                        var6.getBrowserPair().a("BlackBerry Browser");
                        var6.getBrowserPair().b(var6.getOsPair().b());
                     }
                  }
               }
            } else {
               var6.getOsPair().a(var6.getDeviceUserAgent(), O, "Symbian", "^3");
               if (!var6.getBrowserPair().a(var6.getBrowserUserAgent(), P, "Symbian S60 Browser", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), Q, "Opera Mobi", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), R, "UC Web Browser on Symbian", 1)) {
                  var6.getBrowserPair().a("Symbian S60 Browser");
               }
            }
         }
      } else if (var6.getBrowserPair().b(var6.getBrowserUserAgent(), aA, 2)) {
         var6.getBrowserPair().a(var6.getBrowserPair().a() + " Mobile Application");
      } else if (!var6.getBrowserPair().a(var6.getBrowserUserAgent(), p, "UC Browser", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), av, "IE Mobile", 1) && !var6.getBrowserPair().a(var6.getBrowserUserAgent(), aw, "Edge Mobile", 1)) {
      }

      var5.normalizeOS();
      var5.normalizeBrowser();
      return var5;
   }
}
