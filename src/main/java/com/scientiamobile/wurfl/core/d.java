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
   // $FF: synthetic field
   private static boolean d = !d.class.desiredAssertionStatus();

   public d(b var1, int var2) {
      LoggerFactory.getLogger(d.class);
      this.a = 39;
      this.b = var1;
      if (var2 > this.a) {
         this.a = var2;
      }

      this.c = new HashMap(50);
   }

   public final String a(String var1) {
      String var2;
      if ((var2 = this.b.a(this.c, var1)) == null) {
         throw new CapabilityNotDefinedException(var1);
      } else {
         return var2;
      }
   }

   public final Map a() {
      if (this.c == null || this.c.size() < this.a) {
         if (this.b == null) {
            throw new IllegalStateException("The device must be initialized before serialization");
         }

         this.c = this.b.a();
      }

      if (!d && this.c == null) {
         throw new AssertionError();
      } else {
         return this.c;
      }
   }

   private void writeObject(ObjectOutputStream var1) {
      if (this.c == null) {
         this.c = this.b.a();
      }

      var1.defaultWriteObject();
   }
}
