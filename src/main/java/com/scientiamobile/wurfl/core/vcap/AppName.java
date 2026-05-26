package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppName implements VirtualCapabilityEvaluator, Serializable {
   private static final List<String> a = new ArrayList<>();
   private static final List<String> b = new ArrayList<>();
   private static final long serialVersionUID = 7704959740704532442L;
   private static final Pattern c = Pattern.compile("WebViewApp ([^/]+)/");
   private static final Pattern d = Pattern.compile("^([^/]+)/.+? Dalvik/");
   private static final Pattern e = Pattern.compile("^([^/]+)/[\\d\\.-_]+ i(?:Phone|Pad|Pod)\\d+?,\\d+? iOS/[\\d_]+ CFNetwork/[\\d\\.]+");
   private static final Pattern f = Pattern.compile("^([^/]+)/[0-9\\.-_]+ Windows Phone/[\\d\\.]+");

   public String eval(Device var1, WURFLRequest var2) {
      String var3;
      if (var2.isUrlEncoded()) {
         var3 = var2.getCleanedDeviceUserAgent();
      } else {
         var3 = var2.getOriginalUserAgent();
      }

      if (var3.contains("WebViewApp")) {
         Matcher var8;
         return (var8 = c.matcher(var3)).find() ? var8.group(1) : "WebView";
      } else {
         Matcher var4;
         if ((var4 = d.matcher(var3)).find()) {
            return var4.group(1);
         } else if ((var4 = e.matcher(var3)).find()) {
            return var4.group(1);
         } else if ((var4 = f.matcher(var3)).find()) {
            return var4.group(1);
         } else {
            for(int var7 = 0; var7 < a.size(); ++var7) {
               if (var3.contains(a.get(var7))) {
                  return b.get(var7);
               }
            }

            return "Stock Browser";
         }
      }
   }

   public String getHandledVirtualCapabilityName() {
      return "advertised_app_name";
   }

   static {
      a.add("abcf/");
      a.add("adultswim");
      a.add("Aliexpress");
      a.add("AOLShield");
      a.add(" GSA/");
      a.add("AmazonAdSDK");
      a.add("Appstore/release-");
      a.add("AppStore/");
      a.add("Amazon");
      a.add("Argo/");
      a.add("BBCStoryOfLife");
      a.add("bdbrowser");
      a.add("com.apple.tv");
      a.add("comedycentral");
      a.add("DroidRBMobile");
      a.add("ESPN/");
      a.add("FB_IAB/MESSENGER");
      a.add("FB_IAB");
      a.add("FB4A");
      a.add("FBAV/");
      a.add("UCWEB");
      a.add("UCBrowser");
      a.add(" Edge/");
      a.add(" EdgiOS/");
      a.add(" EdgA/");
      a.add(" Focus/");
      a.add(" Firefox/");
      a.add(" FxiOS/");
      a.add("Fennec");
      a.add("Flipboard");
      a.add("fxnetworks");
      a.add("Groupon");
      a.add("hola_android");
      a.add("iHeartRadio");
      a.add("Indeed App");
      a.add("(InstaFollow)");
      a.add("Instagram");
      a.add("itunesstored/");
      a.add("Kik/");
      a.add("LA Times/");
      a.add("Liebao");
      a.add("Line/");
      a.add("MicroMessenger");
      a.add("Microsoft Outlook");
      a.add("Microsoft Office/");
      a.add("MSIE");
      a.add("NAVER");
      a.add("NewsWeather/");
      a.add("NokiaBrowser");
      a.add("nyt_android");
      a.add("offerup");
      a.add("OfferUp");
      a.add("Onefootball");
      a.add("Opera");
      a.add(" OPR/");
      a.add("OviBrowser");
      a.add("nytiphone");
      a.add("Pandora");
      a.add("Pinterest");
      a.add("Puffin");
      a.add("Relay");
      a.add("Reddit");
      a.add(" Silk/");
      a.add("Skype");
      a.add("SoundCloud");
      a.add("Spotify");
      a.add("Twitter");
      a.add("Uber/");
      a.add("uniqlo-app");
      a.add("Valve Steam GameOverlay");
      a.add("Wash Post");
      a.add("Windows Maps");
      a.add("YaBrowser");
      a.add("YJApp");
      a.add(" CriOS/");
      a.add(" Chrome/");
      a.add("iCatalog");
      a.add("mobincube");
      a.add("AndroidCvpPlayer");
      a.add("ANVSDK");
      a.add("CFNetwork");
      a.add("Dalvik");
      a.add("Darwin");
      a.add("FreeWheelAdManager");
      a.add("GoogleTagManager");
      a.add("upLynkAndroidPlayer");
      a.add("VisualOn OSMP+ Player");
      a.add("WindowsPhoneAdClient");
      a.add("Windows Phone Ad Client");
      b.add("Freeform/ABC Family");
      b.add("AdultSwim");
      b.add("AliExpress");
      b.add("AOL Shield browser");
      b.add("Google Search");
      b.add("Amazon Ad SDK");
      b.add("Amazon App Store");
      b.add("iOS App Store");
      b.add("Amazon App");
      b.add("Netflix");
      b.add("BBC Story of Life");
      b.add("Baidu browser");
      b.add("TV on iOS");
      b.add("Comedy Central");
      b.add("Redbox");
      b.add("ESPN");
      b.add("Facebook Messenger");
      b.add("Facebook");
      b.add("Facebook");
      b.add("Facebook");
      b.add("UCBrowser");
      b.add("UCBrowser");
      b.add("Edge Browser");
      b.add("Edge Browser");
      b.add("Edge Browser");
      b.add("Firefox Focus browser");
      b.add("Firefox browser");
      b.add("Firefox browser");
      b.add("Fennec browser");
      b.add("Flipboard");
      b.add("FX Network");
      b.add("Groupon");
      b.add("Hola VPN");
      b.add("iHeartRadio");
      b.add("Indeed");
      b.add("InstaFollow");
      b.add("Instagram");
      b.add("iTunes");
      b.add("Kik");
      b.add("LA Times");
      b.add("Liebao");
      b.add("LINE");
      b.add("WeChat");
      b.add("Microsoft Outlook");
      b.add("Microsoft Office");
      b.add("Internet Explorer");
      b.add("Naver Search");
      b.add("Google News & Weather");
      b.add("Nokia Browser");
      b.add("New York Times");
      b.add("OfferUp");
      b.add("OfferUp");
      b.add("Onefootball");
      b.add("Opera browser");
      b.add("Opera browser");
      b.add("Nokia Ovi Browser");
      b.add("New York Times");
      b.add("Pandora");
      b.add("Pinterest");
      b.add("Puffin browser");
      b.add("Relay");
      b.add("Reddit");
      b.add("Silk browser");
      b.add("Skype");
      b.add("SoundCloud");
      b.add("Spotify");
      b.add("Twitter");
      b.add("Uber");
      b.add("Uniqlo");
      b.add("Steam Client");
      b.add("Washington Post");
      b.add("Bing Maps");
      b.add("Yandex browser");
      b.add("Yahoo Japan");
      b.add("Chrome browser");
      b.add("Chrome browser");
      b.add("iCatalog");
      b.add("Mobincube app builder");
      b.add("Android CVP Player");
      b.add("Anvato Platform");
      b.add("Native CFNetwork application");
      b.add("Native Android application");
      b.add("Native CFNetwork application");
      b.add("FreeWheel");
      b.add("Google Tag Manager");
      b.add("upLynk Android Player");
      b.add("VisualOn OSMP+ Video Player");
      b.add("Windows Phone native ad client");
      b.add("Windows Phone native ad client");
   }
}
