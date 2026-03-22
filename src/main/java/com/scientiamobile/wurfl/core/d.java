package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.LoggerFactory;

class d extends a implements Serializable {
  private static final long serialVersionUID = 100L;

  private int a;

  private transient b b;

  private Map c;

  public d(b paramb, int paramInt) {
    LoggerFactory.getLogger(d.class);
    this.a = 39;
    this.b = paramb;
    if (paramInt > this.a)
      this.a = paramInt;
    this.c = new HashMap<Object, Object>(50);
  }

  public final String a(String paramString) {
    String str;
    if ((str = this.b.a(this.c, paramString)) == null)
      throw new CapabilityNotDefinedException(paramString);
    return str;
  }

  public final Map a() {
    if (this.c == null || this.c.size() < this.a) {
      if (this.b == null)
        throw new IllegalStateException("The device must be initialized before serialization");
      this.c = this.b.a();
    }
    if (this.c == null)
      throw new AssertionError();
    return this.c;
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream) {
    if (this.c == null)
      this.c = this.b.a();
    paramObjectOutputStream.defaultWriteObject();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\d.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
