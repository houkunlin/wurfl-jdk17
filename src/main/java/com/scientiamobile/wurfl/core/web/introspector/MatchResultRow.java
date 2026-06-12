package com.scientiamobile.wurfl.core.web.introspector;

/**
 * 存储匹配结果的一行数据，包含匹配器信息及对应的 User-Agent 字符串。
 * <p>该类实现了 {@link Comparable} 接口，支持按照匹配器名、设备 ID、归一化 UA、原始 UA 的字典顺序排序，
 * 用于桶匹配结果的汇总展示。</p>
 */

final class MatchResultRow implements Comparable<MatchResultRow> {
    /**
     * 匹配器名称（如 Risk、Normal 等）
     */
    private final String matcherName;
    /**
     * 匹配到的设备 ID
     */
    private final String deviceId;
    /**
     * 经过归一化处理后的 User-Agent 字符串
     */
    private final String normalizedUserAgent;
    /**
     * 原始的 User-Agent 字符串
     */
    private final String originalUserAgent;

    MatchResultRow(String matcherName, String deviceId, String normalizedUserAgent, String originalUserAgent) {
        this.matcherName = matcherName;
        this.deviceId = deviceId;
        this.normalizedUserAgent = normalizedUserAgent;
        this.originalUserAgent = originalUserAgent;
    }

    /**
     * 返回该匹配结果的字符串表示，格式为：{@code 匹配器名\t设备ID\t归一化UA\t原始UA}。
     * <p>匹配器名称会去除末尾的 "Matcher" 后缀，使输出更简洁。</p>
     *
     * @return 制表符分隔的字符串
     */
    @Override
    public String toString() {
        String matcherNameWithoutSuffix = this.matcherName;
        int matcherSuffixIndex;
        matcherSuffixIndex = matcherNameWithoutSuffix.indexOf("Matcher");
        if (matcherSuffixIndex > 0) {
            matcherNameWithoutSuffix = matcherNameWithoutSuffix.substring(0, matcherSuffixIndex);
        }

        return matcherNameWithoutSuffix + "\t" + this.deviceId + "\t" + this.normalizedUserAgent + "\t" + this.originalUserAgent;
    }

    /**
     * 按字典顺序比较两条匹配结果，先比匹配器名，再比设备 ID，再比归一化 UA，最后比原始 UA。
     *
     * @param other 要比较的另一个匹配结果行
     * @return 负值、零或正值，分别表示小于、等于或大于
     */

    public final int compareTo(MatchResultRow other) {
        int c = this.matcherName.compareTo(other.matcherName);
        if (c != 0) {
            return c;
        }

        c = this.deviceId.compareTo(other.deviceId);
        if (c != 0) {
            return c;
        }

        c = this.normalizedUserAgent.compareTo(other.normalizedUserAgent);
        if (c != 0) {
            return c;
        }

        return this.originalUserAgent.compareTo(other.originalUserAgent);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MatchResultRow other)) return false;
        return compareTo(other) == 0;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(matcherName, deviceId, normalizedUserAgent, originalUserAgent);
    }

}
