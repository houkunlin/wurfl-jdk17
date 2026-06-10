package com.scientiamobile.wurfl.core.utils;

/**
 * Implementation of User Agent With Needle Count.
 */

public class UserAgentWithNeedleCount {
    private String asciiPrintableUserAgent;
    private int plusCharCount;
    private int percentageCharCount;
    private boolean hasSpaceChars;

    UserAgentWithNeedleCount(String asciiPrintableUserAgent, int plusCharCount, int percentageCharCount, boolean hasSpaceChars) {
        this.asciiPrintableUserAgent = asciiPrintableUserAgent;
        this.plusCharCount = plusCharCount;
        this.percentageCharCount = percentageCharCount;
        this.hasSpaceChars = hasSpaceChars;
    }

    /**
     * Returns the asci irintabl ese rgent.
     */

    public String getAsciiPrintableUserAgent() {
        return this.asciiPrintableUserAgent;
    }

    public int getPlusCharCount() {
        return this.plusCharCount;
    }

    /**
     * Returns the percentag eha rount.
 */

    public int getPercentageCharCount() {
        return this.percentageCharCount;
    }

    public boolean hasSpaceChars() {
        return this.hasSpaceChars;
    }
}
