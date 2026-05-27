package com.scientiamobile.wurfl.core;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

final class ApiConfigHandler extends DefaultHandler {
    private WURFLServiceImpl wurflService;

    private ApiConfigHandler(WURFLServiceImpl wurflService) {
        this.wurflService = wurflService;
    }

    ApiConfigHandler(WURFLServiceImpl wurflService, byte ignored) {
        this(wurflService);
    }

    public final void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("wurfl-api-config")) {
            String engineTarget = attributes.getValue("engine-target");
            if (WURFLServiceImpl.getEngineTarget(this.wurflService) == null) {
                if (engineTarget == null || "performance".equals(engineTarget) || "accuracy".equals(engineTarget)) {
                    WURFLServiceImpl.setEngineTarget(this.wurflService, EngineTarget.defaultTarget);
                    return;
                }

                WURFLServiceImpl.setEngineTarget(this.wurflService, EngineTarget.fastDesktopBrowserMatch);
            }
        }

    }
}

