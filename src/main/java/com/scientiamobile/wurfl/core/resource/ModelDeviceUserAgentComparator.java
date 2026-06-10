package com.scientiamobile.wurfl.core.resource;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;

/**
 * 设备按 User-Agent 排序的比较器。
 * <p>用于 {@link java.util.TreeSet} 等有序集合中，按 User-Agent 字典序
 * 对 {@link ModelDevice} 进行排序。采用单例模式，通过 {@link #INSTANCE} 访问。</p>
 */

final class ModelDeviceUserAgentComparator implements Serializable, Comparator<ModelDevice> {
    /**
     * 全局唯一实例
     */
    static final ModelDeviceUserAgentComparator INSTANCE = new ModelDeviceUserAgentComparator();
    @Serial
    private static final long serialVersionUID = 101L;

    private ModelDeviceUserAgentComparator() {
    }

    /**
     * 比较两个设备的 User-Agent 字典序。
     *
     * @param left  左侧设备
     * @param right 右侧设备
     * @return 比较结果（负、零、正）
     */

    public final int compare(ModelDevice left, ModelDevice right) {
        return left.getUserAgent().compareTo(right.getUserAgent());
    }
}

