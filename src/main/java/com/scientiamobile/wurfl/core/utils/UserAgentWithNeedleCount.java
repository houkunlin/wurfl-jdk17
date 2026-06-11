package com.scientiamobile.wurfl.core.utils;

/**
 * User-Agent 字符串及其特殊字符统计的数据传输对象。
 * <p>除了保留仅含 ASCII 可打印字符的 User-Agent 字符串外，
 * 还记录了其中 {@code '+'} 和 {@code '%'} 两种特殊字符的出现次数，
 * 以及是否包含空格。这些信息用于后续判断 User-Agent 是否为 URL 编码格式
 * 或是否符合特定的日志格式特征。</p>
 */

public class UserAgentWithNeedleCount {

    /**
     * 仅包含 ASCII 可打印字符的 User-Agent 字符串
     */
    private String asciiPrintableUserAgent;

    /**
     * 字符串中 '+' 字符的出现次数
     */
    private int plusCharCount;

    /**
     * 字符串中 '%' 字符的出现次数
     */
    private int percentageCharCount;

    /**
     * 字符串中是否包含空格字符
     */
    private boolean hasSpaceChars;

    /**
     * 构造 User-Agent 统计信息对象。
     *
     * @param asciiPrintableUserAgent 清洗后的 ASCII 可打印 User-Agent 字符串
     * @param plusCharCount           '+' 字符的出现次数
     * @param percentageCharCount     '%' 字符的出现次数
     * @param hasSpaceChars           是否包含空格
     */
    UserAgentWithNeedleCount(String asciiPrintableUserAgent, int plusCharCount, int percentageCharCount, boolean hasSpaceChars) {
        this.asciiPrintableUserAgent = asciiPrintableUserAgent;
        this.plusCharCount = plusCharCount;
        this.percentageCharCount = percentageCharCount;
        this.hasSpaceChars = hasSpaceChars;
    }

    /**
     * 获取清洗后的 ASCII 可打印 User-Agent 字符串。
     *
     * @return ASCII 可打印字符构成的 User-Agent
     */
    public String getAsciiPrintableUserAgent() {
        return this.asciiPrintableUserAgent;
    }

    /**
     * 获取 '+' 字符的出现次数。
     * <p>用于判断 User-Agent 是否符合某种日志风格（大量 '+' 表示非标准格式）。</p>
     *
     * @return '+' 字符计数
     */
    public int getPlusCharCount() {
        return this.plusCharCount;
    }

    /**
     * 获取 '%' 字符的出现次数。
     * <p>'%' 数量可用于判断 User-Agent 是否为 URL 编码格式。</p>
     *
     * @return '%' 字符计数
     */
    public int getPercentageCharCount() {
        return this.percentageCharCount;
    }

    /**
     * 判断 User-Agent 字符串中是否包含空格。
     *
     * @return 如果包含空格则返回 {@code true}
     */
    public boolean hasSpaceChars() {
        return this.hasSpaceChars;
    }
}
