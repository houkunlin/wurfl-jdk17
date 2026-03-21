package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import com.scientiamobile.wurfl.core.utils.CollectionFactory;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashMapCacheProvider implements CacheProvider {
  private Map a;
  
  private int b = 6000;
  
  private float c = 0.75F;
  
  private int d = 16;
  
  private final transient Logger e = LoggerFactory.getLogger(getClass());
  
  public HashMapCacheProvider() {
    this(60000);
  }
  
  public HashMapCacheProvider(int paramInt) {
    this(paramInt, 0.75F);
  }
  
  public HashMapCacheProvider(int paramInt, float paramFloat) {
    this(paramInt, paramFloat, 16);
  }
  
  public HashMapCacheProvider(int paramInt1, float paramFloat, int paramInt2) {
    this.b = paramInt1;
    this.c = paramFloat;
    this.d = paramInt2;
    this.a = CollectionFactory.createConcurrentHashMap(paramInt1, paramFloat, paramInt2);
    if (this.e.isInfoEnabled()) {
      StringBuffer stringBuffer;
      (stringBuffer = new StringBuffer("Created HashMapCacheProvider with initial capacity: ")).append(paramInt1);
      stringBuffer.append(" load factor: ");
      stringBuffer.append(paramFloat);
      stringBuffer.append(" concurrent writes: ");
      stringBuffer.append(paramInt2);
      this.e.info(stringBuffer.toString());
    } 
  }
  
  public int getInitialCapacity() {
    return this.b;
  }
  
  public float getLoadFactor() {
    return this.c;
  }
  
  public int getConcurrentWrites() {
    return this.d;
  }
  
  public void clear() {
    this.e.info("Cache size: " + this.a.size());
    this.a.clear();
    this.e.info("Cache erased");
  }
  
  public InternalDevice getDevice(String paramString) {
    Validate.notNull(paramString, "The key is null");
    return (InternalDevice)this.a.get(paramString);
  }
  
  public void putDevice(String paramString, InternalDevice paramInternalDevice) {
    Validate.notNull(paramString, "The key is null");
    this.a.put(paramString, paramInternalDevice);
  }
  
  public String toString() {
    return (new ToStringBuilder(this)).append("initialCapacity", this.b).append("loadFactor", this.c).append("concurrentWrites", this.d).toString();
  }
  
  public InternalDevice getInternalDeviceFromDeviceId(String paramString) {
    return null;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\cache\HashMapCacheProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
