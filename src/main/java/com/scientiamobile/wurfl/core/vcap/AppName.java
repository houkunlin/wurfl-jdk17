package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppName implements VirtualCapabilityEvaluator, Serializable {
   private static final List<String> APP_INDICATOR_KEYWORDS = new ArrayList<>();
   private static final List<String> APP_NAMES = new ArrayList<>();
   private static final long serialVersionUID = 7704959740704532442L;
   private static final Pattern WEBVIEW_APP_PATTERN = Pattern.compile("WebViewApp ([^/]+)/");
   private static final Pattern ANDROID_DALVIK_APP_PATTERN = Pattern.compile("^([^/]+)/.+? Dalvik/");
   private static final Pattern IOS_CFNETWORK_APP_PATTERN = Pattern.compile("^([^/]+)/[\\d\\.-_]+ i(?:Phone|Pad|Pod)\\d+?,\\d+? iOS/[\\d_]+ CFNetwork/[\\d\\.]+");
   private static final Pattern WINDOWS_PHONE_APP_PATTERN = Pattern.compile("^([^/]+)/[0-9\\.-_]+ Windows Phone/[\\d\\.]+");

   @Override
   public String eval(Device device, WURFLRequest request) {
      String userAgent = request.isUrlEncoded() ? request.getCleanedDeviceUserAgent() : request.getOriginalUserAgent();

      if (userAgent.contains("WebViewApp")) {
         Matcher webViewAppMatcher = WEBVIEW_APP_PATTERN.matcher(userAgent);
         return webViewAppMatcher.find() ? webViewAppMatcher.group(1) : "WebView";
      } else {
         Matcher appNameMatcher;
         appNameMatcher = ANDROID_DALVIK_APP_PATTERN.matcher(userAgent);
      if (appNameMatcher.find()) {
            return appNameMatcher.group(1);
         } else appNameMatcher = IOS_CFNETWORK_APP_PATTERN.matcher(userAgent);
      if (appNameMatcher.find()) {
            return appNameMatcher.group(1);
         } else appNameMatcher = WINDOWS_PHONE_APP_PATTERN.matcher(userAgent);
      if (appNameMatcher.find()) {
            return appNameMatcher.group(1);
         } else {
            for(int i = 0; i < APP_INDICATOR_KEYWORDS.size(); ++i) {
               if (userAgent.contains(APP_INDICATOR_KEYWORDS.get(i))) {
                  return APP_NAMES.get(i);
               }
            }

            return "Stock Browser";
         }
      }
   }

   @Override
   public String getHandledVirtualCapabilityName() {
      return "advertised_app_name";
   }

   static {
      APP_INDICATOR_KEYWORDS.add("abcf/");
      APP_INDICATOR_KEYWORDS.add("adultswim");
      APP_INDICATOR_KEYWORDS.add("Aliexpress");
      APP_INDICATOR_KEYWORDS.add("AOLShield");
      APP_INDICATOR_KEYWORDS.add(" GSA/");
      APP_INDICATOR_KEYWORDS.add("AmazonAdSDK");
      APP_INDICATOR_KEYWORDS.add("Appstore/release-");
      APP_INDICATOR_KEYWORDS.add("AppStore/");
      APP_INDICATOR_KEYWORDS.add("Amazon");
      APP_INDICATOR_KEYWORDS.add("Argo/");
      APP_INDICATOR_KEYWORDS.add("BBCStoryOfLife");
      APP_INDICATOR_KEYWORDS.add("bdbrowser");
      APP_INDICATOR_KEYWORDS.add("com.apple.tv");
      APP_INDICATOR_KEYWORDS.add("comedycentral");
      APP_INDICATOR_KEYWORDS.add("DroidRBMobile");
      APP_INDICATOR_KEYWORDS.add("ESPN/");
      APP_INDICATOR_KEYWORDS.add("FB_IAB/MESSENGER");
      APP_INDICATOR_KEYWORDS.add("FB_IAB");
      APP_INDICATOR_KEYWORDS.add("FB4A");
      APP_INDICATOR_KEYWORDS.add("FBAV/");
      APP_INDICATOR_KEYWORDS.add("UCWEB");
      APP_INDICATOR_KEYWORDS.add("UCBrowser");
      APP_INDICATOR_KEYWORDS.add(" Edge/");
      APP_INDICATOR_KEYWORDS.add(" EdgiOS/");
      APP_INDICATOR_KEYWORDS.add(" EdgA/");
      APP_INDICATOR_KEYWORDS.add(" Focus/");
      APP_INDICATOR_KEYWORDS.add(" Firefox/");
      APP_INDICATOR_KEYWORDS.add(" FxiOS/");
      APP_INDICATOR_KEYWORDS.add("Fennec");
      APP_INDICATOR_KEYWORDS.add("Flipboard");
      APP_INDICATOR_KEYWORDS.add("fxnetworks");
      APP_INDICATOR_KEYWORDS.add("Groupon");
      APP_INDICATOR_KEYWORDS.add("hola_android");
      APP_INDICATOR_KEYWORDS.add("iHeartRadio");
      APP_INDICATOR_KEYWORDS.add("Indeed App");
      APP_INDICATOR_KEYWORDS.add("(InstaFollow)");
      APP_INDICATOR_KEYWORDS.add("Instagram");
      APP_INDICATOR_KEYWORDS.add("itunesstored/");
      APP_INDICATOR_KEYWORDS.add("Kik/");
      APP_INDICATOR_KEYWORDS.add("LA Times/");
      APP_INDICATOR_KEYWORDS.add("Liebao");
      APP_INDICATOR_KEYWORDS.add("Line/");
      APP_INDICATOR_KEYWORDS.add("MicroMessenger");
      APP_INDICATOR_KEYWORDS.add("Microsoft Outlook");
      APP_INDICATOR_KEYWORDS.add("Microsoft Office/");
      APP_INDICATOR_KEYWORDS.add("MSIE");
      APP_INDICATOR_KEYWORDS.add("NAVER");
      APP_INDICATOR_KEYWORDS.add("NewsWeather/");
      APP_INDICATOR_KEYWORDS.add("NokiaBrowser");
      APP_INDICATOR_KEYWORDS.add("nyt_android");
      APP_INDICATOR_KEYWORDS.add("offerup");
      APP_INDICATOR_KEYWORDS.add("OfferUp");
      APP_INDICATOR_KEYWORDS.add("Onefootball");
      APP_INDICATOR_KEYWORDS.add("Opera");
      APP_INDICATOR_KEYWORDS.add(" OPR/");
      APP_INDICATOR_KEYWORDS.add("OviBrowser");
      APP_INDICATOR_KEYWORDS.add("nytiphone");
      APP_INDICATOR_KEYWORDS.add("Pandora");
      APP_INDICATOR_KEYWORDS.add("Pinterest");
      APP_INDICATOR_KEYWORDS.add("Puffin");
      APP_INDICATOR_KEYWORDS.add("Relay");
      APP_INDICATOR_KEYWORDS.add("Reddit");
      APP_INDICATOR_KEYWORDS.add(" Silk/");
      APP_INDICATOR_KEYWORDS.add("Skype");
      APP_INDICATOR_KEYWORDS.add("SoundCloud");
      APP_INDICATOR_KEYWORDS.add("Spotify");
      APP_INDICATOR_KEYWORDS.add("Twitter");
      APP_INDICATOR_KEYWORDS.add("Uber/");
      APP_INDICATOR_KEYWORDS.add("uniqlo-app");
      APP_INDICATOR_KEYWORDS.add("Valve Steam GameOverlay");
      APP_INDICATOR_KEYWORDS.add("Wash Post");
      APP_INDICATOR_KEYWORDS.add("Windows Maps");
      APP_INDICATOR_KEYWORDS.add("YaBrowser");
      APP_INDICATOR_KEYWORDS.add("YJApp");
      APP_INDICATOR_KEYWORDS.add(" CriOS/");
      APP_INDICATOR_KEYWORDS.add(" Chrome/");
      APP_INDICATOR_KEYWORDS.add("iCatalog");
      APP_INDICATOR_KEYWORDS.add("mobincube");
      APP_INDICATOR_KEYWORDS.add("AndroidCvpPlayer");
      APP_INDICATOR_KEYWORDS.add("ANVSDK");
      APP_INDICATOR_KEYWORDS.add("CFNetwork");
      APP_INDICATOR_KEYWORDS.add("Dalvik");
      APP_INDICATOR_KEYWORDS.add("Darwin");
      APP_INDICATOR_KEYWORDS.add("FreeWheelAdManager");
      APP_INDICATOR_KEYWORDS.add("GoogleTagManager");
      APP_INDICATOR_KEYWORDS.add("upLynkAndroidPlayer");
      APP_INDICATOR_KEYWORDS.add("VisualOn OSMP+ Player");
      APP_INDICATOR_KEYWORDS.add("WindowsPhoneAdClient");
      APP_INDICATOR_KEYWORDS.add("Windows Phone Ad Client");
      APP_NAMES.add("Freeform/ABC Family");
      APP_NAMES.add("AdultSwim");
      APP_NAMES.add("AliExpress");
      APP_NAMES.add("AOL Shield browser");
      APP_NAMES.add("Google Search");
      APP_NAMES.add("Amazon Ad SDK");
      APP_NAMES.add("Amazon App Store");
      APP_NAMES.add("iOS App Store");
      APP_NAMES.add("Amazon App");
      APP_NAMES.add("Netflix");
      APP_NAMES.add("BBC Story of Life");
      APP_NAMES.add("Baidu browser");
      APP_NAMES.add("TV on iOS");
      APP_NAMES.add("Comedy Central");
      APP_NAMES.add("Redbox");
      APP_NAMES.add("ESPN");
      APP_NAMES.add("Facebook Messenger");
      APP_NAMES.add("Facebook");
      APP_NAMES.add("Facebook");
      APP_NAMES.add("Facebook");
      APP_NAMES.add("UCBrowser");
      APP_NAMES.add("UCBrowser");
      APP_NAMES.add("Edge Browser");
      APP_NAMES.add("Edge Browser");
      APP_NAMES.add("Edge Browser");
      APP_NAMES.add("Firefox Focus browser");
      APP_NAMES.add("Firefox browser");
      APP_NAMES.add("Firefox browser");
      APP_NAMES.add("Fennec browser");
      APP_NAMES.add("Flipboard");
      APP_NAMES.add("FX Network");
      APP_NAMES.add("Groupon");
      APP_NAMES.add("Hola VPN");
      APP_NAMES.add("iHeartRadio");
      APP_NAMES.add("Indeed");
      APP_NAMES.add("InstaFollow");
      APP_NAMES.add("Instagram");
      APP_NAMES.add("iTunes");
      APP_NAMES.add("Kik");
      APP_NAMES.add("LA Times");
      APP_NAMES.add("Liebao");
      APP_NAMES.add("LINE");
      APP_NAMES.add("WeChat");
      APP_NAMES.add("Microsoft Outlook");
      APP_NAMES.add("Microsoft Office");
      APP_NAMES.add("Internet Explorer");
      APP_NAMES.add("Naver Search");
      APP_NAMES.add("Google News & Weather");
      APP_NAMES.add("Nokia Browser");
      APP_NAMES.add("New York Times");
      APP_NAMES.add("OfferUp");
      APP_NAMES.add("OfferUp");
      APP_NAMES.add("Onefootball");
      APP_NAMES.add("Opera browser");
      APP_NAMES.add("Opera browser");
      APP_NAMES.add("Nokia Ovi Browser");
      APP_NAMES.add("New York Times");
      APP_NAMES.add("Pandora");
      APP_NAMES.add("Pinterest");
      APP_NAMES.add("Puffin browser");
      APP_NAMES.add("Relay");
      APP_NAMES.add("Reddit");
      APP_NAMES.add("Silk browser");
      APP_NAMES.add("Skype");
      APP_NAMES.add("SoundCloud");
      APP_NAMES.add("Spotify");
      APP_NAMES.add("Twitter");
      APP_NAMES.add("Uber");
      APP_NAMES.add("Uniqlo");
      APP_NAMES.add("Steam Client");
      APP_NAMES.add("Washington Post");
      APP_NAMES.add("Bing Maps");
      APP_NAMES.add("Yandex browser");
      APP_NAMES.add("Yahoo Japan");
      APP_NAMES.add("Chrome browser");
      APP_NAMES.add("Chrome browser");
      APP_NAMES.add("iCatalog");
      APP_NAMES.add("Mobincube app builder");
      APP_NAMES.add("Android CVP Player");
      APP_NAMES.add("Anvato Platform");
      APP_NAMES.add("Native CFNetwork application");
      APP_NAMES.add("Native Android application");
      APP_NAMES.add("Native CFNetwork application");
      APP_NAMES.add("FreeWheel");
      APP_NAMES.add("Google Tag Manager");
      APP_NAMES.add("upLynk Android Player");
      APP_NAMES.add("VisualOn OSMP+ Video Player");
      APP_NAMES.add("Windows Phone native ad client");
      APP_NAMES.add("Windows Phone native ad client");
   }
}
