package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.exc.MandatoryCapabilityMissing;
import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.request.DefaultWURFLRequest;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.ModelDeviceWithAncestorId;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.vcap.VirtualCapabilityHandler;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.slf4j.LoggerFactory;

class DefaultDeviceProvider implements DeviceProvider {
   private final MarkupResolver markupResolver;
   private final CapabilitiesHolderFactory capabilitiesHolderFactory;
   private final WURFLModel wurflModel;
   private static boolean assertionsDisabled = !DefaultDeviceProvider.class.desiredAssertionStatus();

   public DefaultDeviceProvider(WURFLModel wurflModel, CapabilitiesHolderFactory capabilitiesHolderFactory, MarkupResolver markupResolver) {
      LoggerFactory.getLogger(DefaultDeviceProvider.class);
      if (!assertionsDisabled && wurflModel == null) {
         throw new AssertionError();
      } else {
         this.wurflModel = wurflModel;
         Validate.notNull(capabilitiesHolderFactory, "capabilitiesHolderFactory must be not null.");
         Validate.notNull(markupResolver, "markupResolver must be not null.");
         this.markupResolver = markupResolver;
         this.capabilitiesHolderFactory = capabilitiesHolderFactory;
         Set modelCapabilities = this.capabilitiesHolderFactory.getModelCapabilities();
         Set mandatoryCapabilities = VirtualCapabilityHandler.getMandatoryCapabilities();
         StringBuilder missingCapabilities = new StringBuilder();

         for(Object capabilityObj : mandatoryCapabilities) {
            String capability = (String)capabilityObj;
            if (!modelCapabilities.contains(capability)) {
               missingCapabilities.append(capability).append(", ");
            }
         }

         if (missingCapabilities.length() > 0) {
            throw new MandatoryCapabilityMissing(missingCapabilities.substring(0, missingCapabilities.length() - 2));
         }
      }
   }

   public DefaultDeviceProvider(WURFLModel wurflModel, CapabilitiesHolderFactory capabilitiesHolderFactory) {
      this(wurflModel, capabilitiesHolderFactory, new MarkupResolverImpl());
   }

   public InternalDevice getInternalDevice(String deviceId) {
      Validate.notNull(deviceId, "The deviceId must be not null");
      ModelDeviceWithAncestorId deviceWithAncestorId = this.getModelDeviceWithAncestorId(deviceId);
      if (!assertionsDisabled && deviceWithAncestorId.getModelDevice() == null) {
         throw new AssertionError("modelDevice is null");
      } else {
         ModelDevice modelDevice = deviceWithAncestorId.getModelDevice();
         CapabilitiesHolder capabilitiesHolder = this.capabilitiesHolderFactory.create(modelDevice);
         return new InternalDeviceImpl(deviceWithAncestorId.getModelDevice(), deviceWithAncestorId.getAncestorId(), capabilitiesHolder);
      }
   }

   public Device buildDevice(InternalDevice internalDevice, String userAgent, MatchType matchType, String matcherName, String bucketMatcherName) {
      return this.buildDevice(internalDevice, (WURFLRequest)(new DefaultWURFLRequest(userAgent, (UserAgentNormalizer)null, UserAgentPriority.OverrideSideloadedBrowserUserAgent, EngineTarget.fastDesktopBrowserMatch)), matchType, matcherName, bucketMatcherName);
   }

   public Device buildDevice(InternalDevice internalDevice, WURFLRequest request, MatchType matchType, String matcherName, String bucketMatcherName) {
      Validate.notNull(internalDevice, "The internal device must be not null");
      String deviceId;
      Validate.notEmpty(deviceId = internalDevice.getId(), "The id must be not null String");
      ModelDeviceWithAncestorId deviceWithAncestorId = this.getModelDeviceWithAncestorId(deviceId);
      if (!assertionsDisabled && deviceWithAncestorId.getModelDevice() == null) {
         throw new AssertionError("modelDevice is null");
      } else {
         return new DefaultDevice(internalDevice, this.markupResolver, matchType, matcherName, bucketMatcherName, request.getNormalizedDeviceUserAgent(), new VirtualCapabilityHandler(request));
      }
   }

   private ModelDeviceWithAncestorId getModelDeviceWithAncestorId(String deviceId) {
      ModelDevice modelDevice = this.wurflModel.getDeviceById(deviceId);
      String ancestorId = this.wurflModel.getDeviceAncestor(modelDevice).getID();
      return new ModelDeviceWithAncestorId(modelDevice, ancestorId);
   }
}
