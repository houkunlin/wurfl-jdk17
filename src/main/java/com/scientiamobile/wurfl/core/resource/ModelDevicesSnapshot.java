package com.scientiamobile.wurfl.core.resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 设备集合快照。
 * <p>封装一次 WURFL 数据加载的结果，包含设备集合、版本信息、
 * 资源信息、是否补丁等元数据。快照是不可变的数据视图，
 * 用于在模型构建过程中传递解析结果。</p>
 */

public final class ModelDevicesSnapshot implements Serializable, Comparable<ModelDevicesSnapshot> {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 资源的描述信息
     */
    private final String info;
    /**
     * 版本号
     */
    private final String version;
    /**
     * 是否为补丁数据
     */
    private final boolean patch;
    /**
     * 设备集合
     */
    private final ModelDevices devices;
    /**
     * 缓存的计算得到的快照键
     */
    private transient String cachedKey;
    /**
     * WURFL SMID（安全模型标识符）
     */
    private final String smid;

    /**
     * 创建设备集合快照。
     *
     * @param info    资源描述信息
     * @param version 版本号
     * @param patch   是否为补丁数据
     * @param devices 设备集合
     * @param smid    WURFL 安全模型标识符
     */
    public ModelDevicesSnapshot(String info, String version, boolean patch, ModelDevices devices, String smid) {
        this.info = info;
        this.version = version;
        this.patch = patch;
        this.devices = devices;
        this.smid = smid;
    }

    /**
     * 获取快照的唯一标识键。
     * <p>由资源类型（Root/Patch）、资源信息和版本号拼接而成，用于区分不同的快照。</p>
     *
     * @return 快照键字符串
     */

    public final String getSnapshotKey() {
        if (this.cachedKey == null) {
            StringBuilder builder = (new StringBuilder()).append(this.patch ? "Patch" : "Root").append(":").append(this.info);
            if (StringUtils.isNotBlank(this.version)) {
                builder.append(":").append(this.version);
            }

            this.cachedKey = builder.toString();
        }

        return this.cachedKey;
    }

    /**
     * 复制快照中的设备集合（深拷贝）。
     *
     * @return 新的设备集合副本
     */

    public final ModelDevices copyDevices() {
        return new ModelDevices(this.devices);
    }

    /**
     * 计算哈希码，基于 info 和 version。
     *
     * @return 哈希码
     */
    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder;
        hashCodeBuilder = new HashCodeBuilder(33, 55);
        hashCodeBuilder.append(this.info).append(this.version);
        return hashCodeBuilder.toHashCode();
    }

    /**
     * 判断两个快照是否相等，基于快照键比较。
     *
     * @param other 要比较的对象
     * @return 如果快照键相同则返回 true
     */
    @Override
    public boolean equals(Object other) {
        if (!this.getClass().isInstance(other)) {
            return false;
        }

        ModelDevicesSnapshot o = (ModelDevicesSnapshot) other;
        EqualsBuilder equalsBuilder;
        equalsBuilder = new EqualsBuilder();
        equalsBuilder.append(this.getSnapshotKey(), o.getSnapshotKey());
        return equalsBuilder.isEquals();
    }

    /**
     * 返回快照的字符串表示。
     *
     * @return 包含 info 和 version 的字符串
     */
    @Override
    public String toString() {
        ToStringBuilder toStringBuilder;
        toStringBuilder = new ToStringBuilder(this);
        toStringBuilder.append(this.info).append(this.version);
        return toStringBuilder.toString();
    }

    /**
     * 获取 WURFL SMID。
     *
     * @return SMID 字符串
     */
    final String getSmid() {
        return this.smid;
    }

    /**
     * 比较两个快照的排序顺序，基于 info 和 version。
     *
     * @param other 另一个快照
     * @return 比较结果
     */

    public final int compareTo(ModelDevicesSnapshot other) {
        CompareToBuilder compareToBuilder;
        compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(this.info, other.info).append(this.version, other.version);
        return compareToBuilder.toComparison();
    }
}
