package com.scientiamobile.wurfl.core.updater;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Implementation of Proxy Settings.
 */

public class ProxySettings {
    private String host;
    private Integer port;
    private Proxy.Type type;

    public ProxySettings(String host, Integer port, Proxy.Type type) {
        this.host = host;
        this.port = port;
        this.type = type;
    }

    /**
     * Returns the host.
     */

    public String getHost() {
        return this.host;
    }

    public Integer getPort() {
        return this.port;
    }

    public Proxy.Type getType() {
        return this.type;
    }

    /**
     * Returns the proxy.
 */

    public Proxy getProxy() {
        return new Proxy(this.type, new InetSocketAddress(this.host, this.port));
    }
}
