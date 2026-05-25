package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class S implements A, F {
   private List a = new LinkedList();
   private final Logger b = LoggerFactory.getLogger(S.class);
   private List c = new LinkedList();

   public final void a(A var1) {
      this.a.add(var1);
      this.c.add(var1.getFilter());
   }

   public DeviceInfo match(WURFLRequest var1) {
      Iterator var2 = this.a.iterator();

      while(var2.hasNext()) {
         A var3;
         (var3 = (A)var2.next()).getMatcherName();
         if (var3.canHandle(var1)) {
            return var3.match(var1);
         }
      }

      if (this.b.isWarnEnabled()) {
         this.b.warn("No any matcher can handle the request: " + var1 + ", returning generic device.");
      }

      return new DeviceInfo("generic", MatchType.none, this.getMatcherName(), "MatcherChain", var1.getOriginalUserAgent(), "");
   }

   public boolean canHandle(WURFLRequest var1) {
      return true;
   }

   public String normalize(String var1) {
      return var1;
   }

   public F getFilter() {
      return this;
   }

   public String getMatcherName() {
      return "MatcherChain";
   }

   public final boolean a(WURFLRequest var1, String var2) {
      Iterator var3 = this.c.iterator();

      while(var3.hasNext()) {
         F var4;
         if ((var4 = (F)var3.next()).canHandle(var1)) {
            var4.a(var1, var2);
            return true;
         }
      }

      return false;
   }

   public final G a() {
      this.b.warn("A Filter of type MatcherChain should never be asked for its FilteredDevices set.");
      G var1 = new G(this);
      Iterator var2 = this.c.iterator();

      while(var2.hasNext()) {
         F var3;
         for(String var5 : (var3 = (F)var2.next()).a().a()) {
            var1.a(var5, var3.a().a(var5));
         }
      }

      return var1;
   }

   public final void b() {
      Iterator var1 = this.c.iterator();

      while(var1.hasNext()) {
         F var2;
         if ((var2 = (F)var1.next()) instanceof S) {
            ((S)var2).b();
         } else {
            var2.a().b();
         }
      }

   }
}
