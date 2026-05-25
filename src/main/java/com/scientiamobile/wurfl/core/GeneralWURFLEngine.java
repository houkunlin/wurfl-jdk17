package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.cache.CacheProvider;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.matchers.MatcherManager;
import com.scientiamobile.wurfl.core.request.DefaultWURFLRequestFactory;
import com.scientiamobile.wurfl.core.request.UserAgentResolver;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequestFactory;
import com.scientiamobile.wurfl.core.request.WURFLRequestFactoryWithPriority;
import com.scientiamobile.wurfl.core.resource.DefaultWURFLModel;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.resource.WURFLResource;
import com.scientiamobile.wurfl.core.resource.WURFLResources;
import com.scientiamobile.wurfl.core.resource.XMLResource;
import com.scientiamobile.wurfl.core.vcap.VirtualCapabilityEvaluator;
import com.scientiamobile.wurfl.core.vcap.VirtualCapabilityHandler;
import com.scientiamobile.wurfl.core.web.WurflWebConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralWURFLEngine implements WURFLEngine, WurflWebConstants {
   private final transient Logger a;
   private static final List b = Arrays.asList("device_os", "device_os_version", "is_tablet", "is_wireless_device", "pointing_method", "preferred_markup", "resolution_height", "resolution_width", "ux_full_desktop", "xhtml_support_level", "is_smarttv", "can_assign_phone_number", "brand_name", "model_name", "marketing_name", "mobile_browser_version");
   private String[] c;
   private final ReadWriteLock d;
   private WURFLResource e;
   private WURFLResources f;
   private String g;
   private MarkupResolver h;
   private CapabilitiesHolderFactory i;
   private DeviceProvider j;
   private CacheProvider k;
   private WURFLService l;
   private UserAgentResolver m;
   private WURFLUtils n;
   private WURFLModel o;
   private Boolean p;
   private WURFLRequestFactoryWithPriority q;
   private EngineTarget r;
   private UserAgentPriority s;

   public GeneralWURFLEngine(String var1) {
      this((WURFLResource)(new XMLResource(var1)));
      this.g = var1;
   }

   public GeneralWURFLEngine(WURFLResource var1) {
      this((WURFLResource)var1, (WURFLResources)null);
   }

   public GeneralWURFLEngine(String var1, String... var2) {
      this.a = LoggerFactory.getLogger(this.getClass());
      this.c = null;
      this.d = new ReentrantReadWriteLock();
      this.e = null;
      this.f = null;
      this.g = null;
      this.p = false;
      this.q = null;
      this.r = null;
      this.s = UserAgentPriority.OverrideSideloadedBrowserUserAgent;
      WURFLResource var3 = a(var1);
      WURFLResources var4 = a(var2);
      Validate.notNull(var3, "The root resource is null");
      this.e = var3;
      this.g = var1;
      this.f = var4;
   }

   public GeneralWURFLEngine(WURFLResource var1, WURFLResource... var2) {
      this(var1, new WURFLResources(var2));
   }

   public GeneralWURFLEngine(WURFLResource var1, WURFLResources var2) {
      this.a = LoggerFactory.getLogger(this.getClass());
      this.c = null;
      this.d = new ReentrantReadWriteLock();
      this.e = null;
      this.f = null;
      this.g = null;
      this.p = false;
      this.q = null;
      this.r = null;
      this.s = UserAgentPriority.OverrideSideloadedBrowserUserAgent;
      Validate.notNull(var1, "The root resource is null");
      this.e = var1;
      if (var1 != null) {
         this.g = var1.getOriginalPath();
      }

      this.f = var2;
   }

   public void reload(String var1) {
      WURFLResource var2 = a(var1);
      this.reload((WURFLResource)var2, (WURFLResources)null);
   }

   public void applyPatches(String... var1) {
      if (var1 == null) {
         this.a.warn("null patches, do nothing...");
      } else {
         this.applyPatches(a(var1));
      }
   }

   public void applyPatches(WURFLResource... var1) {
      this.applyPatches(new WURFLResources(var1));
   }

   public void applyPatches(WURFLResources var1) {
      if (var1 == null) {
         this.a.warn("null patches, do nothing...");
      } else {
         this.d.writeLock().lock();

         try {
            this.a();
            this.l.applyPatches(var1, this.c);
         } finally {
            this.d.writeLock().unlock();
         }

      }
   }

   public void reload(String var1, String[] var2) {
      WURFLResource var3 = a(var1);
      WURFLResources var4 = a(var2);
      this.reload(var3, var4);
   }

   public void reload(WURFLResource var1, WURFLResource... var2) {
      this.reload(var1, new WURFLResources(var2));
   }

   public void reload(WURFLResource var1, WURFLResources var2) {
      this.g = var1.getOriginalPath();
      this.a();
      if ((var2 = var2) == null) {
         var2 = new WURFLResources();
      }

      this.d.writeLock().lock();

      try {
         this.l.reload(var1, var2, this.c);
      } finally {
         this.d.writeLock().unlock();
      }

   }

   public boolean replaceRoot(String var1) {
      try {
         if (StringUtils.isBlank(var1)) {
            this.a.warn("Empty value has been provided for replacing root, skipping");
            return false;
         } else if (!(new File(this.g)).canWrite()) {
            this.a.error("Engine root at " + this.g + "is not writable, cannot replace it");
            return false;
         } else {
            GeneralWURFLEngine var2;
            if (this.f != null && this.f.size() != 0) {
               var2 = new GeneralWURFLEngine(new XMLResource(var1), this.f);
            } else {
               var2 = new GeneralWURFLEngine(var1);
            }

            var2.load();
            FileUtils.copyFile(new File(var1), new File(this.g), true);
            this.reload(this.g);
            return true;
         }
      } catch (Exception var3) {
         this.a.error("An error has occurred replacing " + this.g + "root with " + var1, var3);
         return false;
      }
   }

   public void setMarkupResolver(MarkupResolver var1) {
      this.h = var1;
   }

   public void setCapabilitiesHolderFactory(CapabilitiesHolderFactory var1) {
      this.i = var1;
   }

   public void setWurflRequestFactory(WURFLRequestFactory var1) {
      if (!(var1 instanceof WURFLRequestFactoryWithPriority)) {
         throw new UnsupportedOperationException("User-Agent priority is not supported if the custom request factory does not implement WURFLRequestFactoryWithPriority");
      } else {
         synchronized(this.p) {
            this.q = (WURFLRequestFactoryWithPriority)var1;
            this.s = this.q.getUserAgentPriority();
            if (this.l != null) {
               this.l.setRequestFactory(this.q);
            }

         }
      }
   }

   public void setUserAgentResolver(UserAgentResolver var1) {
      this.m = var1;
   }

   public void setDeviceProvider(DeviceProvider var1) {
      this.j = var1;
   }

   public void setCacheProvider(CacheProvider var1) {
      this.k = var1;
   }

   public final WURFLUtils getWURFLUtils() {
      this.a();
      this.d.readLock().lock();
      WURFLUtils var1 = this.n;
      this.d.readLock().unlock();
      return var1;
   }

   public Device getDeviceById(String var1) {
      return this.getWURFLUtils().getDeviceById(var1);
   }

   public Device getDeviceById(String var1, WURFLRequest var2) {
      return this.getWURFLUtils().getDeviceById(var1, var2);
   }

   public Device getDeviceById(String var1, HttpServletRequest var2) {
      this.a();
      return this.getWURFLUtils().getDeviceById(var1, (new DefaultWURFLRequestFactory(this.m, this.l.getUserAgentPriority())).createRequest(var2, this.l.getEngineTarget()));
   }

   public Set getAllVirtualCapabilities() {
      this.a();
      return new HashSet(VirtualCapabilityHandler.getAllVirtualCapabilities());
   }

   private void a() {
      if (!this.p) {
         synchronized(this.p) {
            if (!this.p) {
               try {
                  GeneralWURFLEngine var2 = this;

                  try {
                     var2.d.writeLock().lock();
                     if (var2.o == null) {
                        var2.o = new DefaultWURFLModel(var2.e, var2.f, var2.c);
                     }

                     var2.e = null;
                     var2.f = null;
                     if (var2.l == null) {
                        MatcherManager var3 = new MatcherManager(var2.o);
                        if (var2.h != null && var2.a.isInfoEnabled()) {
                           var2.a.info("markupResolver is custom: " + var2.h.getClass().getName());
                        }

                        if (var2.i != null && var2.a.isInfoEnabled()) {
                           var2.a.info("capabilitiesHolderFactory is custom: " + var2.i.getClass().getName());
                        }

                        var2.b();
                        if (var2.k != null && var2.a.isInfoEnabled()) {
                           var2.a.info("cacheProvider is custom: " + var2.k.getClass().getName());
                        }

                        if (var2.q == null) {
                           if (var2.m != null) {
                              if (var2.a.isInfoEnabled()) {
                                 var2.a.info("userAgentResolver is custom: " + var2.m.getClass().getName());
                              }

                              var2.q = new DefaultWURFLRequestFactory(var2.m, var2.s);
                           } else {
                              var2.q = new DefaultWURFLRequestFactory(var2.s);
                           }
                        } else if (var2.a.isInfoEnabled()) {
                           var2.a.info("wurflRequestFactory is custom: " + var2.q.getClass().getName());
                        }

                        if (var2.r != null) {
                           var2.l = new WURFLServiceImpl(var2.o, var3, var2.j, var2.q, var2.r);
                        } else {
                           var2.l = new WURFLServiceImpl(var2.o, var3, var2.j, var2.q);
                        }
                     } else if (var2.a.isInfoEnabled()) {
                        var2.a.info("wurflService is fed: " + var2.l.getClass().getName());
                     }

                     var2.b();
                     if (var2.k != null) {
                        if (var2.a.isInfoEnabled()) {
                           var2.a.info("cacheProvider is fed: " + var2.k.getClass().getName());
                        }

                        var2.l.setCacheProvider(var2.k);
                     }

                     if (var2.n == null) {
                        var2.n = new WURFLUtils(var2.o, var2.j, var2.l);
                     }

                     VirtualCapabilityUserAgentTool.getInstance().assignProperties(var2.q.createRequest("", var2.r), var2.j.getInternalDevice("generic"));
                     var2.p = true;
                  } finally {
                     this.d.writeLock().unlock();
                  }
               } catch (Exception var11) {
                  this.a.error("cannot initialize: " + var11, var11);
                  if (var11 instanceof WURFLRuntimeException) {
                     throw (WURFLRuntimeException)var11;
                  }

                  throw new WURFLRuntimeException(var11);
               }

               GeneralWURFLEngine var13 = this;

               try {
                  Class var14;
                  Object var4 = (var14 = Class.forName("com.scientiamobile.wurfl.core.CheckConnection")).newInstance();
                  var14.getDeclaredMethod("setup", WURFLEngine.class, WURFLModel.class).invoke(var4, var13, var13.o);
                  var14.getDeclaredMethod("check").invoke(var4);
               } catch (Exception var9) {
               }
            }

         }
      }
   }

   private void b() {
      if (this.i == null) {
         this.i = new DefaultCapabilitiesHolderFactory(this.o);
      }

      if (this.j == null) {
         if (this.h != null) {
            this.j = new DefaultDeviceProvider(this.o, this.i, this.h);
         } else {
            this.j = new DefaultDeviceProvider(this.o, this.i);
         }
      } else {
         if (this.a.isInfoEnabled()) {
            this.a.info("Device Provider is fed: " + this.j.getClass().getName());
         }

      }
   }

   public Device getDeviceForRequest(HttpServletRequest var1) {
      this.a();
      return this.l.getDevice(var1);
   }

   public Device getDeviceForRequest(WURFLRequest var1) {
      this.a();
      return this.l.getDevice(var1);
   }

   public Device getDeviceForRequest(String var1) {
      this.a();
      return this.l.getDevice(var1);
   }

   public void load() {
      this.a();
   }

   public EngineTarget getEngineTarget() {
      synchronized(this.p) {
         if (this.l != null) {
            this.r = this.l.getEngineTarget();
         }

         return this.r;
      }
   }

   public void setEngineTarget(EngineTarget var1) {
      synchronized(this.p) {
         this.r = var1;
         if (this.l != null) {
            this.l.setEngineTarget(var1);
         }

      }
   }

   public UserAgentPriority getUserAgentPriority() {
      synchronized(this.p) {
         if (this.l != null) {
            this.s = this.l.getUserAgentPriority();
         } else if (this.q != null) {
            this.s = this.q.getUserAgentPriority();
         }

         return this.s;
      }
   }

   public void setUserAgentPriority(UserAgentPriority var1) {
      synchronized(this.p) {
         this.s = var1;
         if (this.l != null) {
            this.l.setUserAgentPriority(var1);
         } else if (this.q != null) {
            this.q.setUserAgentPriority(var1);
         }

      }
   }

   public final void setCapabilityFilter(String... var1) {
      ArrayList var4 = new ArrayList(Arrays.asList(var1));

      for(String var3 : b) {
         if (!var4.contains(var3)) {
            var4.add(var3);
         }
      }

      this.c = (String[])var4.toArray(new String[var4.size()]);
   }

   public final void setCapabilityFilter(Collection var1) {
      if (var1 != null) {
         ArrayList var4 = new ArrayList(var1);

         for(String var3 : b) {
            if (!var4.contains(var3)) {
               var4.add(var3);
            }
         }

         this.c = (String[])var4.toArray(new String[var4.size()]);
      }

   }

   private static WURFLResource a(String var0) {
      Validate.notEmpty(var0, "The path is null");
      return new XMLResource(var0);
   }

   private static WURFLResources a(String[] var0) {
      WURFLResources var1 = new WURFLResources();

      for(int var2 = 0; var0 != null && var2 < var0.length; ++var2) {
         var1.add(new XMLResource(var0[var2]));
      }

      return var1;
   }

   public String getAPIVersion() {
      return "1.9.1.0";
   }

   public Set getAllMandatoryCapabilities() {
      this.a();
      return new HashSet(Arrays.asList(VirtualCapabilityEvaluator.MANDATORY_CAPABILITIES));
   }

   public Set getAllCapabilities() {
      this.a();
      HashSet var1 = new HashSet();
      Iterator var2 = this.o.getAllCapabilities().iterator();

      while(var2.hasNext()) {
         String var3;
         if (!(var3 = (String)var2.next()).startsWith("controlcap_")) {
            var1.add(var3);
         }
      }

      return var1;
   }

   public String getRootPath() {
      return this.g;
   }

   public String getApiVersion() {
      return "1.9.1.0";
   }
}
