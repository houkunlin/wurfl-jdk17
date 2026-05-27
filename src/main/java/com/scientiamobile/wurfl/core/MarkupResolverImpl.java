package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

class MarkupResolverImpl implements MarkupResolver, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(MarkupResolverImpl.class);

    @Override
    public MarkUp getMarkupForDevice(InternalDevice device) {
        String xhtmlSupportLevel;
        String preferredMarkup;

        try {
            xhtmlSupportLevel = device.getCapability("xhtml_support_level");
            preferredMarkup = device.getCapability("preferred_markup");
        } catch (CapabilityNotDefinedException e) {
            log.error("It is not possible getting markUp from capabilities: {}", e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }

        MarkUp markup;
        if (Integer.valueOf(xhtmlSupportLevel) >= 3) {
            markup = MarkUp.XHTML_ADVANCED;
        } else if (Integer.valueOf(xhtmlSupportLevel) > 0) {
            markup = MarkUp.XHTML_SIMPLE;
        } else if (preferredMarkup.indexOf("imode") != -1) {
            markup = MarkUp.CHTML;
        } else {
            markup = MarkUp.WML;
        }

        return markup;
    }
}

