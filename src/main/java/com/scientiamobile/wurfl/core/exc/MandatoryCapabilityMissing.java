package com.scientiamobile.wurfl.core.exc;

import java.io.Serial;

/**
 * Implementation of Mandatory Capability Missing.
 */

public class MandatoryCapabilityMissing extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 233366160908694904L;
    private final String missingMandatoryCapabilities;

    public MandatoryCapabilityMissing(String missingMandatoryCapabilities) {
        this("Mandatory capabilities missing from configuration: ", missingMandatoryCapabilities);
    }

    public MandatoryCapabilityMissing(String messagePrefix, String missingMandatoryCapabilities) {
        super(messagePrefix + missingMandatoryCapabilities);
        this.missingMandatoryCapabilities = missingMandatoryCapabilities;
    }

    /**
     * Returns the missin gandator yapabilities.
     */

    public String getMissingMandatoryCapabilities() {
        return this.missingMandatoryCapabilities;
    }
}
