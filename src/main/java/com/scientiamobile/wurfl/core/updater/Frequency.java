package com.scientiamobile.wurfl.core.updater;

/**
 * WURFL 更新频率枚举。
 * <p>定义 WURFL 自动更新的时间间隔策略，以毫秒为单位。
 * 各枚举常量对应不同的更新周期，从每几分钟到每周不等。</p>
 *
 * <ul>
 *   <li>{@link #MINUTES} - 每 1 分钟（60000ms），主要用于测试场景</li>
 *   <li>{@link #DAILY} - 每 24 小时（86400000ms），默认更新频率</li>
 *   <li>{@link #THREE_DAYS} - 每 3 天（259200000ms）</li>
 *   <li>{@link #WEEKLY} - 每 7 天（604800000ms）</li>
 * </ul>
 */

public enum Frequency {
    MINUTES(60000),
    DAILY(86400000),
    THREE_DAYS(259200000),
    WEEKLY(604800000);

    private long value;

    private Frequency(int value) {
        this.value = value;
    }

    /**
     * 获取频率对应的毫秒数值。
     *
     * @return 更新间隔的毫秒数
     */
    public final long value() {
        return this.value;
    }
}
