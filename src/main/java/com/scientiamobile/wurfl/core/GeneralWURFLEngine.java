package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.cache.CacheProvider;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.matchers.MatcherManager;
import com.scientiamobile.wurfl.core.request.*;
import com.scientiamobile.wurfl.core.resource.*;
import com.scientiamobile.wurfl.core.vcap.VirtualCapabilityEvaluator;
import com.scientiamobile.wurfl.core.vcap.VirtualCapabilityHandler;
import com.scientiamobile.wurfl.core.web.WurflWebConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GeneralWURFLEngine implements WURFLEngine, WurflWebConstants {
  private final transient Logger logger = LoggerFactory.getLogger(getClass());

  private static final List<String> b = Arrays.asList(
        "device_os", "device_os_version", "is_tablet", "is_wireless_device", "pointing_method", "preferred_markup", "resolution_height", "resolution_width", "ux_full_desktop", "xhtml_support_level",
          "is_smarttv", "can_assign_phone_number", "brand_name", "model_name", "marketing_name", "mobile_browser_version");

  private String[] c = null;

  private final ReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

  private WURFLResource wurflResource = null;

  private WURFLResources wurflResources = null;

  private String wurflResourcePath = null;

  private MarkupResolver markupResolver;

  private CapabilitiesHolderFactory capabilitiesHolderFactory;

  private DeviceProvider deviceProvider;

  private CacheProvider cacheProvider;

  private m l;

  private UserAgentResolver userAgentResolver;

  private WURFLUtils wurflUtils;

  private WURFLModel wurflModel;

  private volatile boolean init = false;
  private final byte[] lock = new byte[0];

  private WURFLRequestFactoryWithPriority wurflRequestFactoryWithPriority = null;

  private EngineTarget engineTarget = null;

  private UserAgentPriority userAgentPriority = UserAgentPriority.OverrideSideloadedBrowserUserAgent;

  public GeneralWURFLEngine(String paramString) {
    this(new XMLResource(paramString));
    this.wurflResourcePath = paramString;
  }

  public GeneralWURFLEngine(WURFLResource paramWURFLResource) {
    this(paramWURFLResource, (WURFLResources)null);
  }

  public GeneralWURFLEngine(String paramString, String... paramVarArgs) {
    WURFLResource wURFLResource = loadWURFLResource(paramString);
    WURFLResources wURFLResources = loadWURFLResource(paramVarArgs);
    Validate.notNull(wURFLResource, "The root resource is null");
    this.wurflResource = wURFLResource;
    this.wurflResourcePath = paramString;
    this.wurflResources = wURFLResources;
  }

  public GeneralWURFLEngine(WURFLResource paramWURFLResource, WURFLResource... paramVarArgs) {
    this(paramWURFLResource, new WURFLResources(paramVarArgs));
  }

  public GeneralWURFLEngine(WURFLResource paramWURFLResource, WURFLResources paramWURFLResources) {
    Validate.notNull(paramWURFLResource, "The root resource is null");
    this.wurflResource = paramWURFLResource;
    this.wurflResourcePath = paramWURFLResource.getOriginalPath();
    this.wurflResources = paramWURFLResources;
  }

  public void reload(String paramString) {
    WURFLResource wURFLResource = loadWURFLResource(paramString);
    reload(wURFLResource, (WURFLResources)null);
  }

  public void applyPatches(String... paramVarArgs) {
    if (paramVarArgs == null) {
      this.logger.warn("null patches, do nothing...");
      return;
    }
    applyPatches(loadWURFLResource(paramVarArgs));
  }

  public void applyPatches(WURFLResource... paramVarArgs) {
    applyPatches(new WURFLResources(paramVarArgs));
  }

  public void applyPatches(WURFLResources paramWURFLResources) {
    if (paramWURFLResources == null) {
      this.logger.warn("null patches, do nothing...");
      return;
    }
    this.reentrantReadWriteLock.writeLock().lock();
    try {
      loadWURFLResource();
      this.l.getDeviceForRequest(paramWURFLResources, this.c);
    } finally {
      this.reentrantReadWriteLock.writeLock().unlock();
    }
  }

  public void reload(String paramString, String[] paramArrayOfString) {
    WURFLResource wURFLResource = loadWURFLResource(paramString);
    WURFLResources wURFLResources = loadWURFLResource(paramArrayOfString);
    reload(wURFLResource, wURFLResources);
  }

  public void reload(WURFLResource paramWURFLResource, WURFLResource... paramVarArgs) {
    reload(paramWURFLResource, new WURFLResources(paramVarArgs));
  }

  public void reload(WURFLResource paramWURFLResource, WURFLResources paramWURFLResources) {
    this.wurflResourcePath = paramWURFLResource.getOriginalPath();
    loadWURFLResource();
    if (paramWURFLResources == null)
      paramWURFLResources = new WURFLResources();
    this.reentrantReadWriteLock.writeLock().lock();
    try {
      this.l.getDeviceForRequest(paramWURFLResource, paramWURFLResources, this.c);
    } finally {
      this.reentrantReadWriteLock.writeLock().unlock();
    }
  }

  public boolean replaceRoot(String paramString) {
    try {
      GeneralWURFLEngine generalWURFLEngine;
      if (StringUtils.isBlank(paramString)) {
        this.logger.warn("Empty value has been provided for replacing root, skipping");
        return false;
      }
      if (!(new File(this.wurflResourcePath)).canWrite()) {
        this.logger.error("Engine root at {} is not writable, cannot replace it", this.wurflResourcePath);
        return false;
      }
      if (this.wurflResources == null || this.wurflResources.size() == 0) {
        generalWURFLEngine = new GeneralWURFLEngine(paramString);
      } else {
        generalWURFLEngine = new GeneralWURFLEngine(new XMLResource(paramString), this.wurflResources);
      }
      generalWURFLEngine.load();
      FileUtils.copyFile(new File(paramString), new File(this.wurflResourcePath), true);
      reload(this.wurflResourcePath);
      return true;
    } catch (Exception exception) {
      this.logger.error("An error has occurred replacing {} root with {}", this.wurflResourcePath, paramString, exception);
      return false;
    }
  }

  public void setMarkupResolver(MarkupResolver paramMarkupResolver) {
    this.markupResolver = paramMarkupResolver;
  }

  public void setCapabilitiesHolderFactory(CapabilitiesHolderFactory paramCapabilitiesHolderFactory) {
    this.capabilitiesHolderFactory = paramCapabilitiesHolderFactory;
  }

  public void setWurflRequestFactory(WURFLRequestFactory paramWURFLRequestFactory) {
    if (!(paramWURFLRequestFactory instanceof WURFLRequestFactoryWithPriority))
      throw new UnsupportedOperationException("User-Agent priority is not supported if the custom request factory does not implement WURFLRequestFactoryWithPriority");
    synchronized (lock) {
      this.wurflRequestFactoryWithPriority = (WURFLRequestFactoryWithPriority)paramWURFLRequestFactory;
      this.userAgentPriority = this.wurflRequestFactoryWithPriority.getUserAgentPriority();
      if (this.l != null)
        this.l.getDeviceForRequest(this.wurflRequestFactoryWithPriority);
    }
  }

  public void setUserAgentResolver(UserAgentResolver paramUserAgentResolver) {
    this.userAgentResolver = paramUserAgentResolver;
  }

  public void setDeviceProvider(DeviceProvider paramDeviceProvider) {
    this.deviceProvider = paramDeviceProvider;
  }

  public void setCacheProvider(CacheProvider paramCacheProvider) {
    this.cacheProvider = paramCacheProvider;
  }

  public final WURFLUtils getWURFLUtils() {
    loadWURFLResource();
    this.reentrantReadWriteLock.readLock().lock();
    WURFLUtils wURFLUtils = this.wurflUtils;
    this.reentrantReadWriteLock.readLock().unlock();
    return wURFLUtils;
  }

  public Device getDeviceById(String paramString) {
    return getWURFLUtils().getDeviceById(paramString);
  }

  public Device getDeviceById(String paramString, WURFLRequest paramWURFLRequest) {
    return getWURFLUtils().getDeviceById(paramString, paramWURFLRequest);
  }

  public Device getDeviceById(String paramString, HttpServletRequest paramHttpServletRequest) {
    loadWURFLResource();
    return getWURFLUtils().getDeviceById(paramString, (new DefaultWURFLRequestFactory(this.userAgentResolver, this.l.b())).createRequest(paramHttpServletRequest, this.l.getDeviceForRequest()));
  }

  public Set<String> getAllVirtualCapabilities() {
    loadWURFLResource();
    return new HashSet<>(VirtualCapabilityHandler.getAllVirtualCapabilities());
  }

  private void loadWURFLResource() {
    if (!this.init)
      synchronized (lock) {
        if (!this.init) {
          try {
            try {
              reentrantReadWriteLock.writeLock().lock();
              if (wurflModel == null)
                wurflModel = new DefaultWURFLModel(wurflResource, wurflResources, c);
              wurflResource = null;
              wurflResources = null;
              if (l == null) {
                MatcherManager matcherManager = new MatcherManager(wurflModel);
                if (markupResolver != null && logger.isInfoEnabled())
                  logger.info("markupResolver is custom: {}", markupResolver.getClass().getName());
                if (capabilitiesHolderFactory != null && logger.isInfoEnabled())
                  logger.info("capabilitiesHolderFactory is custom: {}", capabilitiesHolderFactory.getClass().getName());
                loadAfter();
                if (cacheProvider != null && logger.isInfoEnabled())
                  logger.info("cacheProvider is custom: {}", cacheProvider.getClass().getName());
                if (wurflRequestFactoryWithPriority == null) {
                  if (userAgentResolver != null) {
                    if (logger.isInfoEnabled())
                      logger.info("userAgentResolver is custom: {}", userAgentResolver.getClass().getName());
                    wurflRequestFactoryWithPriority = new DefaultWURFLRequestFactory(userAgentResolver, userAgentPriority);
                  } else {
                    wurflRequestFactoryWithPriority = new DefaultWURFLRequestFactory(userAgentPriority);
                  }
                } else if (logger.isInfoEnabled()) {
                  logger.info("wurflRequestFactory is custom: {}", wurflRequestFactoryWithPriority.getClass().getName());
                }
                if (engineTarget != null) {
                  l = new j(wurflModel, matcherManager, deviceProvider, wurflRequestFactoryWithPriority, engineTarget);
                } else {
                  l = new j(wurflModel, matcherManager, deviceProvider, wurflRequestFactoryWithPriority);
                }
              } else if (logger.isInfoEnabled()) {
                logger.info("wurflService is fed: {}", l.getClass().getName());
              }
              loadAfter();
              if (cacheProvider != null) {
                if (logger.isInfoEnabled())
                  logger.info("cacheProvider is fed: {}", cacheProvider.getClass().getName());
                l.getDeviceForRequest(cacheProvider);
              }
              if (wurflUtils == null)
                wurflUtils = new WURFLUtils(wurflModel, deviceProvider, l);
              VirtualCapabilityUserAgentTool.getInstance().assignProperties(wurflRequestFactoryWithPriority.createRequest("", engineTarget), deviceProvider.getInternalDevice("generic"));
              init = true;
            } finally {
              reentrantReadWriteLock.writeLock().unlock();
            }
          } catch (Exception exception) {
              this.logger.error("cannot initialize: {}", exception.getMessage(), exception);
            if (exception instanceof WURFLRuntimeException)
              throw (WURFLRuntimeException)exception;
            throw new WURFLRuntimeException(exception);
          }
          try {
            Class<?> clazz = Class.forName("com.scientiamobile.wurfl.core.CheckConnection");
            Object object = clazz.newInstance();
            clazz.getDeclaredMethod("setup", WURFLEngine.class, WURFLModel.class).invoke(object, this, wurflModel);
            clazz.getDeclaredMethod("check").invoke(object);
          } catch (Exception ignored) {}
        }
      }
  }

  private void loadAfter() {
    if (this.capabilitiesHolderFactory == null)
      this.capabilitiesHolderFactory = new CapabilitiesHolderFactoryImpl(this.wurflModel);
    if (this.deviceProvider == null) {
      if (this.markupResolver != null) {
        this.deviceProvider = new DeviceProviderImpl(this.wurflModel, this.capabilitiesHolderFactory, this.markupResolver);
      }else {
        this.deviceProvider = new DeviceProviderImpl(this.wurflModel, this.capabilitiesHolderFactory);
      }
    }
    if (this.logger.isInfoEnabled())
        this.logger.info("Device Provider is fed: {}", this.deviceProvider.getClass().getName());
  }

  public Device getDeviceForRequest(HttpServletRequest paramHttpServletRequest) {
    loadWURFLResource();
    return this.l.getDeviceForRequest(paramHttpServletRequest);
  }

  public Device getDeviceForRequest(WURFLRequest paramWURFLRequest) {
    loadWURFLResource();
    return this.l.getDeviceForRequest(paramWURFLRequest);
  }

  public Device getDeviceForRequest(String paramString) {
    loadWURFLResource();
    return this.l.getDeviceForRequest(paramString);
  }

  public void load() {
    loadWURFLResource();
  }

  public EngineTarget getEngineTarget() {
    synchronized (lock) {
      if (this.l != null)
        this.engineTarget = this.l.getDeviceForRequest();
      return this.engineTarget;
    }
  }

  public void setEngineTarget(EngineTarget paramEngineTarget) {
    synchronized (lock) {
      this.engineTarget = paramEngineTarget;
      if (this.l != null)
        this.l.getDeviceForRequest(paramEngineTarget);
    }
  }

  public UserAgentPriority getUserAgentPriority() {
    synchronized (lock) {
      if (this.l != null) {
        this.userAgentPriority = this.l.b();
      } else if (this.wurflRequestFactoryWithPriority != null) {
        this.userAgentPriority = this.wurflRequestFactoryWithPriority.getUserAgentPriority();
      }
      return this.userAgentPriority;
    }
  }

  public void setUserAgentPriority(UserAgentPriority paramUserAgentPriority) {
    synchronized (lock) {
      this.userAgentPriority = paramUserAgentPriority;
      if (this.l != null) {
        this.l.getDeviceForRequest(paramUserAgentPriority);
      } else if (this.wurflRequestFactoryWithPriority != null) {
        this.wurflRequestFactoryWithPriority.setUserAgentPriority(paramUserAgentPriority);
      }
    }
  }

  public final void setCapabilityFilter(String... paramVarArgs) {
    ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(paramVarArgs));
    for (String str : b) {
      if (!arrayList.contains(str))
        arrayList.add(str);
    }
    this.c = arrayList.toArray(new String[0]);
  }

  public final void setCapabilityFilter(Collection<String> paramCollection) {
    if (paramCollection != null) {
      paramCollection = new ArrayList<>(paramCollection);
      for (String str : b) {
        if (!paramCollection.contains(str))
          paramCollection.add(str);
      }
      this.c = paramCollection.toArray(new String[0]);
    }
  }

  private static WURFLResource loadWURFLResource(String paramString) {
    Validate.notEmpty(paramString, "The path is null");
    return new XMLResource(paramString);
  }

  private static WURFLResources loadWURFLResource(String[] paramArrayOfString) {
    WURFLResources wURFLResources = new WURFLResources();
    for (byte b = 0; paramArrayOfString != null && b < paramArrayOfString.length; b++)
      wURFLResources.add(new XMLResource(paramArrayOfString[b]));
    return wURFLResources;
  }

  public String getAPIVersion() {
    return "1.9.0.0";
  }

  public Set<String> getAllMandatoryCapabilities() {
    loadWURFLResource();
    return new HashSet<>(Arrays.asList(VirtualCapabilityEvaluator.MANDATORY_CAPABILITIES));
  }

  public Set<String> getAllCapabilities() {
    loadWURFLResource();
    HashSet<String> hashSet = new HashSet<>();
      for (String string : this.wurflModel.getAllCapabilities()) {
          if (!string.startsWith("controlcap_"))
              hashSet.add(string);
      }
    return hashSet;
  }

  public String getRootPath() {
    return this.wurflResourcePath;
  }

  public String getApiVersion() {
    return "1.9.0.0";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\GeneralWURFLEngine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
