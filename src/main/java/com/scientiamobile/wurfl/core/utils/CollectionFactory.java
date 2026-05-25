package com.scientiamobile.wurfl.core.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CollectionFactory {
   public static Map createConcurrentHashMap() {
      return createConcurrentHashMap(6000, 0.75F, 16);
   }

   public static Map createConcurrentHashMap(int var0, float var1, int var2) {
      return new ConcurrentHashMap(var0, var1, var2);
   }
}
