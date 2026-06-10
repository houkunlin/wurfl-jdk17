package com.scientiamobile.wurfl.core;

/**
 * 设备支持的标记语言类型，按从低到高的能力级别排列。
 * <p>根据设备的 XHTML 支持级别和首选标记语言计算得出，用于确定设备能够渲染的网页内容类型。</p>
 * <ul>
 *   <li>{@code WML} - 无线标记语言，最低级别</li>
 *   <li>{@code CHTML} - 压缩 HTML（i-mode 设备使用）</li>
 *   <li>{@code XHTML_SIMPLE} - 简单 XHTML</li>
 *   <li>{@code XHTML_ADVANCED} - 高级 XHTML，最高级别</li>
 * </ul>
 */

public final class MarkUp {
    /**
     * 无线标记语言（WML），用于早期功能手机
     */
    public static final MarkUp WML = new MarkUp("WML", -1);
    /**
     * 压缩 HTML（CHTML），i-mode 设备常用
     */
    public static final MarkUp CHTML = new MarkUp("CHTML", 0);
    /** 简单 XHTML 支持 */
    public static final MarkUp XHTML_SIMPLE = new MarkUp("XHTML_SIMPLE", 1);
    /** 高级 XHTML 支持 */
    public static final MarkUp XHTML_ADVANCED = new MarkUp("XHTML_ADVANCED", 2);
    /** 标记语言的数值等级 */
    private int value;
    /** 标记语言的名称 */
    private String name;

    private MarkUp(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
/**
 * 返回标记语言的名称。
 *
 * @return 标记语言名称
 */

    public String toString() {
        return this.name;
    }

    /**
     * 获取标记语言的数值等级。
     *
     * @return 数值等级（WML=-1, CHTML=0, XHTML_SIMPLE=1, XHTML_ADVANCED=2）
 */

    public final int toValue() {
        return this.value;
    }
}
