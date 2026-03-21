package com.scientiamobile.wurfl.core.resource;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.text.StrBuilder;

final class c implements Serializable, Comparable {
  private static final long serialVersionUID = 1L;
  
  private String a;
  
  private String b;
  
  private boolean c;
  
  private ModelDevices d;
  
  private transient String e;
  
  private String f;
  
  public c(String paramString1, String paramString2, boolean paramBoolean, ModelDevices paramModelDevices, String paramString3) {
    this.a = paramString1;
    this.b = paramString2;
    this.c = paramBoolean;
    this.d = paramModelDevices;
    this.f = paramString3;
  }
  
  public final String a() {
    if (this.e == null) {
      StrBuilder strBuilder;
      (strBuilder = new StrBuilder()).append(this.c ? "Patch" : "Root").append(":").append(this.a);
      if (StringUtils.isNotBlank(this.b))
        strBuilder.append(":").append(this.b); 
      this.e = strBuilder.toString();
    } 
    return this.e;
  }
  
  public final ModelDevices b() {
    return new ModelDevices(this.d);
  }
  
  public final int hashCode() {
    HashCodeBuilder hashCodeBuilder;
    (hashCodeBuilder = new HashCodeBuilder(33, 55)).append(this.a).append(this.b);
    return hashCodeBuilder.toHashCode();
  }
  
  public final boolean equals(Object paramObject) {
    EqualsBuilder equalsBuilder;
    (equalsBuilder = new EqualsBuilder()).appendSuper(getClass().isInstance(paramObject));
    if (equalsBuilder.isEquals()) {
      paramObject = paramObject;
      equalsBuilder.append(a(), paramObject.a());
    } 
    return equalsBuilder.isEquals();
  }
  
  public final String toString() {
    ToStringBuilder toStringBuilder;
    (toStringBuilder = new ToStringBuilder(this)).append(this.a).append(this.b);
    return toStringBuilder.toString();
  }
  
  final String c() {
    return this.f;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\c.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
