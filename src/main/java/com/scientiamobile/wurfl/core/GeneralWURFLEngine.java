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
  private final transient Logger a = LoggerFactory.getLogger(getClass());
  
  private static final List b = Arrays.asList(new String[] { 
        "device_os", "device_os_version", "is_tablet", "is_wireless_device", "pointing_method", "preferred_markup", "resolution_height", "resolution_width", "ux_full_desktop", "xhtml_support_level", 
        "is_smarttv", "can_assign_phone_number", "brand_name", "model_name", "marketing_name", "mobile_browser_version" });
  
  private String[] c = null;
  
  private final ReadWriteLock d = new ReentrantReadWriteLock();
  
  private WURFLResource e = null;
  
  private WURFLResources f = null;
  
  private String g = null;
  
  private MarkupResolver h;
  
  private CapabilitiesHolderFactory i;
  
  private DeviceProvider j;
  
  private CacheProvider k;
  
  private m l;
  
  private UserAgentResolver m;
  
  private WURFLUtils n;
  
  private WURFLModel o;
  
  private Boolean p = Boolean.valueOf(false);
  
  private WURFLRequestFactoryWithPriority q = null;
  
  private EngineTarget r = null;
  
  private UserAgentPriority s = UserAgentPriority.OverrideSideloadedBrowserUserAgent;
  
  public GeneralWURFLEngine(String paramString) {
    this((WURFLResource)new XMLResource(paramString));
    this.g = paramString;
  }
  
  public GeneralWURFLEngine(WURFLResource paramWURFLResource) {
    this(paramWURFLResource, (WURFLResources)null);
  }
  
  public GeneralWURFLEngine(String paramString, String... paramVarArgs) {
    WURFLResource wURFLResource = a(paramString);
    WURFLResources wURFLResources = a(paramVarArgs);
    Validate.notNull(wURFLResource, "The root resource is null");
    this.e = wURFLResource;
    this.g = paramString;
    this.f = wURFLResources;
  }
  
  public GeneralWURFLEngine(WURFLResource paramWURFLResource, WURFLResource... paramVarArgs) {
    this(paramWURFLResource, new WURFLResources(paramVarArgs));
  }
  
  public GeneralWURFLEngine(WURFLResource paramWURFLResource, WURFLResources paramWURFLResources) {
    Validate.notNull(paramWURFLResource, "The root resource is null");
    this.e = paramWURFLResource;
    if (paramWURFLResource != null)
      this.g = paramWURFLResource.getOriginalPath(); 
    this.f = paramWURFLResources;
  }
  
  public void reload(String paramString) {
    WURFLResource wURFLResource = a(paramString);
    reload(wURFLResource, (WURFLResources)null);
  }
  
  public void applyPatches(String... paramVarArgs) {
    if (paramVarArgs == null) {
      this.a.warn("null patches, do nothing...");
      return;
    } 
    applyPatches(a(paramVarArgs));
  }
  
  public void applyPatches(WURFLResource... paramVarArgs) {
    applyPatches(new WURFLResources(paramVarArgs));
  }
  
  public void applyPatches(WURFLResources paramWURFLResources) {
    if (paramWURFLResources == null) {
      this.a.warn("null patches, do nothing...");
      return;
    } 
    this.d.writeLock().lock();
    try {
      a();
      this.l.a(paramWURFLResources, this.c);
      return;
    } finally {
      this.d.writeLock().unlock();
    } 
  }
  
  public void reload(String paramString, String[] paramArrayOfString) {
    WURFLResource wURFLResource = a(paramString);
    WURFLResources wURFLResources = a(paramArrayOfString);
    reload(wURFLResource, wURFLResources);
  }
  
  public void reload(WURFLResource paramWURFLResource, WURFLResource... paramVarArgs) {
    reload(paramWURFLResource, new WURFLResources(paramVarArgs));
  }
  
  public void reload(WURFLResource paramWURFLResource, WURFLResources paramWURFLResources) {
    this.g = paramWURFLResource.getOriginalPath();
    a();
    if ((paramWURFLResources = paramWURFLResources) == null)
      paramWURFLResources = new WURFLResources(); 
    this.d.writeLock().lock();
    try {
      this.l.a(paramWURFLResource, paramWURFLResources, this.c);
      return;
    } finally {
      this.d.writeLock().unlock();
    } 
  }
  
  public boolean replaceRoot(String paramString) {
    try {
      GeneralWURFLEngine generalWURFLEngine;
      if (StringUtils.isBlank(paramString)) {
        this.a.warn("Empty value has been provided for replacing root, skipping");
        return false;
      } 
      if (!(new File(this.g)).canWrite()) {
        this.a.error("Engine root at " + this.g + "is not writable, cannot replace it");
        return false;
      } 
      if (this.f == null || this.f.size() == 0) {
        generalWURFLEngine = new GeneralWURFLEngine(paramString);
      } else {
        generalWURFLEngine = new GeneralWURFLEngine((WURFLResource)new XMLResource(paramString), this.f);
      } 
      generalWURFLEngine.load();
      FileUtils.copyFile(new File(paramString), new File(this.g), true);
      reload(this.g);
      return true;
    } catch (Exception exception) {
      this.a.error("An error has occurred replacing " + this.g + "root with " + paramString, exception);
      return false;
    } 
  }
  
  public void setMarkupResolver(MarkupResolver paramMarkupResolver) {
    this.h = paramMarkupResolver;
  }
  
  public void setCapabilitiesHolderFactory(CapabilitiesHolderFactory paramCapabilitiesHolderFactory) {
    this.i = paramCapabilitiesHolderFactory;
  }
  
  public void setWurflRequestFactory(WURFLRequestFactory paramWURFLRequestFactory) {
    if (!(paramWURFLRequestFactory instanceof WURFLRequestFactoryWithPriority))
      throw new UnsupportedOperationException("User-Agent priority is not supported if the custom request factory does not implement WURFLRequestFactoryWithPriority"); 
    synchronized (this.p) {
      this.q = (WURFLRequestFactoryWithPriority)paramWURFLRequestFactory;
      this.s = this.q.getUserAgentPriority();
      if (this.l != null)
        this.l.a(this.q); 
      return;
    } 
  }
  
  public void setUserAgentResolver(UserAgentResolver paramUserAgentResolver) {
    this.m = paramUserAgentResolver;
  }
  
  public void setDeviceProvider(DeviceProvider paramDeviceProvider) {
    this.j = paramDeviceProvider;
  }
  
  public void setCacheProvider(CacheProvider paramCacheProvider) {
    this.k = paramCacheProvider;
  }
  
  public final WURFLUtils getWURFLUtils() {
    a();
    this.d.readLock().lock();
    WURFLUtils wURFLUtils = this.n;
    this.d.readLock().unlock();
    return wURFLUtils;
  }
  
  public Device getDeviceById(String paramString) {
    return getWURFLUtils().getDeviceById(paramString);
  }
  
  public Device getDeviceById(String paramString, WURFLRequest paramWURFLRequest) {
    return getWURFLUtils().getDeviceById(paramString, paramWURFLRequest);
  }
  
  public Device getDeviceById(String paramString, HttpServletRequest paramHttpServletRequest) {
    a();
    return getWURFLUtils().getDeviceById(paramString, (new DefaultWURFLRequestFactory(this.m, this.l.b())).createRequest(paramHttpServletRequest, this.l.a()));
  }
  
  public Set getAllVirtualCapabilities() {
    a();
    return new HashSet(VirtualCapabilityHandler.getAllVirtualCapabilities());
  }
  
  private void a() {
    if (!this.p.booleanValue())
      synchronized (this.p) {
        if (!this.p.booleanValue()) {
          try {
            GeneralWURFLEngine generalWURFLEngine1 = this;
            try {
              generalWURFLEngine1.d.writeLock().lock();
              if (generalWURFLEngine1.o == null)
                generalWURFLEngine1.o = (WURFLModel)new DefaultWURFLModel(generalWURFLEngine1.e, generalWURFLEngine1.f, generalWURFLEngine1.c); 
              generalWURFLEngine1.e = null;
              generalWURFLEngine1.f = null;
              if (generalWURFLEngine1.l == null) {
                MatcherManager matcherManager = new MatcherManager(generalWURFLEngine1.o);
                if (generalWURFLEngine1.h != null && generalWURFLEngine1.a.isInfoEnabled())
                  generalWURFLEngine1.a.info("markupResolver is custom: " + generalWURFLEngine1.h.getClass().getName()); 
                if (generalWURFLEngine1.i != null && generalWURFLEngine1.a.isInfoEnabled())
                  generalWURFLEngine1.a.info("capabilitiesHolderFactory is custom: " + generalWURFLEngine1.i.getClass().getName()); 
                generalWURFLEngine1.b();
                if (generalWURFLEngine1.k != null && generalWURFLEngine1.a.isInfoEnabled())
                  generalWURFLEngine1.a.info("cacheProvider is custom: " + generalWURFLEngine1.k.getClass().getName()); 
                if (generalWURFLEngine1.q == null) {
                  if (generalWURFLEngine1.m != null) {
                    if (generalWURFLEngine1.a.isInfoEnabled())
                      generalWURFLEngine1.a.info("userAgentResolver is custom: " + generalWURFLEngine1.m.getClass().getName()); 
                    generalWURFLEngine1.q = (WURFLRequestFactoryWithPriority)new DefaultWURFLRequestFactory(generalWURFLEngine1.m, generalWURFLEngine1.s);
                  } else {
                    generalWURFLEngine1.q = (WURFLRequestFactoryWithPriority)new DefaultWURFLRequestFactory(generalWURFLEngine1.s);
                  } 
                } else if (generalWURFLEngine1.a.isInfoEnabled()) {
                  generalWURFLEngine1.a.info("wurflRequestFactory is custom: " + generalWURFLEngine1.q.getClass().getName());
                } 
                if (generalWURFLEngine1.r != null) {
                  generalWURFLEngine1.l = new j(generalWURFLEngine1.o, matcherManager, generalWURFLEngine1.j, generalWURFLEngine1.q, generalWURFLEngine1.r);
                } else {
                  generalWURFLEngine1.l = new j(generalWURFLEngine1.o, matcherManager, generalWURFLEngine1.j, generalWURFLEngine1.q);
                } 
              } else if (generalWURFLEngine1.a.isInfoEnabled()) {
                generalWURFLEngine1.a.info("wurflService is fed: " + generalWURFLEngine1.l.getClass().getName());
              } 
              generalWURFLEngine1.b();
              if (generalWURFLEngine1.k != null) {
                if (generalWURFLEngine1.a.isInfoEnabled())
                  generalWURFLEngine1.a.info("cacheProvider is fed: " + generalWURFLEngine1.k.getClass().getName()); 
                generalWURFLEngine1.l.a(generalWURFLEngine1.k);
              } 
              if (generalWURFLEngine1.n == null)
                generalWURFLEngine1.n = new WURFLUtils(generalWURFLEngine1.o, generalWURFLEngine1.j, generalWURFLEngine1.l); 
              VirtualCapabilityUserAgentTool.getInstance().assignProperties(generalWURFLEngine1.q.createRequest("", generalWURFLEngine1.r), generalWURFLEngine1.j.getInternalDevice("generic"));
              generalWURFLEngine1.p = Boolean.valueOf(true);
            } finally {
              generalWURFLEngine1.d.writeLock().unlock();
            } 
          } catch (Exception exception) {
            this.a.error("cannot initialize: " + exception, exception);
            if (exception instanceof WURFLRuntimeException)
              throw (WURFLRuntimeException)exception; 
            throw new WURFLRuntimeException(exception);
          } 
          GeneralWURFLEngine generalWURFLEngine = this;
          try {
            Class<?> clazz;
            Object object = (clazz = Class.forName("com.scientiamobile.wurfl.core.CheckConnection")).newInstance();
            clazz.getDeclaredMethod("setup", new Class[] { WURFLEngine.class, WURFLModel.class }).invoke(object, new Object[] { generalWURFLEngine, generalWURFLEngine.o });
            clazz.getDeclaredMethod("check", new Class[0]).invoke(object, new Object[0]);
          } catch (Exception exception) {}
        } 
        return;
      }  
  }
  
  private void b() {
    if (this.i == null)
      this.i = new e(this.o); 
    if (this.j == null) {
      if (this.h != null) {
        this.j = new g(this.o, this.i, this.h);
        return;
      } 
      this.j = new g(this.o, this.i);
      return;
    } 
    if (this.a.isInfoEnabled())
      this.a.info("Device Provider is fed: " + this.j.getClass().getName()); 
  }
  
  public Device getDeviceForRequest(HttpServletRequest paramHttpServletRequest) {
    a();
    return this.l.a(paramHttpServletRequest);
  }
  
  public Device getDeviceForRequest(WURFLRequest paramWURFLRequest) {
    a();
    return this.l.a(paramWURFLRequest);
  }
  
  public Device getDeviceForRequest(String paramString) {
    a();
    return this.l.a(paramString);
  }
  
  public void load() {
    a();
  }
  
  public EngineTarget getEngineTarget() {
    synchronized (this.p) {
      if (this.l != null)
        this.r = this.l.a(); 
      return this.r;
    } 
  }
  
  public void setEngineTarget(EngineTarget paramEngineTarget) {
    synchronized (this.p) {
      this.r = paramEngineTarget;
      if (this.l != null)
        this.l.a(paramEngineTarget); 
      return;
    } 
  }
  
  public UserAgentPriority getUserAgentPriority() {
    synchronized (this.p) {
      if (this.l != null) {
        this.s = this.l.b();
      } else if (this.q != null) {
        this.s = this.q.getUserAgentPriority();
      } 
      return this.s;
    } 
  }
  
  public void setUserAgentPriority(UserAgentPriority paramUserAgentPriority) {
    synchronized (this.p) {
      this.s = paramUserAgentPriority;
      if (this.l != null) {
        this.l.a(paramUserAgentPriority);
      } else if (this.q != null) {
        this.q.setUserAgentPriority(paramUserAgentPriority);
      } 
      return;
    } 
  }
  
  public final void setCapabilityFilter(String... paramVarArgs) {
    ArrayList<String> arrayList = new ArrayList(Arrays.asList((Object[])paramVarArgs));
    for (String str : b) {
      if (!arrayList.contains(str))
        arrayList.add(str); 
    } 
    this.c = arrayList.<String>toArray(new String[arrayList.size()]);
  }
  
  public final void setCapabilityFilter(Collection<?> paramCollection) {
    if (paramCollection != null) {
      paramCollection = new ArrayList(paramCollection);
      for (String str : b) {
        if (!paramCollection.contains(str))
          paramCollection.add(str); 
      } 
      this.c = paramCollection.<String>toArray(new String[paramCollection.size()]);
    } 
  }
  
  private static WURFLResource a(String paramString) {
    Validate.notEmpty(paramString, "The path is null");
    return (WURFLResource)new XMLResource(paramString);
  }
  
  private static WURFLResources a(String[] paramArrayOfString) {
    WURFLResources wURFLResources = new WURFLResources();
    for (byte b = 0; paramArrayOfString != null && b < paramArrayOfString.length; b++)
      wURFLResources.add((WURFLResource)new XMLResource(paramArrayOfString[b])); 
    return wURFLResources;
  }
  
  public String getAPIVersion() {
    return "1.9.0.0";
  }
  
  public Set getAllMandatoryCapabilities() {
    a();
    return new HashSet(Arrays.asList((Object[])VirtualCapabilityEvaluator.MANDATORY_CAPABILITIES));
  }
  
  public Set getAllCapabilities() {
    a();
    HashSet<String> hashSet = new HashSet();
    Iterator<String> iterator = this.o.getAllCapabilities().iterator();
    while (iterator.hasNext()) {
      String str;
      if (!(str = iterator.next()).startsWith("controlcap_"))
        hashSet.add(str); 
    } 
    return hashSet;
  }
  
  public String getRootPath() {
    return this.g;
  }
  
  public String getApiVersion() {
    return "1.9.0.0";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\GeneralWURFLEngine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */