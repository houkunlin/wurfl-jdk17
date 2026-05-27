package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.cache.CacheProvider;
import com.scientiamobile.wurfl.core.cache.DoubleLRUMapCacheProvider;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.matchers.MatcherManager;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequestFactoryWithPriority;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.resource.WURFLResource;
import com.scientiamobile.wurfl.core.resource.WURFLResources;
import com.scientiamobile.wurfl.core.resource.XmlFileLoader;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class WURFLServiceImpl implements WURFLService {
   private static final Logger log = LoggerFactory.getLogger(WURFLServiceImpl.class);
   private WURFLModel wurflModel;
   private ReentrantReadWriteLock modelLock;
   private CacheProvider cacheProvider;
   private MatcherManager matcherManager;
   private DeviceProvider deviceProvider;
   private WURFLRequestFactoryWithPriority requestFactory;
   private EngineTarget engineTarget;
   private final XmlFileLoader configFileLoader;
   private boolean configLoaded;
   private static boolean assertionsDisabled = !WURFLServiceImpl.class.desiredAssertionStatus();

   public WURFLServiceImpl(WURFLModel wurflModel, MatcherManager matcherManager, DeviceProvider deviceProvider, WURFLRequestFactoryWithPriority requestFactory, EngineTarget engineTarget) {
      
      this.configFileLoader = new XmlFileLoader("classpath:/META-INF/wurfl-config.xml", new ApiConfigHandler(this, (byte)0));
      this.configLoaded = false;
      this.wurflModel = wurflModel;
      this.matcherManager = matcherManager;
      this.deviceProvider = deviceProvider;
      this.requestFactory = requestFactory;
      if (engineTarget == null) {
         if (!this.configLoaded) {
            this.configFileLoader.parseFile();
            this.configLoaded = true;
         }
      } else {
         this.engineTarget = engineTarget;
      }

      this.modelLock = new ReentrantReadWriteLock();
      log.info("{} created", this.getClass().getSimpleName());
   }

   public WURFLServiceImpl(WURFLModel wurflModel, MatcherManager matcherManager, DeviceProvider deviceProvider, WURFLRequestFactoryWithPriority requestFactory) {
      this(wurflModel, matcherManager, deviceProvider, requestFactory, (EngineTarget)null);
   }

   @Override
   public void setCacheProvider(CacheProvider cacheProvider) {
      log.info("feeding {}", cacheProvider);
      this.cacheProvider = cacheProvider;
   }

   @Override
   public Device getDevice(WURFLRequest request) {
      this.modelLock.readLock().lock();

      try {
         Validate.notNull(request, "The request is null");
         this.ensureCacheProvider();
         InternalDevice internalDevice;
         DeviceInfo deviceInfo;
         internalDevice = this.cacheProvider.getDevice(request.getOriginalUserAgent());
         if (internalDevice != null) {
            deviceInfo = new DeviceInfo(internalDevice.getId(), MatchType.cached, "Cache", "Cache", request.getOriginalUserAgent(), "");
         } else {
            request.performGenericNormalization();
            if (EngineTarget.fastDesktopBrowserMatch.equals(request.getEngineTarget()) && request._internalIsDesktopBrowserHeavyDutyAnalysis()) {
               internalDevice = this.deviceProvider.getInternalDevice("generic_web_browser");
               this.cacheProvider.putDevice(request.getOriginalUserAgent(), internalDevice);
               Device fastMatched = this.deviceProvider.buildDevice(internalDevice, request, MatchType.fastDesktopBrowser, "", "");
               return fastMatched;
            }

            deviceInfo = this.matcherManager.matchRequest(request);
            internalDevice = this.cacheProvider.getInternalDeviceFromDeviceId(deviceInfo.getId());
            if (internalDevice == null) {
               internalDevice = this.deviceProvider.getInternalDevice(deviceInfo.getId());
            }

            this.cacheProvider.putDevice(request.getOriginalUserAgent(), internalDevice);
         }

         if (!assertionsDisabled && internalDevice == null) {
            throw new AssertionError();
         } else {
            Device device = this.deviceProvider.buildDevice(internalDevice, request, deviceInfo.getMatchType(), deviceInfo.getMatcherName(), deviceInfo.getBucketMatcherName());
            return device;
         }
      } finally {
         this.modelLock.readLock().unlock();
      }
   }

   @Override
   public Device getDevice(HttpServletRequest request) {
      Validate.notNull(request, "The request must be not null");
      WURFLRequest wurflRequest = this.requestFactory.createRequest(request, this.engineTarget);
      return this.getDevice(wurflRequest);
   }

   @Override
   public Device getDevice(String userAgent) {
      Validate.notNull(userAgent, "The userAgent must be not null");
      WURFLRequest wurflRequest = this.requestFactory.createRequest(userAgent, this.engineTarget);
      return this.getDevice(wurflRequest);
   }

   private void ensureCacheProvider() {
      if (this.cacheProvider == null) {
         synchronized(this) {
            if (this.cacheProvider == null) {
               log.info("no Cache Provider, using default (DoubleLRUMapCacheProvider)");
               this.cacheProvider = new DoubleLRUMapCacheProvider();
            }

         }
      }
   }

   @Override
   public EngineTarget getEngineTarget() {
      return this.engineTarget;
   }

   @Override
   public void setEngineTarget(EngineTarget engineTarget) {
      if (engineTarget != EngineTarget.fastDesktopBrowserMatch) {
         this.engineTarget = EngineTarget.defaultTarget;
      } else {
         this.engineTarget = engineTarget;
      }
   }

   @Override
   public UserAgentPriority getUserAgentPriority() {
      return this.requestFactory.getUserAgentPriority();
   }

   @Override
   public void setUserAgentPriority(UserAgentPriority priority) {
      this.requestFactory.setUserAgentPriority(priority);
   }

   @Override
   public Device getDeviceById(String deviceId) {
      InternalDevice internalDevice = this.deviceProvider.getInternalDevice(deviceId);
      String userAgentFromModel;
      String userAgent;
      if (!(userAgentFromModel = internalDevice.getWURFLUserAgent()).startsWith("DO_NOT_MATCH")) {
         userAgent = userAgentFromModel;
      } else {
         ModelDevice ancestor;
         for(ancestor = ((InternalDeviceImpl)internalDevice).getAncestorModelDevice(); ancestor != null && ancestor.getUserAgent().contains("DO_NOT_MATCH"); ancestor = ancestor.getAncestor()) {
         }

         userAgent = ancestor != null && ancestor.getUserAgent() != null ? ancestor.getUserAgent() : "";
      }

      WURFLRequest request = this.requestFactory.createRequest(userAgent, this.engineTarget);
      return this.getDeviceById(deviceId, request);
   }

   @Override
   public Device getDeviceById(String deviceId, HttpServletRequest request) {
      Validate.notNull(request, "The request must be not null");
      WURFLRequest wurflRequest = this.requestFactory.createRequest(request, this.engineTarget);
      return this.getDeviceById(deviceId, wurflRequest);
   }

   @Override
   public Device getDeviceById(String deviceId, WURFLRequest request) {
      Validate.notNull(request, "The request must be not null");
      request.performGenericNormalization();
      return this.deviceProvider.buildDevice(this.deviceProvider.getInternalDevice(deviceId), request, MatchType.none, "Utils", "Utils");
   }

   @Override
   public void reload(WURFLResource wurflResource, WURFLResources wurflResources, String... patches) {
      this.modelLock.writeLock().lock();
      log.info("reloading service");

      try {
         this.wurflModel.reload(wurflResource, wurflResources, patches);
         this.matcherManager.reloadModel(this.wurflModel);
         this.clearCacheProvider();
      } finally {
         this.modelLock.writeLock().unlock();
      }

   }

   private void clearCacheProvider() {
      log.info("about to clear cache provider");
      this.ensureCacheProvider();
      this.cacheProvider.clear();
   }

   @Override
   public void applyPatches(WURFLResources wurflResources, String... patches) {
      log.info("before applying patches {}", wurflResources);
      this.modelLock.writeLock().lock();

      try {
         this.wurflModel.applyPatches(wurflResources, patches);
         this.matcherManager.reloadModel(this.wurflModel);
         this.clearCacheProvider();
         log.info("finished applying patches {}", wurflResources);
      } finally {
         this.modelLock.writeLock().unlock();
      }

   }

   public final void setRequestFactory(WURFLRequestFactoryWithPriority requestFactory) {
      this.requestFactory = requestFactory;
   }

   static EngineTarget getEngineTarget(WURFLServiceImpl service) {
      return service.engineTarget;
   }

   static EngineTarget setEngineTarget(WURFLServiceImpl service, EngineTarget engineTarget) {
      return service.engineTarget = engineTarget;
   }
}
