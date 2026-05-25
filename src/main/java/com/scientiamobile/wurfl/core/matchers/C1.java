package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.slf4j.LoggerFactory;

final class C implements F {
   private Matcher a;
   private G b;

   public C(Matcher var1) {
      LoggerFactory.getLogger(this.getClass());
      this.a = var1;
      this.b = new G(this);
   }

   public final boolean canHandle(WURFLRequest var1) {
      return this.a.canHandle(var1);
   }

   public final boolean a(WURFLRequest var1, String var2) {
      this.b.a(this.a.normalize(var1.getCleanedDeviceUserAgent()), var2);
      return true;
   }

   public final G a() {
      return this.b;
   }

   public final String getMatcherName() {
      return this.a.getMatcherName();
   }
}
