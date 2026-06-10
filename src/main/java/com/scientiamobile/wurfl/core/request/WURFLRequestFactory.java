package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.EngineTarget;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Factory for creating WURFL Request instances.
 */

public interface WURFLRequestFactory {
    WURFLRequest createRequest(HttpServletRequest request, EngineTarget engineTarget);

    WURFLRequest createRequest(String userAgent, EngineTarget engineTarget);

    WURFLRequest createRequest(WURFLHeaderProvider headerProvider, EngineTarget engineTarget);
}
