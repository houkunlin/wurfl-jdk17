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
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralWURFLEngine implements WURFLEngine, WurflWebConstants {
   private final transient Logger log;
   private static final List<String> ALWAYS_INCLUDED_CAPABILITIES = Arrays.asList("device_os", "device_os_version", "is_tablet", "is_wireless_device", "pointing_method", "preferred_markup", "resolution_height", "resolution_width", "ux_full_desktop", "xhtml_support_level", "is_smarttv", "can_assign_phone_number", "brand_name", "model_name", "marketing_name", "mobile_browser_version");
   private String[] capabilityFilter;
   private final ReadWriteLock lock;
   private WURFLResource rootResource;
   private WURFLResources patchResources;
   private String rootPath;
   private MarkupResolver markupResolver;
   private CapabilitiesHolderFactory capabilitiesHolderFactory;
   private DeviceProvider deviceProvider;
   private CacheProvider cacheProvider;
   private WURFLService wurflService;
   private UserAgentResolver userAgentResolver;
   private WURFLUtils wurflUtils;
   private WURFLModel wurflModel;
   private final Object initLock;
   private volatile boolean initialized;
   private WURFLRequestFactoryWithPriority requestFactory;
   private EngineTarget engineTarget;
   private UserAgentPriority userAgentPriority;

   public GeneralWURFLEngine(String rootPath) {
      this((WURFLResource)(new XMLResource(rootPath)));
      this.rootPath = rootPath;
   }

   public GeneralWURFLEngine(WURFLResource rootResource) {
      this(rootResource, (WURFLResources)null);
   }

   public GeneralWURFLEngine(String rootPath, String... patchPaths) {
      this.log = LoggerFactory.getLogger(this.getClass());
      this.capabilityFilter = null;
      this.lock = new ReentrantReadWriteLock();
      this.rootResource = null;
      this.patchResources = null;
      this.rootPath = null;
      this.initLock = new Object();
      this.initialized = false;
      this.requestFactory = null;
      this.engineTarget = null;
      this.userAgentPriority = UserAgentPriority.OverrideSideloadedBrowserUserAgent;
      WURFLResource resolvedRootResource = createXmlResource(rootPath);
      WURFLResources resolvedPatchResources = createXmlResources(patchPaths);
      Validate.notNull(resolvedRootResource, "The root resource is null");
      this.rootResource = resolvedRootResource;
      this.rootPath = rootPath;
      this.patchResources = resolvedPatchResources;
   }

   public GeneralWURFLEngine(WURFLResource rootResource, WURFLResource... patchResources) {
      this(rootResource, new WURFLResources(patchResources));
   }

   public GeneralWURFLEngine(WURFLResource rootResource, WURFLResources patchResources) {
      this.log = LoggerFactory.getLogger(this.getClass());
      this.capabilityFilter = null;
      this.lock = new ReentrantReadWriteLock();
      this.rootResource = null;
      this.patchResources = null;
      this.rootPath = null;
      this.initLock = new Object();
      this.initialized = false;
      this.requestFactory = null;
      this.engineTarget = null;
      this.userAgentPriority = UserAgentPriority.OverrideSideloadedBrowserUserAgent;
      Validate.notNull(rootResource, "The root resource is null");
      this.rootResource = rootResource;
      if (rootResource != null) {
         this.rootPath = rootResource.getOriginalPath();
      }

      this.patchResources = patchResources;
   }

   @Override
   public void reload(String rootPath) {
      WURFLResource rootResource = createXmlResource(rootPath);
      this.reload(rootResource, (WURFLResources)null);
   }

   @Override
   public void applyPatches(String... patchPaths) {
      if (patchPaths == null) {
         this.log.warn("null patches, do nothing...");
      } else {
         this.applyPatches(createXmlResources(patchPaths));
      }
   }

   @Override
   public void applyPatches(WURFLResource... patchResources) {
      this.applyPatches(new WURFLResources(patchResources));
   }

   @Override
   public void applyPatches(WURFLResources patchResources) {
      if (patchResources == null) {
         this.log.warn("null patches, do nothing...");
      } else {
         this.lock.writeLock().lock();

         try {
            this.ensureInitialized();
            this.wurflService.applyPatches(patchResources, this.capabilityFilter);
         } finally {
            this.lock.writeLock().unlock();
         }

      }
   }

   @Override
   public void reload(String rootPath, String[] patchPaths) {
      WURFLResource rootResource = createXmlResource(rootPath);
      WURFLResources patchResources = createXmlResources(patchPaths);
      this.reload(rootResource, patchResources);
   }

   @Override
   public void reload(WURFLResource rootResource, WURFLResource... patchResources) {
      this.reload(rootResource, new WURFLResources(patchResources));
   }

   @Override
   public void reload(WURFLResource rootResource, WURFLResources patchResources) {
      this.rootPath = rootResource.getOriginalPath();
      this.ensureInitialized();
      if ((patchResources = patchResources) == null) {
         patchResources = new WURFLResources();
      }

      this.lock.writeLock().lock();

      try {
         this.wurflService.reload(rootResource, patchResources, this.capabilityFilter);
      } finally {
         this.lock.writeLock().unlock();
      }

   }

   @Override
   public boolean replaceRoot(String newRootPath) {
      try {
         if (StringUtils.isBlank(newRootPath)) {
            this.log.warn("Empty value has been provided for replacing root, skipping");
            return false;
         } else if (!(new File(this.rootPath).getCanonicalFile()).canWrite()) {
            this.log.error("Engine root at {}is not writable, cannot replace it", this.rootPath);
            return false;
         } else {
            GeneralWURFLEngine newEngine;
            if (this.patchResources != null && this.patchResources.size() != 0) {
               newEngine = new GeneralWURFLEngine(new XMLResource(newRootPath), this.patchResources);
            } else {
               newEngine = new GeneralWURFLEngine(newRootPath);
            }

            newEngine.load();
            FileUtils.copyFile(new File(newRootPath).getCanonicalFile(), new File(this.rootPath).getCanonicalFile(), true);
            this.reload(this.rootPath);
            return true;
         }
      } catch (Exception e) {
         this.log.error("An error has occurred replacing {}root with {}", this.rootPath, newRootPath, e);
         return false;
      }
   }

   @Override
   public void setMarkupResolver(MarkupResolver markupResolver) {
      this.markupResolver = markupResolver;
   }

   @Override
   public void setCapabilitiesHolderFactory(CapabilitiesHolderFactory capabilitiesHolderFactory) {
      this.capabilitiesHolderFactory = capabilitiesHolderFactory;
   }

   @Override
   public void setWurflRequestFactory(WURFLRequestFactory requestFactory) {
      if (!(requestFactory instanceof WURFLRequestFactoryWithPriority)) {
         throw new UnsupportedOperationException("User-Agent priority is not supported if the custom request factory does not implement WURFLRequestFactoryWithPriority");
      } else {
         synchronized(this.initLock) {
            this.requestFactory = (WURFLRequestFactoryWithPriority)requestFactory;
            this.userAgentPriority = this.requestFactory.getUserAgentPriority();
            if (this.wurflService != null) {
               this.wurflService.setRequestFactory(this.requestFactory);
            }

         }
      }
   }

   @Override
   public void setUserAgentResolver(UserAgentResolver userAgentResolver) {
      this.userAgentResolver = userAgentResolver;
   }

   @Override
   public void setDeviceProvider(DeviceProvider deviceProvider) {
      this.deviceProvider = deviceProvider;
   }

   @Override
   public void setCacheProvider(CacheProvider cacheProvider) {
      this.cacheProvider = cacheProvider;
   }

   @Override
   public final WURFLUtils getWURFLUtils() {
      this.ensureInitialized();
      this.lock.readLock().lock();
      WURFLUtils out = this.wurflUtils;
      this.lock.readLock().unlock();
      return out;
   }

   @Override
   public Device getDeviceById(String deviceId) {
      return this.getWURFLUtils().getDeviceById(deviceId);
   }

   @Override
   public Device getDeviceById(String deviceId, WURFLRequest request) {
      return this.getWURFLUtils().getDeviceById(deviceId, request);
   }

   @Override
   public Device getDeviceById(String deviceId, HttpServletRequest request) {
      this.ensureInitialized();
      return this.getWURFLUtils().getDeviceById(deviceId, (new DefaultWURFLRequestFactory(this.userAgentResolver, this.wurflService.getUserAgentPriority())).createRequest(request, this.wurflService.getEngineTarget()));
   }

   @Override
   public Set<String> getAllVirtualCapabilities() {
      this.ensureInitialized();
      return new HashSet<>(VirtualCapabilityHandler.getAllVirtualCapabilities());
   }

   private void ensureInitialized() {
      if (!this.initialized) {
         synchronized(this.initLock) {
            if (!this.initialized) {
               try {
                  try {
                     this.lock.writeLock().lock();
                     if (this.wurflModel == null) {
                        this.wurflModel = new DefaultWURFLModel(this.rootResource, this.patchResources, this.capabilityFilter);
                     }

                     this.rootResource = null;
                     this.patchResources = null;
                     if (this.wurflService == null) {
                        MatcherManager matcherManager = new MatcherManager(this.wurflModel);
                        if (this.markupResolver != null && this.log.isInfoEnabled()) {
                           this.log.info("markupResolver is custom: {}", this.markupResolver.getClass().getName());
                        }

                        if (this.capabilitiesHolderFactory != null && this.log.isInfoEnabled()) {
                           this.log.info("capabilitiesHolderFactory is custom: {}", this.capabilitiesHolderFactory.getClass().getName());
                        }

                        this.ensureDeviceProviderInitialized();
                        if (this.cacheProvider != null && this.log.isInfoEnabled()) {
                           this.log.info("cacheProvider is custom: {}", this.cacheProvider.getClass().getName());
                        }

                        if (this.requestFactory == null) {
                           if (this.userAgentResolver != null) {
                              if (this.log.isInfoEnabled()) {
                                 this.log.info("userAgentResolver is custom: {}", this.userAgentResolver.getClass().getName());
                              }

                              this.requestFactory = new DefaultWURFLRequestFactory(this.userAgentResolver, this.userAgentPriority);
                           } else {
                              this.requestFactory = new DefaultWURFLRequestFactory(this.userAgentPriority);
                           }
                        } else if (this.log.isInfoEnabled()) {
                           this.log.info("wurflRequestFactory is custom: {}", this.requestFactory.getClass().getName());
                        }

                        if (this.engineTarget != null) {
                           this.wurflService = new WURFLServiceImpl(this.wurflModel, matcherManager, this.deviceProvider, this.requestFactory, this.engineTarget);
                        } else {
                           this.wurflService = new WURFLServiceImpl(this.wurflModel, matcherManager, this.deviceProvider, this.requestFactory);
                        }
                     } else if (this.log.isInfoEnabled()) {
                        this.log.info("wurflService is fed: {}", this.wurflService.getClass().getName());
                     }

                     this.ensureDeviceProviderInitialized();
                     if (this.cacheProvider != null) {
                        if (this.log.isInfoEnabled()) {
                           this.log.info("cacheProvider is fed: {}", this.cacheProvider.getClass().getName());
                        }

                        this.wurflService.setCacheProvider(this.cacheProvider);
                     }

                     if (this.wurflUtils == null) {
                        this.wurflUtils = new WURFLUtils(this.wurflModel, this.deviceProvider, this.wurflService);
                     }

                     VirtualCapabilityUserAgentTool.getInstance().assignProperties(this.requestFactory.createRequest("", this.engineTarget), this.deviceProvider.getInternalDevice("generic"));
                     this.initialized = true;
                  } finally {
                     this.lock.writeLock().unlock();
                  }
               } catch (Exception e) {
                  this.log.error("cannot initialize: {}", e);
                  if (e instanceof WURFLRuntimeException) {
                     throw (WURFLRuntimeException)e;
                  }

                  throw new WURFLRuntimeException(e);
               }

               try {
                  Class<?> checkConnectionClass = Class.forName("com.scientiamobile.wurfl.core.CheckConnection");
                  Object checkConnectionInstance = checkConnectionClass.getDeclaredConstructor().newInstance();
                  checkConnectionClass.getDeclaredMethod("setup", WURFLEngine.class, WURFLModel.class).invoke(checkConnectionInstance, this, this.wurflModel);
                  checkConnectionClass.getDeclaredMethod("check").invoke(checkConnectionInstance);
               } catch (ReflectiveOperationException | RuntimeException ignore) {
               }
            }

         }
      }
   }

   private void ensureDeviceProviderInitialized() {
      if (this.capabilitiesHolderFactory == null) {
         this.capabilitiesHolderFactory = new DefaultCapabilitiesHolderFactory(this.wurflModel);
      }

      if (this.deviceProvider == null) {
         if (this.markupResolver != null) {
            this.deviceProvider = new DefaultDeviceProvider(this.wurflModel, this.capabilitiesHolderFactory, this.markupResolver);
         } else {
            this.deviceProvider = new DefaultDeviceProvider(this.wurflModel, this.capabilitiesHolderFactory);
         }
      } else {
         if (this.log.isInfoEnabled()) {
            this.log.info("Device Provider is fed: {}", this.deviceProvider.getClass().getName());
         }

      }
   }

   @Override
   public Device getDeviceForRequest(HttpServletRequest request) {
      this.ensureInitialized();
      return this.wurflService.getDevice(request);
   }

   @Override
   public Device getDeviceForRequest(WURFLRequest request) {
      this.ensureInitialized();
      return this.wurflService.getDevice(request);
   }

   @Override
   public Device getDeviceForRequest(String userAgent) {
      this.ensureInitialized();
      return this.wurflService.getDevice(userAgent);
   }

   @Override
   public void load() {
      this.ensureInitialized();
   }

   @Override
   public EngineTarget getEngineTarget() {
      synchronized(this.initLock) {
         if (this.wurflService != null) {
            this.engineTarget = this.wurflService.getEngineTarget();
         }

         return this.engineTarget;
      }
   }

   @Override
   public void setEngineTarget(EngineTarget engineTarget) {
      synchronized(this.initLock) {
         this.engineTarget = engineTarget;
         if (this.wurflService != null) {
            this.wurflService.setEngineTarget(engineTarget);
         }

      }
   }

   @Override
   public UserAgentPriority getUserAgentPriority() {
      synchronized(this.initLock) {
         if (this.wurflService != null) {
            this.userAgentPriority = this.wurflService.getUserAgentPriority();
         } else if (this.requestFactory != null) {
            this.userAgentPriority = this.requestFactory.getUserAgentPriority();
         }

         return this.userAgentPriority;
      }
   }

   @Override
   public void setUserAgentPriority(UserAgentPriority userAgentPriority) {
      synchronized(this.initLock) {
         this.userAgentPriority = userAgentPriority;
         if (this.wurflService != null) {
            this.wurflService.setUserAgentPriority(userAgentPriority);
         } else if (this.requestFactory != null) {
            this.requestFactory.setUserAgentPriority(userAgentPriority);
         }

      }
   }

   @Override
   public final void setCapabilityFilter(String... capabilityFilter) {
      ArrayList<String> capabilities = new ArrayList<>(Arrays.asList(capabilityFilter));

      for(String capability : ALWAYS_INCLUDED_CAPABILITIES) {
         if (!capabilities.contains(capability)) {
            capabilities.add(capability);
         }
      }

      this.capabilityFilter = capabilities.toArray(new String[capabilities.size()]);
   }

   @Override
   public final void setCapabilityFilter(Collection<String> capabilityFilter) {
      if (capabilityFilter != null) {
         ArrayList<String> capabilities = new ArrayList<>(capabilityFilter);

         for(String capability : ALWAYS_INCLUDED_CAPABILITIES) {
            if (!capabilities.contains(capability)) {
               capabilities.add(capability);
            }
         }

         this.capabilityFilter = capabilities.toArray(new String[capabilities.size()]);
      }

   }

   private static WURFLResource createXmlResource(String path) {
      Validate.notEmpty(path, "The path is null");
      return new XMLResource(path);
   }

   private static WURFLResources createXmlResources(String[] paths) {
      WURFLResources resources = new WURFLResources();

      for(int i = 0; paths != null && i < paths.length; ++i) {
         resources.add(new XMLResource(paths[i]));
      }

      return resources;
   }

   @Override
   public String getAPIVersion() {
      return "1.9.1.0";
   }

   @Override
   public Set<String> getAllMandatoryCapabilities() {
      this.ensureInitialized();
      return new HashSet<>(Arrays.asList(VirtualCapabilityEvaluator.MANDATORY_CAPABILITIES));
   }

   @Override
   public Set<String> getAllCapabilities() {
      this.ensureInitialized();
      HashSet<String> capabilities = new HashSet<>();
      for (String capability : this.wurflModel.getAllCapabilities()) {
         if (!capability.startsWith("controlcap_")) {
            capabilities.add(capability);
         }
      }

      return capabilities;
   }

   @Override
   public String getRootPath() {
      return this.rootPath;
   }

   public String getApiVersion() {
      return "1.9.1.0";
   }
}
