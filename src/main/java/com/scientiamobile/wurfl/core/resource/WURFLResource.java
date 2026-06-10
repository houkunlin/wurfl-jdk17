package com.scientiamobile.wurfl.core.resource;

/**
 * WURFL 资源接口。
 * <p>定义访问 WURFL 数据源的标准契约。所有 WURFL 数据源
 * （如 XML 文件、输入流等）都必须实现该接口，以提供统一的
 * 数据获取、版本查询和资源释放能力。</p>
 */

public interface WURFLResource {
    /**
     * 获取 WURFL 数据快照。
     *
     * @param params 可选的功能点过滤列表，仅加载指定的功能点
     * @return 包含设备集合、版本信息等数据的快照对象
     */
    ModelDevicesSnapshot getData(String... params);

    /**
     * 获取资源的描述信息（如文件名或 URI 等）。
     *
     * @return 资源的描述字符串
     */
    String getInfo();

    /**
     * 获取 WURFL 数据的版本号。
     *
     * @return 版本号字符串
     */
    String getVersion();

    /**
     * 释放资源持有的底层系统资源（如文件句柄、输入流等）。
     */
    void release();

    /**
     * 获取资源的原始路径。
     *
     * @return 原始路径字符串
     */
    String getOriginalPath();
}
