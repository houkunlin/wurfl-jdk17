package com.scientiamobile.wurfl.core.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CollectionFactory {
   public static <K, V> Map<K, V> createConcurrentHashMap() {
      return createConcurrentHashMap(6000, 0.75F, 16);
   }

   public static <K, V> Map<K, V> createConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
      return new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
   }
}
