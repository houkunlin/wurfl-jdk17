package com.scientiamobile.wurfl.core.resource;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

/**
 * WURFL 资源集合。
 * <p>管理一组 {@link WURFLResource} 实例的有序集合，支持添加、移除、
 * 遍历和批量释放资源。通常用于持有主 WURFL 数据源和多个补丁数据源，
 * 按顺序应用于 {@link DefaultWURFLModel}。</p>
 */

public final class WURFLResources {
    private final List<WURFLResource> resources = new ArrayList<>();

    /**
     * 创建一个空的资源集合。
     */
    public WURFLResources() {
    }

    /**
     * 使用可变参数创建资源集合。
     *
     * @param resources WURFL 资源数组
     * @throws NullPointerException 如果资源数组为 null
     */
    public WURFLResources(WURFLResource... resources) {
        Validate.notNull(resources, "The resources is null");
        this.resources.addAll(Arrays.asList(resources));
    }

    /**
     * 使用已有的资源集合创建。
     *
     * @param resources WURFL 资源集合
     * @throws NullPointerException     如果集合为 null
     * @throws IllegalArgumentException 如果集合中包含 null 元素
     */
    public WURFLResources(Collection<WURFLResource> resources) {
        Validate.notNull(resources, "The resources is null");
        Validate.noNullElements(resources, "The resources contains null value");
        this.resources.addAll(resources);
    }

    /**
     * 获取集合中的资源数量。
     *
     * @return 资源数量
     */

    public final int size() {
        return this.resources.size();
    }

    /**
     * 根据索引获取资源。
     *
     * @param index 索引位置
     * @return 指定索引处的资源
     */
    public final WURFLResource get(int index) {
        return this.resources.get(index);
    }

    /**
     * 查找指定资源在集合中的索引位置。
     *
     * @param resource 要查找的资源
     * @return 资源所在的索引，不存在则返回 -1
     */

    public final int indexOf(WURFLResource resource) {
        Validate.notNull(resource, "The resource is null");
        return this.resources.indexOf(resource);
    }

    /**
     * 释放集合中所有资源持有的底层系统资源（如文件句柄、输入流等）。
     */

    public final void release() {

        for (WURFLResource resource : this.resources) {
            resource.release();
        }

    }

    /**
     * 向集合中添加一个资源。
     *
     * @param resource 要添加的资源
     * @throws NullPointerException 如果资源为 null
     */

    public final void add(WURFLResource resource) {
        Validate.notNull(resource, "The resource must be not null");
        this.resources.add(resource);
    }

    /**
     * 从集合中移除一个资源。
     *
     * @param resource 要移除的资源
     * @throws NullPointerException 如果资源为 null
     */

    public final void remove(WURFLResource resource) {
        Validate.notNull(resource, "The resource must be not null");
        this.resources.remove(resource);
    }

    /**
     * 返回遍历所有资源的迭代器。
     *
     * @return 资源迭代器
     */

    public final Iterator<WURFLResource> iterator() {
        return this.resources.iterator();
    }

    /**
     * 返回集合中所有资源的字符串表示。
     *
     * @return 包含每个资源信息及版本的字符串
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");

        for (int i = 0; i < this.resources.size(); ++i) {
            WURFLResource resource = this.resources.get(i);
            builder.append(resource).append("(").append(resource.getInfo()).append(" version: ").append(resource.getVersion()).append(")");
            if (i < this.resources.size() - 1) {
                builder.append(" - ");
            }
        }

        builder.append("]");
        return builder.toString();
    }

    /**
     * 判断两个资源集合是否相等。
     *
     * @param object 要比较的对象
     * @return 如果内部资源列表相同则返回 true
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof WURFLResources other)) {
            return false;
        } else {
            return (new EqualsBuilder()).append(this.resources, other.resources).isEquals();
        }
    }

    /**
     * 计算哈希码。
     *
     * @return 哈希码
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(53, 79)
                .append(this.getClass())
                .append(this.resources)
                .toHashCode();
    }
}
