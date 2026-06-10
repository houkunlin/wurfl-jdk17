package com.scientiamobile.wurfl.core.utils;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.strategy.RISMatcher;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Implementation of String Match Utils.
 */

public final class StringMatchUtils {
    public static final String NULL_STRING;
    public static final String EMPTY_STRING = "";
    public static final int INDEX_NOT_FOUND = -1;

    static {
        LoggerFactory.getLogger(StringMatchUtils.class);
        NULL_STRING = null;
    }

    private StringMatchUtils() {
    }

    /**
     * Firs tlash.
     */

    public static int firstSlash(String value) {
        return ordinalIndexOfOrNotFound(value, "/", 1);
    }

    public static int secondSlash(String value) {
        return ordinalIndexOfOrNotFound(value, "/", 2);
    }

    /**
     * Firs tlos earenthesis.
 */

    public static int firstCloseParenthesis(String value) {
        return ordinalIndexOfOrNotFound(value, ")", 1);
    }

    public static int firstOpenParenthesis(String value) {
        return ordinalIndexOfOrNotFound(value, "(", 1);
    }

    /**
     * Firs tpace.
 */

    public static int firstSpace(String value) {
        return ordinalIndexOfOrNotFound(value, " ", 1);
    }

    public static int ordinalIndexOfOrNotFound(String value, String search, int ordinal) {
        if (search == null) {
            return -1;
        } else {
            int index;
            index = StringUtils.ordinalIndexOf(value, search, ordinal);
            return index == -1 ? -1 : index + search.length();
        }
    }

    /**
     * Firs tem iolon.
 */

    public static int firstSemiColon(String value) {
        return ordinalIndexOfOrLength(value, ";", 1);
    }

    public static int indexOfOrLength(String value, String search) {
        return indexOfOrLength(value, search, 0);
    }

    /**
     * Inde x f rength.
 */

    public static int indexOfOrLength(String value, String search, int fromIndex) {
        return ordinalIndexOfOrLength(value, search, 1, fromIndex);
    }

    public static int ordinalIndexOfOrLength(String value, String search, int ordinal) {
        return ordinalIndexOfOrLength(value, search, ordinal, 0);
    }

    /**
     * Ordina lnde x f rength.
 */

    public static int ordinalIndexOfOrLength(String value, String search, int ordinal, int fromIndex) {
        String nonNullValue = StringUtils.defaultString(value);
        if (fromIndex < 0) {
            return nonNullValue.length();
        } else {
            int index;
            index = StringUtils.ordinalIndexOf(StringUtils.substring(nonNullValue, fromIndex), search, ordinal);
            if (index >= 0) {
                index += fromIndex;
            } else {
                index = value.length();
            }

            return index;
        }
    }

    /**
     * Ri satch.
 */

    public static String risMatch(Collection<String> userAgents, String userAgent, int userAgentLength) {
        return RISMatcher.INSTANCE.match(userAgents, userAgent, userAgentLength);
    }

    public static String hierarchyAsString(List<ModelDevice> devices) {
        StringBuilder out = new StringBuilder();
        for (ModelDevice device : devices) {
            if (!out.isEmpty()) {
                out.append(" -> ");
            }
            out.append(device.getID());
        }
        return out.toString();
    }

    /**
     * Inde xf.
 */

    public static int indexOf(String value, String search) {
        return value != null && search != null ? value.indexOf(search) : -1;
    }

    public static int indexOf(String value, String search, int fromIndex) {
        return value != null && search != null ? value.indexOf(search, fromIndex) : -1;
    }

    /**
     * Remov eubstrin gefore.
 */

    public static String removeSubstringBefore(String value, String search) {
        int index;
        index = value.indexOf(search);
        return index > 0 ? value.substring(index) : value;
    }

    /**
     * Contain sn yf.
 */

    public static boolean containsAnyOf(String value, String... searches) {
        for (String s : searches) {
            if (value.contains(s)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Contain sn y fgnor ease.
 */

    public static boolean containsAnyOfIgnoreCase(String value, String... searches) {
        return containsAnyOf(value.toLowerCase(Locale.ENGLISH), searches);
    }

    public static boolean startsWithAnyOf(String value, String... prefixes) {
        for (String prefix : prefixes) {
            if (value.startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Inde x fn y rength.
 */

    public static int indexOfAnyOrLength(String value, String... searches) {
        return indexOfAnyOrLength(value, searches, 0);
    }

    public static int indexOfAnyOrLength(String value, String[] searches, int fromIndex) {
        if (fromIndex == -1) {
            return value.length();
        } else {
            int index;
            index = StringUtils.indexOfAny(fromIndex > 0 ? value.substring(fromIndex) : value, searches);
            return index >= 0 ? index + fromIndex : value.length();
        }
    }

    /**
     * Contain sl lf.
 */

    public static boolean containsAllOf(String value, String... searches) {
        for (String s : searches) {
            if (value.indexOf(s) == -1) {
                return false;
            }
        }

        return true;
    }

    /**
     * Contain sl lf.
 */

    public static boolean containsAllOf(String value, List<String> searches) {
        for (String s : searches) {
            if (value.indexOf(s) == -1) {
                return false;
            }
        }

        return true;
    }

    /**
     * Format.
 */

    public static String format(Set<String> lines) {
        StringBuilder out = new StringBuilder(64);

        for (String line : lines) {
            out.append(line).append('\n');
        }

        return out.toString();
    }

    /**
     * Rtrim.
 */

    public static String rtrim(String value, char... trims) {
        int length;
        for (length = value.length(); length > 0 && ArrayUtils.contains(trims, value.charAt(length - 1)); --length) {
        }

        return value.substring(0, length);
    }

    /**
     * Firs thar.
 */

    public static Integer firstChar(String value, char ch) {
        int index;
        index = value.indexOf(ch);
        return index != -1 ? index + 1 : -1;
    }

    /**
     * Rawdecode.
 */

    public static String rawdecode(String value, String encoding) {
        if (StringUtils.isEmpty(value)) {
            return value;
        } else {
            int length = value.length();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream(length);

            try {
                for (int i = 0; i < length; ++i) {
                    if (value.charAt(i) == '%' && i + 2 < length) {
                        int highNibble = value.charAt(i + 1);
                        int lowNibble = value.charAt(i + 2);
                        highNibble = Character.digit((char) highNibble, 16);
                        lowNibble = Character.digit((char) lowNibble, 16);
                        if (highNibble != -1 && lowNibble != -1) {
                            int decoded = (char) ((highNibble << 4) + lowNibble);
                            buffer.write(decoded);
                            i += 2;
                        } else {
                            writeChar(buffer, value, i, encoding);
                        }
                    } else {
                        writeChar(buffer, value, i, encoding);
                    }
                }
            } finally {
                try {
                    buffer.close();
                } catch (IOException ignore) {
                }

            }

            return new String(buffer.toByteArray(), Charset.forName(encoding));
        }
    }

    /**
     * Writ ehar.
 */

    private static void writeChar(ByteArrayOutputStream buffer, String value, int index, String encoding) {
        char ch = value.charAt(index);
        String stringValue = new String(new char[]{ch});

        try {
            buffer.write(stringValue.getBytes(encoding));
        } catch (IOException ignore) {
            buffer.write(ch);
        }
    }

    /**
     * Cha r oyte stfcustom.
 */

    public static byte[] charToBytesUTFCustom(char ch) {
        byte[] out;
        out = new byte[2];
        out[0] = (byte) (ch >> 8);
        out[1] = (byte) ch;
        return out;
    }

    /**
     * Rawdecode.
 */

    public static String rawdecode(String value) {
        return rawdecode(value, "UTF-8");
    }

    public static String replaceAll(String value, Pattern pattern, String replacement) {
        return pattern.matcher(value).replaceAll(replacement);
    }
}
