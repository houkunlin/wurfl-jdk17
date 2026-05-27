package com.scientiamobile.wurfl.core.utils;

import java.util.Arrays;
import java.util.StringTokenizer;

public class Version implements Comparable<Version> {
    private final char separator;
    private int[] digits;

    private Version(int[] digits, char separator) {
        this.digits = digits;
        this.separator = separator;
    }

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

    public int compareTo(String version) {
        return this.compareTo(valueOf(version));
    }

    public int getDigitAtOrZero(int index) {
        return index < this.digits.length ? this.getDigitAtOrThrow(index) : 0;
    }

    public int getDigitAtOrThrow(int index) {
        return this.digits[index];
    }

    @Override
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
    public boolean equals(Object object) {
        if (object != null && object instanceof Version) {
            return this.compareTo((Version) object) == 0;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.digits);
    }
}
