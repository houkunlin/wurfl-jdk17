package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.cache.CacheProvider;
import com.scientiamobile.wurfl.core.cache.DoubleLRUMapCacheProvider;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.matchers.MatcherManager;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequestFactoryWithPriority;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.resource.WURFLResource;
import com.scientiamobile.wurfl.core.resource.WURFLResources;
import com.scientiamobile.wurfl.core.resource.XmlFileLoader;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class j implements m {
   private final Logger a;
   private WURFLModel b;
   private ReentrantReadWriteLock c;
   private CacheProvider d;
   private MatcherManager e;
   private DeviceProvider f;
   private WURFLRequestFactoryWithPriority g;
   private EngineTarget h;
   private final XmlFileLoader i;
   private boolean j;
   // $FF: synthetic field
   private static boolean k = !j.class.desiredAssertionStatus();

   public j(WURFLModel var1, MatcherManager var2, DeviceProvider var3, WURFLRequestFactoryWithPriority var4, EngineTarget var5) {
      this.a = LoggerFactory.getLogger(this.getClass());
      this.i = new XmlFileLoader("classpath:/META-INF/wurfl-config.xml", new k(this, (byte)0));
      this.j = false;
      this.b = var1;
      this.e = var2;
      this.f = var3;
      this.g = var4;
      if (var5 == null) {
         try {
            if (!this.j) {
               this.i.parseFile();
               this.j = true;
            }
         } catch (IOException var6) {
            this.a.error(var6.getMessage(), var6);
            throw new WURFLRuntimeException(var6);
         }
      } else {
         this.h = var5;
      }

      this.c = new ReentrantReadWriteLock();
      this.a.info(this.getClass().getSimpleName() + " created");
   }

   public j(WURFLModel var1, MatcherManager var2, DeviceProvider var3, WURFLRequestFactoryWithPriority var4) {
      this(var1, var2, var3, var4, (EngineTarget)null);
   }

   public final void a(CacheProvider var1) {
      this.a.info("feeding " + var1);
      this.d = var1;
   }

   public final Device a(WURFLRequest var1) {
      this.c.readLock().lock();

      try {
         Validate.notNull(var1, "The request is null");
         this.c();
         InternalDevice var2;
         DeviceInfo var3;
         if ((var2 = this.d.getDevice(var1.getOriginalUserAgent())) != null) {
            var3 = new DeviceInfo(var2.getId(), MatchType.cached, "Cache", "Cache", var1.getOriginalUserAgent(), "");
         } else {
            var1.performGenericNormalization();
            if (EngineTarget.fastDesktopBrowserMatch.equals(var1.getEngineTarget()) && var1._internalIsDesktopBrowserHeavyDutyAnalysis()) {
               var2 = this.f.getInternalDevice("generic_web_browser");
               this.d.putDevice(var1.getOriginalUserAgent(), var2);
               Device var7 = this.f.buildDevice(var2, var1, MatchType.fastDesktopBrowser, "", "");
               return var7;
            }

            var3 = this.e.matchRequest(var1);
            if ((var2 = this.d.getInternalDeviceFromDeviceId(var3.getId())) == null) {
               var2 = this.f.getInternalDevice(var3.getId());
            }

            this.d.putDevice(var1.getOriginalUserAgent(), var2);
         }

         if (!k && var2 == null) {
            throw new AssertionError();
         } else {
            Device var6 = this.f.buildDevice(var2, var1, var3.getMatchType(), var3.a(), var3.b());
            return var6;
         }
      } finally {
         this.c.readLock().unlock();
      }
   }

   public final Device a(HttpServletRequest var1) {
      Validate.notNull(var1, "The request must be not null");
      WURFLRequest var2 = this.g.createRequest(var1, this.h);
      return this.a(var2);
   }

   public final Device a(String var1) {
      Validate.notNull(var1, "The userAgent must be not null");
      WURFLRequest var2 = this.g.createRequest(var1, this.h);
      return this.a(var2);
   }

   private void c() {
      if (this.d == null) {
         synchronized(this) {
            if (this.d == null) {
               this.a.info("no Cache Provider, using default (DoubleLRUMapCacheProvider)");
               this.d = new DoubleLRUMapCacheProvider();
            }

         }
      }
   }

   public final EngineTarget a() {
      return this.h;
   }

   public final void a(EngineTarget var1) {
      if (var1 != EngineTarget.fastDesktopBrowserMatch) {
         this.h = EngineTarget.defaultTarget;
      } else {
         this.h = var1;
      }
   }

   public final UserAgentPriority b() {
      return this.g.getUserAgentPriority();
   }

   public final void a(UserAgentPriority var1) {
      this.g.setUserAgentPriority(var1);
   }

   public final Device b(String var1) {
      InternalDevice var2 = this.f.getInternalDevice(var1);
      WURFLRequestFactoryWithPriority var10000 = this.g;
      String var3;
      String var10001;
      if (!(var3 = var2.getWURFLUserAgent()).startsWith("DO_NOT_MATCH")) {
         var10001 = var3;
      } else {
         for(var4 = ((h)var2).a(); var4 != null && var4.getUserAgent().contains("DO_NOT_MATCH"); var4 = var4.getAncestor()) {
         }

         var10001 = var4 != null && var4.getUserAgent() != null ? var4.getUserAgent() : "";
      }

      WURFLRequest var5 = var10000.createRequest(var10001, this.h);
      return this.a(var1, var5);
   }

   public final Device a(String var1, HttpServletRequest var2) {
      Validate.notNull(var2, "The request must be not null");
      WURFLRequest var3 = this.g.createRequest(var2, this.h);
      return this.a(var1, var3);
   }

   public final Device a(String var1, WURFLRequest var2) {
      Validate.notNull(var2, "The request must be not null");
      var2.performGenericNormalization();
      return this.f.buildDevice(this.f.getInternalDevice(var1), var2, MatchType.none, "Utils", "Utils");
   }

   public final void a(WURFLResource var1, WURFLResources var2, String... var3) {
      this.c.writeLock().lock();
      this.a.info("reloading service");

      try {
         this.b.reload(var1, var2, var3);
         this.e.reloadModel(this.b);
         this.d();
      } finally {
         this.c.writeLock().unlock();
      }

   }

   private void d() {
      this.a.info("about to clear cache provider");
      this.c();
      this.d.clear();
   }

   public final void a(WURFLResources var1, String... var2) {
      this.a.info("before applying patches " + var1);
      this.c.writeLock().lock();

      try {
         this.b.applyPatches(var1, var2);
         this.e.reloadModel(this.b);
         this.d();
         this.a.info("finished applying patches " + var1);
      } finally {
         this.c.writeLock().unlock();
      }

   }

   public final void a(WURFLRequestFactoryWithPriority var1) {
      this.g = var1;
   }

   // $FF: synthetic method
   static EngineTarget a(j var0) {
      return var0.h;
   }

   // $FF: synthetic method
   static EngineTarget a(j var0, EngineTarget var1) {
      return var0.h = var1;
   }
}
