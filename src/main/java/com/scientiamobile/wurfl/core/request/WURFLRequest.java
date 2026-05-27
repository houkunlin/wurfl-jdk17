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

    void normalizeUserAgent(UserAgentNormalizer normalizer);

    void performGenericNormalization();

    String getUserAgentProfile();

    String getHeader(String headerName);

    Map<String, String> getHeaders();

    EngineTarget getEngineTarget();

    boolean _internalIsMobileBrowser();

    boolean _internalIsDesktopBrowser();

    boolean _internalIsDesktopBrowserHeavyDutyAnalysis();

    boolean _internalIsBot();

    boolean _internalIsSmartTvBrowser();

    boolean _internalIsEmailClient();

    boolean isUrlEncoded();

    void setUrlEncoded(boolean urlEncoded);
}
