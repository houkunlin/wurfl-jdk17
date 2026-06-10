package com.scientiamobile.wurfl.core.resource;

/**
 * Implementation of Wurfl Xml Parse State.
 */

final class WurflXmlParseState {
    static final int START_DOCUMENT = 1;
    static final int WURFL = 2;
    static final int VERSION = 3;
    static final int VERSION_VER = 4;
    static final int VERSION_LAST_UPDATED = 5;
    static final int VERSION_SMID = 6;
    static final int DEVICES = 7;
    static final int DEVICE = 8;
    static final int GROUP = 9;
    static final int CAPABILITY = 10;
    static final int END = 11;
}
