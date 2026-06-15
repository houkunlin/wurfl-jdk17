package com.scientiamobile.wurfl.core.utils;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.strategy.RISMatcher;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 字符串匹配与处理工具类。
 * <p>提供 WURFL 引擎在 User-Agent 解析过程中常用的字符串操作，
 * 包括索引查找（首次/末次/序数位置）、URL 编码解码（rawdecode）、
 * RIS 匹配委托、字符串裁剪以及集合格式化等。</p>
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
     * 消毒用户可控字符串，防止 CRLF 日志注入。
     * <p>替换 \\r、\\n 为安全占位符，确保日志行格式不被破坏。</p>
     */
    public static String sanitizeForLog(String input) {
        if (input == null) return "";
        return input.replace('\r', '␍').replace('\n', '␊');
    }

    /**
     * 获取字符串中第一个斜杠（'/'）之后的索引位置。
     *
     * @param value 输入字符串
     * @return 第一个斜杠后的位置，若不存在则返回 {@link #INDEX_NOT_FOUND}
     */
    public static int firstSlash(String value) {
        return ordinalIndexOfOrNotFound(value, "/", 1);
    }

    /**
     * 获取字符串中第二个斜杠（'/'）之后的索引位置。
     *
     * @param value 输入字符串
     * @return 第二个斜杠后的位置，若不存在则返回 {@link #INDEX_NOT_FOUND}
     */
    public static int secondSlash(String value) {
        return ordinalIndexOfOrNotFound(value, "/", 2);
    }

    /**
     * 获取字符串中第一个右括号（')'）之后的索引位置。
     *
     * @param value 输入字符串
     * @return 第一个右括号后的位置，若不存在则返回 {@link #INDEX_NOT_FOUND}
     */
    public static int firstCloseParenthesis(String value) {
        return ordinalIndexOfOrNotFound(value, ")", 1);
    }

    /**
     * 获取字符串中第一个左括号（'('）之后的索引位置。
     *
     * @param value 输入字符串
     * @return 第一个左括号后的位置，若不存在则返回 {@link #INDEX_NOT_FOUND}
     */
    public static int firstOpenParenthesis(String value) {
        return ordinalIndexOfOrNotFound(value, "(", 1);
    }

    /**
     * 获取字符串中第一个空格之后的索引位置。
     *
     * @param value 输入字符串
     * @return 第一个空格后的位置，若不存在则返回 {@link #INDEX_NOT_FOUND}
     */
    public static int firstSpace(String value) {
        return ordinalIndexOfOrNotFound(value, " ", 1);
    }

    /**
     * 获取字符串中第 N 次出现指定子串之后的索引位置。
     * <p>如果子串出现的次数不足 N 次，则返回 {@link #INDEX_NOT_FOUND}。</p>
     *
     * @param value   输入字符串
     * @param search  要查找的子串
     * @param ordinal 第几次出现（从 1 开始计数）
     * @return 第 N 次出现后第一个字符的位置，若不存在则返回 {@link #INDEX_NOT_FOUND}
     */
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
     * 获取字符串中第一个分号（';'）之后的索引位置。
     * <p>如果未找到分号，则返回字符串末尾位置（即字符串长度）。</p>
     *
     * @param value 输入字符串
     * @return 第一个分号后的位置，若不存在则返回字符串长度
     */
    public static int firstSemiColon(String value) {
        return ordinalIndexOfOrLength(value, ";", 1);
    }

    /**
     * 获取指定子串首次出现的索引位置；如果未找到，则返回字符串长度。
     *
     * @param value  输入字符串
     * @param search 要查找的子串
     * @return 首次出现的索引，若不存在则返回字符串长度
     */
    public static int indexOfOrLength(String value, String search) {
        return indexOfOrLength(value, search, 0);
    }

    /**
     * 从指定位置开始查找子串首次出现的索引位置；如果未找到，则返回字符串长度。
     *
     * @param value     输入字符串
     * @param search    要查找的子串
     * @param fromIndex 起始搜索位置
     * @return 首次出现的索引，若不存在则返回字符串长度
     */
    public static int indexOfOrLength(String value, String search, int fromIndex) {
        return ordinalIndexOfOrLength(value, search, 1, fromIndex);
    }

    /**
     * 获取第 N 次出现指定子串的位置；如果次数不足，则返回字符串长度。
     *
     * @param value   输入字符串
     * @param search  要查找的子串
     * @param ordinal 第几次出现（从 1 开始计数）
     * @return 第 N 次出现的位置，若不存在则返回字符串长度
     */
    public static int ordinalIndexOfOrLength(String value, String search, int ordinal) {
        return ordinalIndexOfOrLength(value, search, ordinal, 0);
    }

    /**
     * 从指定位置开始，获取第 N 次出现指定子串的位置；如果次数不足，则返回字符串长度。
     * <p>该方法的特殊之处在于：当搜索不到指定子串时，返回字符串长度（而非 -1），
     * 便于调用方直接用于子串截取操作而无需额外判空。</p>
     *
     * @param value     输入字符串
     * @param search    要查找的子串
     * @param ordinal   第几次出现（从 1 开始计数）
     * @param fromIndex 起始搜索位置
     * @return 第 N 次出现的位置，若不存在则返回字符串长度
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
     * 使用 RIS（Reduced Initial Substring）匹配算法从一批 User-Agent 中查找最匹配的一项。
     * <p>RIS 匹配是 WURFL 的核心匹配策略之一，通过逐字符缩减目标字符串进行精确匹配。</p>
     *
     * @param userAgents      待匹配的 User-Agent 集合
     * @param userAgent       目标 User-Agent 字符串
     * @param userAgentLength 目标 User-Agent 的长度
     * @return 最匹配的 User-Agent 字符串
     */
    public static String risMatch(List<String> userAgents, String userAgent, int userAgentLength) {
        return RISMatcher.INSTANCE.match(userAgents, userAgent, userAgentLength);
    }

    /**
     * 将设备层级结构格式化为可读的字符串。
     * <p>将设备列表依次连接，中间以 " -> " 分隔，便于日志输出和调试。</p>
     *
     * @param devices 设备列表
     * @return 格式化后的层级字符串，如 "generic -> generic_android -> generic_android_ver2_3"
     */
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
     * 在字符串中查找子串首次出现的位置。
     *
     * @param value  输入字符串
     * @param search 要查找的子串
     * @return 首次出现的索引；如果任一参数为 null 则返回 -1
     */
    public static int indexOf(String value, String search) {
        return value != null && search != null ? value.indexOf(search) : -1;
    }

    /**
     * 从指定起始位置开始查找子串首次出现的位置。
     *
     * @param value     输入字符串
     * @param search    要查找的子串
     * @param fromIndex 起始搜索位置
     * @return 首次出现的索引；如果任一参数为 null 则返回 -1
     */
    public static int indexOf(String value, String search, int fromIndex) {
        return value != null && search != null ? value.indexOf(search, fromIndex) : -1;
    }

    /**
     * 移除字符串中指定子串之前的部分（包含子串本身）。
     * <p>例如 {@code removeSubstringBefore("abc/def", "/")} 返回 {@code "/def"}。</p>
     *
     * @param value  输入字符串
     * @param search 用于定位的参考子串
     * @return 从参考子串开始到末尾的子串；如果参考子串不存在则返回原字符串
     */
    public static String removeSubstringBefore(String value, String search) {
        int index;
        index = value.indexOf(search);
        return index > 0 ? value.substring(index) : value;
    }

    /**
     * 判断字符串是否包含任意一个指定的子串。
     *
     * @param value    输入字符串
     * @param searches 待检查的子串列表
     * @return 如果包含任意一个子串则返回 {@code true}
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
     * 判断字符串是否包含任意一个指定的子串（不区分大小写）。
     * <p>先将输入字符串转为小写后再进行匹配。</p>
     *
     * @param value    输入字符串
     * @param searches 待检查的子串列表
     * @return 如果包含任意一个子串（忽略大小写）则返回 {@code true}
     */
    public static boolean containsAnyOfIgnoreCase(String value, String... searches) {
        return containsAnyOf(value.toLowerCase(Locale.ENGLISH), searches);
    }

    /**
     * 判断字符串是否以任意一个指定的前缀开头。
     *
     * @param value    输入字符串
     * @param prefixes 待检查的前缀列表
     * @return 如果以任意前缀开头则返回 {@code true}
     */
    public static boolean startsWithAnyOf(String value, String... prefixes) {
        for (String prefix : prefixes) {
            if (value.startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 查找字符串中任意一个指定子串首次出现的位置；如果都未找到，则返回字符串长度。
     *
     * @param value    输入字符串
     * @param searches 待查找的子串列表
     * @return 首次匹配的位置，若都未找到则返回字符串长度
     */
    public static int indexOfAnyOrLength(String value, String... searches) {
        return indexOfAnyOrLength(value, searches, 0);
    }

    /**
     * 从指定位置开始查找字符串中任意一个指定子串首次出现的位置；如果都未找到，则返回字符串长度。
     *
     * @param value     输入字符串
     * @param searches  待查找的子串列表
     * @param fromIndex 起始搜索位置
     * @return 首次匹配的位置，若都未找到则返回字符串长度
     */
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
     * 判断字符串是否包含所有指定的子串。
     *
     * @param value    输入字符串
     * @param searches 可变参数形式的子串列表
     * @return 如果包含所有指定的子串则返回 {@code true}
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
     * 判断字符串是否包含集合中所有指定的子串。
     *
     * @param value    输入字符串
     * @param searches 子串列表
     * @return 如果包含所有指定的子串则返回 {@code true}
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
     * 将字符串集合格式化为多行文本，每行一个元素。
     *
     * @param lines 字符串集合
     * @return 以换行符连接的多行字符串
     */
    public static String format(Set<String> lines) {
        StringBuilder out = new StringBuilder(64);

        for (String line : lines) {
            out.append(line).append('\n');
        }

        return out.toString();
    }

    /**
     * 移除字符串右侧出现的所有指定字符。
     *
     * @param value 输入字符串
     * @param trims 需要从右侧移除的字符集合
     * @return 移除右侧指定字符后的字符串
     */
    public static String rtrim(String value, char... trims) {
        int length;
        for (length = value.length(); length > 0 && ArrayUtils.contains(trims, value.charAt(length - 1)); --length) {
        }

        return value.substring(0, length);
    }

    /**
     * 查找字符串中指定字符首次出现的位置（返回的是索引 + 1）。
     * <p>与 {@code indexOf} 的区别在于返回值为字符位置（从 1 开始计数），
     * 而非从 0 开始的索引，适用于某些 WURFL 的场景需求。</p>
     *
     * @param value 输入字符串
     * @param ch    要查找的字符
     * @return 字符位置（索引 + 1），如果未找到则返回 -1
     */
    public static Integer firstChar(String value, char ch) {
        int index;
        index = value.indexOf(ch);
        return index != -1 ? index + 1 : -1;
    }

    /**
     * 对 URL 编码的字符串进行解码。
     * <p>将形如 {@code %XX} 的十六进制转义序列还原为对应字符，
     * 非法的 % 序列将被原样保留。支持指定字符编码（如 UTF-8）。</p>
     *
     * @param value    URL 编码的字符串
     * @param encoding 目标字符编码名称，如 "UTF-8"
     * @return 解码后的字符串；如果输入为空则原样返回
     */
    public static String rawdecode(String value, String encoding) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }

        int length = value.length();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(length);
        Charset charset = Charset.forName(encoding);

        for (int i = 0; i < length; i++) {
            char c = value.charAt(i);
            if (c == '%' && i + 2 < length) {
                int highNibble = Character.digit(value.charAt(i + 1), 16);
                int lowNibble = Character.digit(value.charAt(i + 2), 16);
                if (highNibble != -1 && lowNibble != -1) {
                    buffer.write((highNibble << 4) + lowNibble);
                    i += 2;
                    continue;
                }
            }
            // Write character in the specified encoding
            try {
                buffer.write(String.valueOf(c).getBytes(charset));
            } catch (IOException e) {
                buffer.write(c);
            }
        }

        return buffer.toString(charset);
    }

    /**
     * 将字符转换为两个字节的 UTF 编码表示（大端序）。
     * <p>高 8 位在前，低 8 位在后。此为自定义实现，不同于标准的 UTF-8 编码。</p>
     *
     * @param ch 待转换的字符
     * @return 两字节数组
     */
    public static byte[] charToBytesUTFCustom(char ch) {
        byte[] out;
        out = new byte[2];
        out[0] = (byte) (ch >> 8);
        out[1] = (byte) ch;
        return out;
    }

    /**
     * 使用 UTF-8 编码对 URL 编码的字符串进行解码。
     *
     * @param value URL 编码的字符串
     * @return 解码后的字符串
     */
    public static String rawdecode(String value) {
        return rawdecode(value, "UTF-8");
    }

    /**
     * 使用正则表达式替换字符串中的所有匹配项。
     *
     * @param value       原始字符串
     * @param pattern     正则表达式模式
     * @param replacement 替换字符串
     * @return 替换后的结果
     */
    public static String replaceAll(String value, Pattern pattern, String replacement) {
        return pattern.matcher(value).replaceAll(replacement);
    }
}
