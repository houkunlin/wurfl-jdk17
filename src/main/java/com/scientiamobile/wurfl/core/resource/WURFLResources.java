package com.scientiamobile.wurfl.core.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class WURFLResources {
  private final List a = new ArrayList();
  
  public WURFLResources() {}
  
  public WURFLResources(WURFLResource... paramVarArgs) {
    Validate.notNull(paramVarArgs, "The resources is null");
    this.a.addAll(Arrays.asList(paramVarArgs));
  }
  
  public WURFLResources(Collection paramCollection) {
    Validate.notNull(paramCollection, "The resources is null");
    Validate.noNullElements(paramCollection, "The resources contains null value");
    Validate.allElementsOfType(paramCollection, WURFLResource.class, "The resources contains value not instaceof WURFLResource");
    this.a.addAll(paramCollection);
  }
  
  public final int size() {
    return this.a.size();
  }
  
  public final WURFLResource get(int paramInt) {
    return this.a.get(paramInt);
  }
  
  public final int indexOf(WURFLResource paramWURFLResource) {
    Validate.notNull(paramWURFLResource, "The resource is null");
    return this.a.indexOf(paramWURFLResource);
  }
  
  public final void release() {
    Iterator<WURFLResource> iterator = this.a.iterator();
    while (iterator.hasNext())
      ((WURFLResource)iterator.next()).release(); 
  }
  
  public final void add(WURFLResource paramWURFLResource) {
    Validate.notNull(paramWURFLResource, "The resource must be not null");
    this.a.add(paramWURFLResource);
  }
  
  public final void remove(WURFLResource paramWURFLResource) {
    Validate.notNull(paramWURFLResource, "The resource must be not null");
    this.a.remove(paramWURFLResource);
  }
  
  public final Iterator iterator() {
    return this.a.iterator();
  }
  
  public final String toString() {
    StringBuilder stringBuilder = new StringBuilder("[");
    for (byte b = 0; b < this.a.size(); b++) {
      WURFLResource wURFLResource = this.a.get(b);
      stringBuilder.append(wURFLResource).append("(").append(wURFLResource.getInfo()).append(" version: ").append(wURFLResource.getVersion()).append(")");
      if (b < this.a.size() - 1)
        stringBuilder.append(" - "); 
    } 
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
  
  public final boolean equals(Object paramObject) {
    EqualsBuilder equalsBuilder;
    (equalsBuilder = new EqualsBuilder()).appendSuper(getClass().isInstance(paramObject));
    if (equalsBuilder.isEquals()) {
      paramObject = paramObject;
      equalsBuilder.append(this.a, ((WURFLResources)paramObject).a);
    } 
    return equalsBuilder.isEquals();
  }
  
  public final int hashCode() {
    HashCodeBuilder hashCodeBuilder;
    (hashCodeBuilder = new HashCodeBuilder(53, 79)).append(getClass()).append(this.a);
    return hashCodeBuilder.toHashCode();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\WURFLResources.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
