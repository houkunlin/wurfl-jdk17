package com.scientiamobile.wurfl.core.utils;

import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 版本号比较工具类，支持以任意字符分隔的数字版本号（如 "2.3.1"）。
 * <p>实现了 {@link Comparable} 接口，可对版本号进行逐段数值比较，
 * 而非字符串字典序比较，确保 "10.0" 大于 "9.0"。
 * 常用于 WURFL 设备操作系统版本和浏览器版本的判断逻辑中。</p>
 */

public class Version implements Comparable<Version> {

    /**
     * 版本号各段之间的分隔符
     */
    private final char separator;

    /**
     * 版本号各段的整型数值数组
     */
    private int[] digits;

    /**
     * 使用指定的数字段和分隔符构造版本号。
     *
     * @param digits    版本号各段数值
     * @param separator 分隔符
     */
    private Version(int[] digits, char separator) {
        this.digits = digits;
        this.separator = separator;
    }

    /**
     * 使用默认的分隔符（'.'）将字符串解析为版本号。
     *
     * @param version 版本号字符串，如 "4.0.1"
     * @return 对应的 Version 实例
     * @throws IllegalArgumentException 如果输入为 null 或空字符串
     */
    public static Version valueOf(String version) {
        return valueOf(version, '.');
    }

    /**
     * 使用指定的分隔符将字符串解析为版本号。
     * <p>将字符串按分隔符拆分为数值段数组，每段均转为整数。
     * 如 "2.3.1" 分隔符为 '.' 会得到 [2, 3, 1]。</p>
     *
     * @param version   版本号字符串
     * @param separator 各段之间的分隔符
     * @return 对应的 Version 实例
     * @throws IllegalArgumentException 如果输入为 null 或空字符串
     */
    public static Version valueOf(String version, char separator) {
        if (version != null && !version.isEmpty()) {
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
     * 与另一个 Version 对象进行版本号比较。
     * <p>按位逐段比较数值大小；如果前缀完全相同，则较长版本号中剩余的
     * 非零段会使该版本号更大（即 "2.0" 与 "2.0.0" 视为相等）。</p>
     *
     * @param otherVersion 要比较的另一个版本号
     * @return 负数（小于）、零（等于）、正数（大于）
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
     * 将字符串解析为 Version 并与当前对象比较。
     * <p>便捷方法，等价于 {@code this.compareTo(Version.valueOf(version))}。</p>
     *
     * @param version 待比较的版本号字符串
     * @return 负数（小于）、零（等于）、正数（大于）
     */
    public int compareTo(String version) {
        return this.compareTo(valueOf(version));
    }

    /**
     * 获取指定索引位置的数字段值，如果索引超出则返回 0。
     *
     * @param index 段索引（从 0 开始）
     * @return 该段的数值，索引越界时返回 0
     */
    public int getDigitAtOrZero(int index) {
        return index < this.digits.length ? this.getDigitAtOrThrow(index) : 0;
    }

    /**
     * 获取指定索引位置的数字段值，索引越界时抛出异常。
     *
     * @param index 段索引（从 0 开始）
     * @return 该段的数值
     * @throws ArrayIndexOutOfBoundsException 如果索引超出范围
     */
    public int getDigitAtOrThrow(int index) {
        return this.digits[index];
    }

    /**
     * 返回版本号的字符串表示形式，各段之间以分隔符连接。
     *
     * @return 版本号字符串，如 "2.3.1"
     */
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

    /**
     * 判断两个版本号是否相等。
     * <p>通过 {@link #compareTo(Version)} 方法判断是否返回 0，
     * 而非简单比较内部数组引用。</p>
     *
     * @param object 要比较的对象
     * @return 如果版本号数值相同则返回 {@code true}
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof Version version) {
            return this.compareTo(version) == 0;
        } else {
            return false;
        }
    }

    /**
     * 基于版本号各段数值数组计算哈希码。
     *
     * @return 哈希码
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.digits);
    }
}
