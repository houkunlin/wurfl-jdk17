package com.scientiamobile.wurfl.core.updater;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * WURFL 更新器使用的代理设置。
 * <p>封装 HTTP 代理的主机、端口和代理类型，用于 WURFL 更新器通过代理服务器
 * 访问 Scieniamobile 远程服务器。</p>
 */

public class ProxySettings {
    /**
     * 代理服务器主机名或 IP 地址
     */
    private String host;
    /**
     * 代理服务器端口号
     */
    private Integer port;
    /** 代理类型（如 HTTP、SOCKS） */
    private Proxy.Type type;

    public ProxySettings(String host, Integer port, Proxy.Type type) {
        this.host = host;
        this.port = port;
        this.type = type;
    }

    /**
     * 获取代理服务器主机地址。
     *
     * @return 主机名或 IP 地址
     */
    public String getHost() {
        return this.host;
    }

    /**
     * 获取代理服务器端口号。
     *
     * @return 端口号
     */
    public Integer getPort() {
        return this.port;
    }

    /**
     * 获取代理类型。
     *
     * @return 代理类型（{@link Proxy.Type}）
     */
    public Proxy.Type getType() {
        return this.type;
    }

    /**
     * 构造并返回 {@link Proxy} 实例。
     * <p>基于当前设置的主机、端口和类型创建 Java 网络代理对象，
     * 用于 {@link java.net.URLConnection} 的连接配置。</p>
     *
     * @return 配置好的 Proxy 实例
     */
    public Proxy getProxy() {
        return new Proxy(this.type, new InetSocketAddress(this.host, this.port));
    }
}
