package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

class h implements InternalDevice, Serializable {
  private static final long serialVersionUID = 101L;
  
  private final String a;
  
  private final String b;
  
  private final boolean c;
  
  private final String d;
  
  private ModelDevice e;
  
  private final a f;
  
  protected h(ModelDevice paramModelDevice, String paramString, a parama) {
    this(paramModelDevice.getID(), paramModelDevice.getUserAgent(), paramModelDevice.isActualDeviceRoot(), paramString, parama);
    this.e = paramModelDevice.getAncestor();
  }
  
  private h(String paramString1, String paramString2, boolean paramBoolean, String paramString3, a parama) {
    Validate.notNull(parama, "The capabilitiesHolder must be not null");
    this.a = paramString1;
    this.b = paramString2;
    this.c = paramBoolean;
    this.d = paramString3;
    this.f = parama;
  }
  
  public String getId() {
    return this.a;
  }
  
  public String getWURFLUserAgent() {
    return this.b;
  }
  
  public String getCapability(String paramString) {
    return this.f.a(paramString);
  }
  
  public final ModelDevice a() {
    return this.e;
  }
  
  public int getCapabilityAsInt(String paramString) {
    return this.f.b(paramString);
  }
  
  public boolean getCapabilityAsBool(String paramString) {
    String str;
    if ((paramString = this.f.a(str = paramString)) != null && paramString.toLowerCase().equals("true"))
      return true; 
    if (paramString != null && paramString.toLowerCase().equals("false"))
      return false; 
    throw new NumberFormatException("WURFL invalid capability value: " + str + " expected \"true\" or \"false\", received: \"" + paramString + "\"");
  }
  
  public Map getCapabilities() {
    Map map = this.f.a();
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>(map.size());
    Iterator<String> iterator = map.keySet().iterator();
    while (iterator.hasNext()) {
      String str;
      if (!(str = iterator.next()).startsWith("controlcap_"))
        hashMap.put(str, map.get(str)); 
    } 
    return hashMap;
  }
  
  public boolean isActualDeviceRoot() {
    return this.c;
  }
  
  public String getDeviceRootId() {
    String str = this.d;
    if (this.d.equals("generic"))
      str = ""; 
    return str;
  }
  
  public boolean equals(Object paramObject) {
    EqualsBuilder equalsBuilder;
    (equalsBuilder = new EqualsBuilder()).appendSuper(getClass().isInstance(paramObject));
    if (equalsBuilder.isEquals()) {
      paramObject = paramObject;
      equalsBuilder.append(this.a, ((h)paramObject).a);
    } 
    return equalsBuilder.isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder(63, 89)).append(getClass()).append(this.a).toHashCode();
  }
  
  public String toString() {
    return "[" + this.a + ", match=, matcher=]";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\h.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
