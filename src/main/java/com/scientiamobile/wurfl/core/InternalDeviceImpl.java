package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import java.io.Serializable;
import java.util.Locale;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

class InternalDeviceImpl implements InternalDevice, Serializable {
   private static final long serialVersionUID = 101L;
   private final String id;
   private final String wurflUserAgent;
   private final boolean actualDeviceRoot;
   private final String deviceRootId;
   private ModelDevice ancestorModelDevice;
   private final CapabilitiesHolder capabilitiesHolder;

   protected InternalDeviceImpl(ModelDevice modelDevice, String ancestorId, CapabilitiesHolder capabilitiesHolder) {
      this(modelDevice.getID(), modelDevice.getUserAgent(), modelDevice.isActualDeviceRoot(), ancestorId, capabilitiesHolder);
      this.ancestorModelDevice = modelDevice.getAncestor();
   }

   private InternalDeviceImpl(String id, String wurflUserAgent, boolean actualDeviceRoot, String deviceRootId, CapabilitiesHolder capabilitiesHolder) {
      Validate.notNull(capabilitiesHolder, "The capabilitiesHolder must be not null");
      this.id = id;
      this.wurflUserAgent = wurflUserAgent;
      this.actualDeviceRoot = actualDeviceRoot;
      this.deviceRootId = deviceRootId;
      this.capabilitiesHolder = capabilitiesHolder;
   }

   public String getId() {
      return this.id;
   }

   public String getWURFLUserAgent() {
      return this.wurflUserAgent;
   }

   public String getCapability(String capabilityName) {
      return this.capabilitiesHolder.getCapability(capabilityName);
   }

   final ModelDevice getAncestorModelDevice() {
      return this.ancestorModelDevice;
   }

   public int getCapabilityAsInt(String capabilityName) {
      return this.capabilitiesHolder.getCapabilityAsInt(capabilityName);
   }

   public boolean getCapabilityAsBool(String capabilityName) {
      String originalCapabilityName = capabilityName;
      if ((capabilityName = this.capabilitiesHolder.getCapability(capabilityName)) != null && capabilityName.toLowerCase(Locale.ENGLISH).equals("true")) {
         return true;
      } else if (capabilityName != null && capabilityName.toLowerCase(Locale.ENGLISH).equals("false")) {
         return false;
      } else {
         throw new NumberFormatException("WURFL invalid capability value: " + originalCapabilityName + " expected \"true\" or \"false\", received: \"" + capabilityName + "\"");
      }
   }

   @SuppressWarnings("unchecked")
   public Map<String, String> getCapabilities() {
      Map<String, String> allCapabilities = (Map<String, String>)this.capabilitiesHolder.getCapabilities();
      HashMap<String, String> filteredCapabilities = new HashMap<>(allCapabilities.size());
      Iterator<String> it = allCapabilities.keySet().iterator();

      while(it.hasNext()) {
         String capabilityName;
         if (!(capabilityName = it.next()).startsWith("controlcap_")) {
            filteredCapabilities.put(capabilityName, allCapabilities.get(capabilityName));
         }
      }

      return filteredCapabilities;
   }

   public boolean isActualDeviceRoot() {
      return this.actualDeviceRoot;
   }

   public String getDeviceRootId() {
      String rootId = this.deviceRootId;
      if (this.deviceRootId.equals("generic")) {
         rootId = "";
      }

      return rootId;
   }

   public boolean equals(Object other) {
      EqualsBuilder eb;
      (eb = new EqualsBuilder()).appendSuper(this.getClass().isInstance(other));
      if (eb.isEquals()) {
         eb.append(this.id, ((InternalDeviceImpl)other).id);
      }

      return eb.isEquals();
   }

   public int hashCode() {
      return (new HashCodeBuilder(63, 89)).append(this.getClass()).append(this.id).toHashCode();
   }

   public String toString() {
      return "[" + this.id + ", match=, matcher=]";
   }
}
