package com.scientiamobile.wurfl.core.updater;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxySettings {
   private String a;
   private Integer b;
   private Proxy.Type c;

   public ProxySettings(String var1, Integer var2, Proxy.Type var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
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
      return new Proxy(this.c, new InetSocketAddress(this.a, this.b));
   }
}
