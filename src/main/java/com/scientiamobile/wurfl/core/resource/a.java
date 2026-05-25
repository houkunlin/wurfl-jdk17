package com.scientiamobile.wurfl.core.resource;

import java.io.Serializable;
import java.util.Comparator;

final class a implements Serializable, Comparator {
   private static final long serialVersionUID = 101L;
   public static final a a = new a();

   private a() {
   }

   // $FF: synthetic method
   public final int compare(Object var1, Object var2) {
      ModelDevice var10000 = (ModelDevice)var1;
      var2 = var2;
      return var10000.getUserAgent().compareTo(var2.getUserAgent());
   }
}
