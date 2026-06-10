package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.utils.CollectionFactory;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * WURFL 设备集合。
 * <p>管理一组 {@link ModelDevice} 实例，提供按设备 ID、插入顺序等方式
 * 对设备进行增删改查的能力。内部使用线程安全的 ConcurrentHashMap 存储
 * 设备 ID 到设备对象的映射，并维护设备的插入顺序列表。</p>
 */

public class ModelDevices implements Serializable, Iterable<ModelDevice> {
    @Serial
    private static final long serialVersionUID = 10L;
    /**
     * 设备 ID 到设备对象的映射
     */
    private final Map<String, ModelDevice> devicesById;
    /**
     * 按插入顺序维护的设备 ID 列表
     */
    private LinkedList<String> deviceIdsByInsertionOrder;

    /**
     * 创建一个空的设备集合。
     */
    public ModelDevices() {
        this.devicesById = CollectionFactory.createConcurrentHashMap();
        this.deviceIdsByInsertionOrder = new LinkedList<>();
    }

    /**
     * 通过复制现有设备集合创建设备集合（拷贝构造）。
     *
     * @param modelDevices 要复制的源设备集合
     */
    public ModelDevices(ModelDevices modelDevices) {
        this.devicesById = CollectionFactory.createConcurrentHashMap();
        this.deviceIdsByInsertionOrder = new LinkedList<>();
        this.devicesById.putAll(modelDevices.devicesById);
        this.deviceIdsByInsertionOrder = new LinkedList<>(modelDevices.getDeviceIdsByInsertionOrder());
    }

    /**
     * 使用已有的设备 ID 映射创建设备集合。
     *
     * @param devicesById 设备 ID 到设备的映射
     */
    public ModelDevices(Map<String, ModelDevice> devicesById) {
        this.devicesById = CollectionFactory.createConcurrentHashMap();
        this.deviceIdsByInsertionOrder = new LinkedList<>();
        Validate.notNull(devicesById, "The devicesById is null");
        Validate.noNullElements(devicesById.values(), "The devicesById contains null value");
        this.devicesById.putAll(devicesById);
    }

    /**
     * 使用设备集合创建设备集合。
     *
     * @param devices 设备集合
     */
    public ModelDevices(Collection<ModelDevice> devices) {
        this.devicesById = CollectionFactory.createConcurrentHashMap();
        this.deviceIdsByInsertionOrder = new LinkedList<>();
        Validate.notNull(devices, "The devices is null");
        Validate.noNullElements(devices, "The devices contains null value");

        for (ModelDevice device : devices) {
            this.devicesById.put(device.getID(), device);
        }

    }

    /**
     * 使用设备数组创建设备集合。
     *
     * @param devices 设备数组
     */
    public ModelDevices(ModelDevice[] devices) {
        this(Arrays.asList(devices));
    }

    /**
     * 获取集合中的设备数量。
     *
     * @return 设备数量
     */

    public int size() {
        return this.devicesById.size();
    }

    /**
     * 判断集合中是否包含指定设备。
     *
     * @param device 要检查的设备
     * @return 如果包含则返回 true
     */
    public boolean contains(ModelDevice device) {
        return this.devicesById.containsValue(device);
    }

    /**
     * 判断集合中是否包含指定 ID 的设备。
     *
     * @param deviceId 设备 ID
     * @return 如果包含则返回 true
     */

    public boolean containsId(String deviceId) {
        return this.devicesById.containsKey(deviceId);
    }

    /**
     * 获取集合中所有设备的集合。
     *
     * @return 设备集合
     */
    public Set<ModelDevice> getDevices() {
        return new HashSet<>(this.devicesById.values());
    }

    /**
     * 获取设备 ID 到设备对象的不可修改映射。
     *
     * @return 设备映射视图
     */

    public Map<String, ModelDevice> getDevicesById() {
        return Collections.unmodifiableMap(this.devicesById);
    }

    @Override
/**
 * 返回遍历所有设备的迭代器。
 * @return 设备迭代器
 */

    public Iterator<ModelDevice> iterator() {
        return this.getDevicesById().values().iterator();
    }

    /**
     * 根据设备 ID 获取设备。
     *
     * @param deviceId 设备 ID
     * @return 设备对象，如果不存在则返回 null
     */
    public ModelDevice getById(String deviceId) {
        return this.devicesById.get(deviceId);
    }

    /**
     * 向集合中添加一个设备。如果设备 ID 已存在则覆盖。
     *
     * @param device 要添加的设备
     */

    public void add(ModelDevice device) {
        this.devicesById.put(device.getID(), device);
        // 首次添加时记录插入顺序
        if (this.devicesById.size() > this.deviceIdsByInsertionOrder.size()) {
            this.deviceIdsByInsertionOrder.add(device.getID());
        }

    }

    /**
     * 获取按插入顺序排列的设备 ID 列表。
     *
     * @return 设备 ID 列表
     */

    public List<String> getDeviceIdsByInsertionOrder() {
        return this.deviceIdsByInsertionOrder;
    }

    /**
     * 批量添加设备到集合中。
     *
     * @param devices 设备集合
     */
    public void addAll(Collection<ModelDevice> devices) {
        Validate.notNull(devices, "The devices is null");
        Validate.noNullElements(devices, "The devices contains null value");

        for (ModelDevice device : devices) {
            this.devicesById.put(device.getID(), device);
        }

    }

    /**
     * 将另一个设备集合中的所有设备添加到当前集合。
     *
     * @param devices 另一个设备集合
     */

    public void addAll(ModelDevices devices) {
        this.devicesById.putAll(devices.devicesById);
    }

    /**
     * 从集合中移除指定设备。
     *
     * @param device 要移除的设备
     */
    public void remove(ModelDevice device) {
        this.devicesById.remove(device.getID());
    }

    /**
     * 批量移除设备。
     *
     * @param devices 要移除的设备集合
     */

    public void removeAll(Collection<ModelDevice> devices) {
        for (ModelDevice device : devices) {
            this.devicesById.remove(device.getID());
        }

    }

    /**
     * 批量移除另一个设备集合中的所有设备。
     *
     * @param devices 另一个设备集合
     */

    public void removeAll(ModelDevices devices) {
        for (ModelDevice device : devices) {
            this.devicesById.remove(device.getID());
        }

    }

    /**
     * 清空集合中的所有设备。
     */

    public void clear() {
        this.devicesById.clear();
    }

    @Override
/**
 * 计算哈希码。
 * @return 哈希码
 */

    public int hashCode() {
        HashCodeBuilder hashCodeBuilder;
        hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(this.getClass()).append(this.devicesById);
        return hashCodeBuilder.toHashCode();
    }

    @Override
/**
 * 判断两个设备集合是否相等（基于设备映射的比较）。
 * @param obj 要比较的对象
 * @return 如果设备映射相同则返回 true
 */

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof ModelDevices)) {
            return false;
        } else {
            ModelDevices other = (ModelDevices) object;
            return (new EqualsBuilder()).append(this.devicesById, other.devicesById).isEquals();
        }
    }
}
