package com.scientiamobile.wurfl.core.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

final class b {
  private b a;
  
  private boolean b;
  
  private TreeMap c;
  
  public b() {
    this(false);
  }
  
  private b(boolean paramBoolean) {
    this.b = paramBoolean;
    this.a = null;
    this.c = new TreeMap<Object, Object>();
  }
  
  public final void a(String paramString) {
    while (true) {
      b b1;
      while ((b1 = (b)this.c.get(Character.valueOf(paramString.charAt(0)))) == null) {
        if (paramString.length() == 1) {
          b1 = new b(true);
          this.c.put(Character.valueOf(paramString.charAt(0)), b1);
          return;
        } 
        b1 = new b(false);
        this.c.put(Character.valueOf(paramString.charAt(0)), b1);
        paramString = paramString.substring(1);
        this = b1;
      } 
      if (paramString.length() > 1) {
        paramString = paramString.substring(1);
        this = b1;
        continue;
      } 
      break;
    } 
  }
  
  public final TreeMap a() {
    return this.c;
  }
  
  public final boolean b() {
    return this.b;
  }
  
  public final int c() {
    return this.c.size();
  }
  
  public final b a(int paramInt) {
    Iterator<Map.Entry> iterator = this.c.entrySet().iterator();
    int i = 0;
    while (iterator.hasNext()) {
      if (i == paramInt)
        return (b)((Map.Entry)iterator.next()).getValue(); 
      i++;
      iterator.next();
    } 
    return null;
  }
  
  public final b a(char paramChar) {
    return (b)this.c.get(Character.valueOf(paramChar));
  }
  
  public final Set d() {
    return this.c.keySet();
  }
  
  public final void a(b paramb) {
    this.a = paramb;
  }
  
  public final b e() {
    return this.a;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\utils\b.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */