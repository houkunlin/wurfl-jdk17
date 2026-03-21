package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.EngineTarget;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.Map;

public interface WURFLRequest {
  String getDeviceUserAgent();
  
  String getBrowserUserAgent();
  
  String getCleanedDeviceUserAgent();
  
  String getNormalizedDeviceUserAgent();
  
  String getOriginalUserAgent();
  
  void normalizeUserAgent(UserAgentNormalizer paramUserAgentNormalizer);
  
  void performGenericNormalization();
  
  String getUserAgentProfile();
  
  String getHeader(String paramString);
  
  Map getHeaders();
  
  EngineTarget getEngineTarget();
  
  boolean _internalIsMobileBrowser();
  
  boolean _internalIsDesktopBrowser();
  
  boolean _internalIsDesktopBrowserHeavyDutyAnalysis();
  
  boolean _internalIsBot();
  
  boolean _internalIsSmartTvBrowser();
  
  boolean _internalIsEmailClient();
  
  boolean isUrlEncoded();
  
  void setUrlEncoded(boolean paramBoolean);
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\WURFLRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */