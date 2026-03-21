package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.exc.MandatoryCapabilityMissing;
import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.request.DefaultWURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.ModelDeviceWithAncestorId;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.vcap.VirtualCapabilityHandler;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.slf4j.LoggerFactory;

class g implements DeviceProvider {
  private final MarkupResolver a;
  
  private final CapabilitiesHolderFactory b;
  
  private final WURFLModel c;
  
  public g(WURFLModel paramWURFLModel, CapabilitiesHolderFactory paramCapabilitiesHolderFactory, MarkupResolver paramMarkupResolver) {
    LoggerFactory.getLogger(g.class);
    if (!d && paramWURFLModel == null)
      throw new AssertionError(); 
    this.c = paramWURFLModel;
    Validate.notNull(paramCapabilitiesHolderFactory, "capabilitiesHolderFactory must be not null.");
    Validate.notNull(paramMarkupResolver, "markupResolver must be not null.");
    this.a = paramMarkupResolver;
    this.b = paramCapabilitiesHolderFactory;
    Set set1 = this.b.getModelCapabilities();
    Set set2 = VirtualCapabilityHandler.getMandatoryCapabilities();
    StringBuilder stringBuilder = new StringBuilder();
    for (String str : set2) {
      if (!set1.contains(str))
        stringBuilder.append(str).append(", "); 
    } 
    if (stringBuilder.length() > 0)
      throw new MandatoryCapabilityMissing(stringBuilder.substring(0, stringBuilder.length() - 2)); 
  }
  
  public g(WURFLModel paramWURFLModel, CapabilitiesHolderFactory paramCapabilitiesHolderFactory) {
    this(paramWURFLModel, paramCapabilitiesHolderFactory, new i());
  }
  
  public InternalDevice getInternalDevice(String paramString) {
    Validate.notNull(paramString, "The deviceId must be not null");
    ModelDeviceWithAncestorId modelDeviceWithAncestorId = a(paramString);
    if (!d && modelDeviceWithAncestorId.getModelDevice() == null)
      throw new AssertionError("modelDevice is null"); 
    ModelDevice modelDevice = modelDeviceWithAncestorId.getModelDevice();
    a a = this.b.create(modelDevice);
    return new h(modelDeviceWithAncestorId.getModelDevice(), modelDeviceWithAncestorId.getAncestorId(), a);
  }
  
  public Device buildDevice(InternalDevice paramInternalDevice, String paramString1, MatchType paramMatchType, String paramString2, String paramString3) {
    return buildDevice(paramInternalDevice, (WURFLRequest)new DefaultWURFLRequest(paramString1, null, UserAgentPriority.OverrideSideloadedBrowserUserAgent, EngineTarget.fastDesktopBrowserMatch), paramMatchType, paramString2, paramString3);
  }
  
  public Device buildDevice(InternalDevice paramInternalDevice, WURFLRequest paramWURFLRequest, MatchType paramMatchType, String paramString1, String paramString2) {
    Validate.notNull(paramInternalDevice, "The internal device must be not null");
    String str;
    Validate.notEmpty(str = paramInternalDevice.getId(), "The id must be not null String");
    ModelDeviceWithAncestorId modelDeviceWithAncestorId = a(str);
    if (!d && modelDeviceWithAncestorId.getModelDevice() == null)
      throw new AssertionError("modelDevice is null"); 
    return new DefaultDevice(paramInternalDevice, this.a, paramMatchType, paramString1, paramString2, paramWURFLRequest.getNormalizedDeviceUserAgent(), new VirtualCapabilityHandler(paramWURFLRequest));
  }
  
  private final ModelDeviceWithAncestorId a(String paramString) {
    ModelDevice modelDevice = this.c.getDeviceById(paramString);
    String str = this.c.getDeviceAncestor(modelDevice).getID();
    return new ModelDeviceWithAncestorId(modelDevice, str);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\g.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
