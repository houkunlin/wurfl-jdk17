package com.scientiamobile.wurfl.core.updater;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxySettings {
  private String a;
  
  private Integer b;
  
  private Proxy.Type c;
  
  public ProxySettings(String paramString, Integer paramInteger, Proxy.Type paramType) {
    this.a = paramString;
    this.b = paramInteger;
    this.c = paramType;
  }
  
  public String getHost() {
    return this.a;
  }
  
  public Integer getPort() {
    return this.b;
  }
  
  public Proxy.Type type() {
    return this.c;
  }
  
  public Proxy getProxy() {
    return new Proxy(this.c, new InetSocketAddress(this.a, this.b.intValue()));
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\updater\ProxySettings.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */