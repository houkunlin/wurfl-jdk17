package com.scientiamobile.wurfl.core.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CollectionFactory {
  public static Map createConcurrentHashMap() {
    return createConcurrentHashMap(6000, 0.75F, 16);
  }
  
  public static Map createConcurrentHashMap(int paramInt1, float paramFloat, int paramInt2) {
    return new ConcurrentHashMap<Object, Object>(paramInt1, paramFloat, paramInt2);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\cor\\utils\CollectionFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */