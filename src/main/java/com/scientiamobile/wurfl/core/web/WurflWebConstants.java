package com.scientiamobile.wurfl.core.web;

import com.scientiamobile.wurfl.core.WURFLEngine;

public interface WurflWebConstants {
   String WURFL_ENGINE_KEY_PARAM = "wurflEngineKey";
   String WURFL_ENGINE_KEY = WURFLEngine.class.getName();
   String WURFL_PATCH = "wurflPatch";
   String WURFL = "wurfl";
   String WURFL_DEFAULT_LOCATION = "/WEB-INF/wurfl.zip";
   String WURFL_ENGINE_TARGET_KEY = "wurflEngineTarget";
   String WURFL_USER_AGENT_PRIORITY_KEY = "wurflUserAgentPriority";
}
