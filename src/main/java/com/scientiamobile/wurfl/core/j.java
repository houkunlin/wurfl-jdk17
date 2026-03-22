package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.cache.CacheProvider;
import com.scientiamobile.wurfl.core.cache.DoubleLRUMapCacheProvider;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.matchers.MatcherManager;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequestFactoryWithPriority;
import com.scientiamobile.wurfl.core.resource.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class j implements m {
  private final Logger a = LoggerFactory.getLogger(getClass());

  private WURFLModel wurflModel;

  private ReentrantReadWriteLock reentrantReadWriteLock;

  private volatile CacheProvider cacheProvider;

  private MatcherManager matcherManager;

  private DeviceProvider deviceProvider;

  private WURFLRequestFactoryWithPriority wurflRequestFactoryWithPriority;

  private EngineTarget engineTarget;

  private final XmlFileLoader xmlFileLoader = new XmlFileLoader("classpath:/META-INF/wurfl-config.xml", new k(this, (byte) 0));

  private boolean init = false;

  public j(WURFLModel paramWURFLModel, MatcherManager paramMatcherManager, DeviceProvider paramDeviceProvider, WURFLRequestFactoryWithPriority paramWURFLRequestFactoryWithPriority, EngineTarget paramEngineTarget) {
    this.wurflModel = paramWURFLModel;
    this.matcherManager = paramMatcherManager;
    this.deviceProvider = paramDeviceProvider;
    this.wurflRequestFactoryWithPriority = paramWURFLRequestFactoryWithPriority;
    if (paramEngineTarget == null) {
      try {
        if (!this.init) {
          this.xmlFileLoader.parseFile();
          this.init = true;
        }
      } catch (IOException iOException) {
        this.a.error(iOException.getMessage(), iOException);
        throw new WURFLRuntimeException(iOException);
      }
    } else {
      this.engineTarget = paramEngineTarget;
    }
    this.reentrantReadWriteLock = new ReentrantReadWriteLock();
    this.a.info("{} created", getClass().getSimpleName());
  }

  public j(WURFLModel paramWURFLModel, MatcherManager paramMatcherManager, DeviceProvider paramDeviceProvider, WURFLRequestFactoryWithPriority paramWURFLRequestFactoryWithPriority) {
    this(paramWURFLModel, paramMatcherManager, paramDeviceProvider, paramWURFLRequestFactoryWithPriority, null);
  }

  public final void getDeviceForRequest(CacheProvider paramCacheProvider) {
    this.a.info("feeding {}", paramCacheProvider);
    this.cacheProvider = paramCacheProvider;
  }

  public final Device getDeviceForRequest(WURFLRequest paramWURFLRequest) {
    this.reentrantReadWriteLock.readLock().lock();
    try {
      DeviceInfo deviceInfo;
      Validate.notNull(paramWURFLRequest, "The request is null");
      c();
      InternalDevice internalDevice;
      if ((internalDevice = this.cacheProvider.getDevice(paramWURFLRequest.getOriginalUserAgent())) != null) {
        deviceInfo = new DeviceInfo(internalDevice.getId(), MatchType.cached, "Cache", "Cache", paramWURFLRequest.getOriginalUserAgent(), "");
      } else {
        paramWURFLRequest.performGenericNormalization();
        if (EngineTarget.fastDesktopBrowserMatch.equals(paramWURFLRequest.getEngineTarget()) && paramWURFLRequest._internalIsDesktopBrowserHeavyDutyAnalysis()) {
          internalDevice = this.deviceProvider.getInternalDevice("generic_web_browser");
          this.cacheProvider.putDevice(paramWURFLRequest.getOriginalUserAgent(), internalDevice);
          return this.deviceProvider.buildDevice(internalDevice, paramWURFLRequest, MatchType.fastDesktopBrowser, "", "");
        }
        deviceInfo = this.matcherManager.matchRequest(paramWURFLRequest);
        if ((internalDevice = this.cacheProvider.getInternalDeviceFromDeviceId(deviceInfo.getId())) == null)
          internalDevice = this.deviceProvider.getInternalDevice(deviceInfo.getId());
        this.cacheProvider.putDevice(paramWURFLRequest.getOriginalUserAgent(), internalDevice);
      }
      if (internalDevice == null)
        throw new AssertionError();
      return this.deviceProvider.buildDevice(internalDevice, paramWURFLRequest, deviceInfo.getMatchType(), deviceInfo.a(), deviceInfo.b());
    } finally {
      this.reentrantReadWriteLock.readLock().unlock();
    }
  }

  public final Device getDeviceForRequest(HttpServletRequest paramHttpServletRequest) {
    Validate.notNull(paramHttpServletRequest, "The request must be not null");
    WURFLRequest wURFLRequest = this.wurflRequestFactoryWithPriority.createRequest(paramHttpServletRequest, this.engineTarget);
    return getDeviceForRequest(wURFLRequest);
  }

  public final Device getDeviceForRequest(String paramString) {
    Validate.notNull(paramString, "The userAgent must be not null");
    WURFLRequest wURFLRequest = this.wurflRequestFactoryWithPriority.createRequest(paramString, this.engineTarget);
    return getDeviceForRequest(wURFLRequest);
  }

  private void c() {
    if (this.cacheProvider == null)
      synchronized (this) {
        if (this.cacheProvider == null) {
          this.a.info("no Cache Provider, using default (DoubleLRUMapCacheProvider)");
          this.cacheProvider = new DoubleLRUMapCacheProvider();
        }
      }
  }

  public final EngineTarget getDeviceForRequest() {
    return this.engineTarget;
  }

  public final void getDeviceForRequest(EngineTarget paramEngineTarget) {
    if (paramEngineTarget != EngineTarget.fastDesktopBrowserMatch) {
      this.engineTarget = EngineTarget.defaultTarget;
      return;
    }
    this.engineTarget = paramEngineTarget;
  }

  public final UserAgentPriority b() {
    return this.wurflRequestFactoryWithPriority.getUserAgentPriority();
  }

  public final void getDeviceForRequest(UserAgentPriority paramUserAgentPriority) {
    this.wurflRequestFactoryWithPriority.setUserAgentPriority(paramUserAgentPriority);
  }

  public final Device b(String paramString) {
    InternalDevice internalDevice = this.deviceProvider.getInternalDevice(paramString);
    ModelDevice modelDevice;
    for (modelDevice = ((h)internalDevice).a(); modelDevice != null && modelDevice.getUserAgent().contains("DO_NOT_MATCH"); modelDevice = modelDevice.getAncestor());
    String str;
    WURFLRequest wURFLRequest = this.wurflRequestFactoryWithPriority.createRequest(!(str = internalDevice.getWURFLUserAgent()).startsWith("DO_NOT_MATCH") ? str : ((modelDevice != null && modelDevice.getUserAgent() != null) ? modelDevice.getUserAgent() : ""), this.engineTarget);
    return getDeviceForRequest(paramString, wURFLRequest);
  }

  public final Device getDeviceForRequest(String paramString, HttpServletRequest paramHttpServletRequest) {
    Validate.notNull(paramHttpServletRequest, "The request must be not null");
    WURFLRequest wURFLRequest = this.wurflRequestFactoryWithPriority.createRequest(paramHttpServletRequest, this.engineTarget);
    return getDeviceForRequest(paramString, wURFLRequest);
  }

  public final Device getDeviceForRequest(String paramString, WURFLRequest paramWURFLRequest) {
    Validate.notNull(paramWURFLRequest, "The request must be not null");
    paramWURFLRequest.performGenericNormalization();
    return this.deviceProvider.buildDevice(this.deviceProvider.getInternalDevice(paramString), paramWURFLRequest, MatchType.none, "Utils", "Utils");
  }

  public final void getDeviceForRequest(WURFLResource paramWURFLResource, WURFLResources paramWURFLResources, String... paramVarArgs) {
    this.reentrantReadWriteLock.writeLock().lock();
    this.a.info("reloading service");
    try {
      this.wurflModel.reload(paramWURFLResource, paramWURFLResources, paramVarArgs);
      this.matcherManager.reloadModel(this.wurflModel);
      d();
    } finally {
      this.reentrantReadWriteLock.writeLock().unlock();
    }
  }

  private void d() {
    this.a.info("about to clear cache provider");
    c();
    this.cacheProvider.clear();
  }

  public final void getDeviceForRequest(WURFLResources paramWURFLResources, String... paramVarArgs) {
      this.a.info("before applying patches {}", paramWURFLResources);
    this.reentrantReadWriteLock.writeLock().lock();
    try {
      this.wurflModel.applyPatches(paramWURFLResources, paramVarArgs);
      this.matcherManager.reloadModel(this.wurflModel);
      d();
        this.a.info("finished applying patches {}", paramWURFLResources);
    } finally {
      this.reentrantReadWriteLock.writeLock().unlock();
    }
  }

  public final void getDeviceForRequest(WURFLRequestFactoryWithPriority paramWURFLRequestFactoryWithPriority) {
    this.wurflRequestFactoryWithPriority = paramWURFLRequestFactoryWithPriority;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\j.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
