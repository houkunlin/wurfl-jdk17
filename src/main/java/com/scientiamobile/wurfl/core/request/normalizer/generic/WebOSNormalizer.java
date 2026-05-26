package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebOSNormalizer implements UserAgentNormalizer {
   private static final Pattern TRAILING_APP_NAME_AND_VERSION_PATTERN = Pattern.compile(" ([^/]+)/([\\d\\.]+)$");
   private static final Pattern WEBOS_MAJOR_VERSION_PATTERN = Pattern.compile("(?:hpw|web)OS.(\\d)\\.");

   public String normalize(String userAgent) {
      Matcher matcher;
      String webOsToken = (matcher = WEBOS_MAJOR_VERSION_PATTERN.matcher(userAgent)).find() ? "webOS".concat(matcher.group(1)) : null;
      String appNameAndVersion = (matcher = TRAILING_APP_NAME_AND_VERSION_PATTERN.matcher(userAgent)).find() ? matcher.group(1) + " " + matcher.group(2) : null;
      return webOsToken != null && appNameAndVersion != null ? appNameAndVersion + " " + webOsToken + "---" + userAgent : userAgent;
   }
}
