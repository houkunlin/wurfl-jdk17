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
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class j implements m {
  private final Logger a = LoggerFactory.getLogger(getClass());
  
  private WURFLModel b;
  
  private ReentrantReadWriteLock c;
  
  private CacheProvider d;
  
  private MatcherManager e;
  
  private DeviceProvider f;
  
  private WURFLRequestFactoryWithPriority g;
  
  private EngineTarget h;
  
  private final XmlFileLoader i = new XmlFileLoader("classpath:/META-INF/wurfl-config.xml", new k(this, (byte)0));
  
  private boolean j = false;
  
  public j(WURFLModel paramWURFLModel, MatcherManager paramMatcherManager, DeviceProvider paramDeviceProvider, WURFLRequestFactoryWithPriority paramWURFLRequestFactoryWithPriority, EngineTarget paramEngineTarget) {
    this.b = paramWURFLModel;
    this.e = paramMatcherManager;
    this.f = paramDeviceProvider;
    this.g = paramWURFLRequestFactoryWithPriority;
    if (paramEngineTarget == null) {
      try {
        if (!this.j) {
          this.i.parseFile();
          this.j = true;
        } 
      } catch (IOException iOException) {
        this.a.error(iOException.getMessage(), iOException);
        throw new WURFLRuntimeException(iOException);
      } 
    } else {
      this.h = paramEngineTarget;
    } 
    this.c = new ReentrantReadWriteLock();
    this.a.info(getClass().getSimpleName() + " created");
  }
  
  public j(WURFLModel paramWURFLModel, MatcherManager paramMatcherManager, DeviceProvider paramDeviceProvider, WURFLRequestFactoryWithPriority paramWURFLRequestFactoryWithPriority) {
    this(paramWURFLModel, paramMatcherManager, paramDeviceProvider, paramWURFLRequestFactoryWithPriority, null);
  }
  
  public final void a(CacheProvider paramCacheProvider) {
    this.a.info("feeding " + paramCacheProvider);
    this.d = paramCacheProvider;
  }
  
  public final Device a(WURFLRequest paramWURFLRequest) {
    this.c.readLock().lock();
    try {
      DeviceInfo deviceInfo;
      Validate.notNull(paramWURFLRequest, "The request is null");
      c();
      InternalDevice internalDevice;
      if ((internalDevice = this.d.getDevice(paramWURFLRequest.getOriginalUserAgent())) != null) {
        deviceInfo = new DeviceInfo(internalDevice.getId(), MatchType.cached, "Cache", "Cache", paramWURFLRequest.getOriginalUserAgent(), "");
      } else {
        paramWURFLRequest.performGenericNormalization();
        if (EngineTarget.fastDesktopBrowserMatch.equals(paramWURFLRequest.getEngineTarget()) && paramWURFLRequest._internalIsDesktopBrowserHeavyDutyAnalysis()) {
          internalDevice = this.f.getInternalDevice("generic_web_browser");
          this.d.putDevice(paramWURFLRequest.getOriginalUserAgent(), internalDevice);
          device = this.f.buildDevice(internalDevice, paramWURFLRequest, MatchType.fastDesktopBrowser, "", "");
          return device;
        } 
        deviceInfo = this.e.matchRequest((WURFLRequest)device);
        if ((internalDevice = this.d.getInternalDeviceFromDeviceId(deviceInfo.getId())) == null)
          internalDevice = this.f.getInternalDevice(deviceInfo.getId()); 
        this.d.putDevice(device.getOriginalUserAgent(), internalDevice);
      } 
      if (!k && internalDevice == null)
        throw new AssertionError(); 
      Device device = this.f.buildDevice(internalDevice, (WURFLRequest)device, deviceInfo.getMatchType(), deviceInfo.a(), deviceInfo.b());
      return device;
    } finally {
      this.c.readLock().unlock();
    } 
  }
  
  public final Device a(HttpServletRequest paramHttpServletRequest) {
    Validate.notNull(paramHttpServletRequest, "The request must be not null");
    WURFLRequest wURFLRequest = this.g.createRequest(paramHttpServletRequest, this.h);
    return a(wURFLRequest);
  }
  
  public final Device a(String paramString) {
    Validate.notNull(paramString, "The userAgent must be not null");
    WURFLRequest wURFLRequest = this.g.createRequest(paramString, this.h);
    return a(wURFLRequest);
  }
  
  private void c() {
    if (this.d == null)
      synchronized (this) {
        if (this.d == null) {
          this.a.info("no Cache Provider, using default (DoubleLRUMapCacheProvider)");
          this.d = (CacheProvider)new DoubleLRUMapCacheProvider();
        } 
        return;
      }  
  }
  
  public final EngineTarget a() {
    return this.h;
  }
  
  public final void a(EngineTarget paramEngineTarget) {
    if (paramEngineTarget != EngineTarget.fastDesktopBrowserMatch) {
      this.h = EngineTarget.defaultTarget;
      return;
    } 
    this.h = paramEngineTarget;
  }
  
  public final UserAgentPriority b() {
    return this.g.getUserAgentPriority();
  }
  
  public final void a(UserAgentPriority paramUserAgentPriority) {
    this.g.setUserAgentPriority(paramUserAgentPriority);
  }
  
  public final Device b(String paramString) {
    InternalDevice internalDevice = this.f.getInternalDevice(paramString);
    ModelDevice modelDevice;
    for (modelDevice = ((h)internalDevice).a(); modelDevice != null && modelDevice.getUserAgent().contains("DO_NOT_MATCH"); modelDevice = modelDevice.getAncestor());
    String str;
    WURFLRequest wURFLRequest = this.g.createRequest(!(str = (internalDevice = internalDevice).getWURFLUserAgent()).startsWith("DO_NOT_MATCH") ? str : ((modelDevice != null && modelDevice.getUserAgent() != null) ? modelDevice.getUserAgent() : ""), this.h);
    return a(paramString, wURFLRequest);
  }
  
  public final Device a(String paramString, HttpServletRequest paramHttpServletRequest) {
    Validate.notNull(paramHttpServletRequest, "The request must be not null");
    WURFLRequest wURFLRequest = this.g.createRequest(paramHttpServletRequest, this.h);
    return a(paramString, wURFLRequest);
  }
  
  public final Device a(String paramString, WURFLRequest paramWURFLRequest) {
    Validate.notNull(paramWURFLRequest, "The request must be not null");
    paramWURFLRequest.performGenericNormalization();
    return this.f.buildDevice(this.f.getInternalDevice(paramString), paramWURFLRequest, MatchType.none, "Utils", "Utils");
  }
  
  public final void a(WURFLResource paramWURFLResource, WURFLResources paramWURFLResources, String... paramVarArgs) {
    this.c.writeLock().lock();
    this.a.info("reloading service");
    try {
      this.b.reload(paramWURFLResource, paramWURFLResources, paramVarArgs);
      this.e.reloadModel(this.b);
      d();
      return;
    } finally {
      this.c.writeLock().unlock();
    } 
  }
  
  private void d() {
    this.a.info("about to clear cache provider");
    c();
    this.d.clear();
  }
  
  public final void a(WURFLResources paramWURFLResources, String... paramVarArgs) {
    this.a.info("before applying patches " + paramWURFLResources);
    this.c.writeLock().lock();
    try {
      this.b.applyPatches(paramWURFLResources, paramVarArgs);
      this.e.reloadModel(this.b);
      d();
      this.a.info("finished applying patches " + paramWURFLResources);
      return;
    } finally {
      this.c.writeLock().unlock();
    } 
  }
  
  public final void a(WURFLRequestFactoryWithPriority paramWURFLRequestFactoryWithPriority) {
    this.g = paramWURFLRequestFactoryWithPriority;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\j.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
