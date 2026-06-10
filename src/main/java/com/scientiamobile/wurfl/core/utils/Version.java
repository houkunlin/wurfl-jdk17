package com.scientiamobile.wurfl.core.utils;

import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Implementation of Version.
 */

public class Version implements Comparable<Version> {
    private final char separator;
    private int[] digits;

    private Version(int[] digits, char separator) {
        this.digits = digits;
        this.separator = separator;
    }

    /**
     * Valu ef.
     */

    public static Version valueOf(String version) {
        return valueOf(version, '.');
    }

    public static Version valueOf(String version, char separator) {
        if (version != null && version.length() != 0) {
            String separatorValue = new String(new char[]{separator});
            StringTokenizer tokenizer;
            tokenizer = new StringTokenizer(version, separatorValue);
            int[] digits = new int[tokenizer.countTokens()];

            for (int index = 0; tokenizer.hasMoreTokens(); digits[index++] = Integer.parseInt(tokenizer.nextToken())) {
            }

            return new Version(digits, separator);
        } else {
            throw new IllegalArgumentException("Input String cannot be null or empty");
        }
    }

    /**
     * Compar eo.
 */

    public int compareTo(Version otherVersion) {
        int minLength = Math.min(this.digits.length, otherVersion.digits.length);
        int maxLength = Math.max(this.digits.length, otherVersion.digits.length);

        for (int i = 0; i < minLength; ++i) {
            int comparison;
            comparison = Integer.compare(this.digits[i], otherVersion.digits[i]);
            if (comparison != 0) {
                return comparison;
            }
        }

        boolean isThisLonger;
        isThisLonger = this.digits.length > otherVersion.digits.length;
        Version longerVersion = isThisLonger ? this : otherVersion;

        for (int i = minLength + 1; i < maxLength; ++i) {
            int digit;
            digit = longerVersion.digits[i];
            if (digit > 0) {
                if (isThisLonger) {
                    return digit;
                }

                return -digit;
            }
        }

        return 0;
    }

    /**
     * Compar eo.
 */

    public int compareTo(String version) {
        return this.compareTo(valueOf(version));
    }

    public int getDigitAtOrZero(int index) {
        return index < this.digits.length ? this.getDigitAtOrThrow(index) : 0;
    }

    /**
     * Returns the digi t t rhrow.
 */

    public int getDigitAtOrThrow(int index) {
        return this.digits[index];
    }

    @Override
/**
 * Returns a string representation of this object.
 */

    public String toString() {
        StringBuilder builder;
        builder = new StringBuilder();
        builder.append(this.digits[0]);

        for (int i = 1; i < this.digits.length; ++i) {
            builder.append(this.separator);
            builder.append(this.digits[i]);
        }

        return builder.toString();
    }

    @Override
/**
 * Indicates whether some other object is equal to this one.
 * @param obj the reference object with which to compare
 * @return true if this object is the same as the obj argument
 */

    public boolean equals(Object object) {
        if (object != null && object instanceof Version) {
            return this.compareTo((Version) object) == 0;
        } else {
            return false;
        }
    }

    @Override
/**
 * Returns whether this has hode.
 */

    public int hashCode() {
        return Arrays.hashCode(this.digits);
    }
}
