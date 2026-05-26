package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.WURFLParsingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

final class WurflXmlHandler extends DefaultHandler {
   private int parseState;
   private String currentUserAgent;
   private String currentDeviceId;
   private String currentFallback;
   private boolean currentActualDeviceRoot;
   private String currentGroupId;
   private String currentCapabilityName;
   private String currentCapabilityValue;
   private Set<String> seenUserAgents;
   private Set<String> seenDeviceIds;
   private Map<String, String> currentCapabilities;
   private Map<String, String> currentCapabilitiesByGroup;
   private ModelDevices devices;
   private final Map<String, ModelDevice> actualDeviceRootsById;
   private String wurflVersion;
   private String wurflLastUpdated;
   private String wurflSmid;
   private boolean patch;
   private Set<String> includedCapabilities;

   WurflXmlHandler(Set<String> includedCapabilities) {
      this.actualDeviceRootsById = new HashMap<>();
      this.patch = false;
      this.includedCapabilities = includedCapabilities;
      this.parseState = WurflXmlParseState.a;
   }

   public final void startDocument() {
      this.seenUserAgents = new HashSet<>();
      this.seenDeviceIds = new HashSet<>();
      this.devices = new ModelDevices();
   }

   public final void endDocument() {
   }

   public final void startElement(String var1, String var2, String var3, Attributes var4) {
      if (var3.equals("capability") && this.parseState != WurflXmlParseState.i) {
         var3 = var4.getValue("name");
         throw new WURFLParsingException("Capability '" + var3 + "'  does not belong to any group");
      } else {
         switch (WurflXmlParseStateSwitch.a[this.parseState - 1]) {
            case 1:
               this.patch = "wurfl_patch".equals(var3);
               if ("wurfl".equals(var3) || this.patch) {
                  this.parseState = WurflXmlParseState.b;
                  return;
               }
               break;
            case 2:
               if ("version".equals(var3)) {
                  this.parseState = WurflXmlParseState.c;
                  return;
               }

               if ("devices".equals(var3)) {
                  this.parseState = WurflXmlParseState.g;
                  return;
               }
               break;
            case 3:
               if ("ver".equals(var3)) {
                  this.parseState = WurflXmlParseState.d;
                  return;
               }

               if ("last_updated".equals(var3)) {
                  this.parseState = WurflXmlParseState.e;
                  return;
               }

               if ("smid".equals(var3)) {
                  this.parseState = WurflXmlParseState.f;
                  return;
               }
               break;
            case 4:
               if ("device".equals(var3)) {
                  this.parseState = WurflXmlParseState.h;
                  this.currentUserAgent = var4.getValue("user_agent");
                  this.currentDeviceId = var4.getValue("id");
                  this.currentFallback = var4.getValue("fall_back");
                  this.currentActualDeviceRoot = Boolean.valueOf(var4.getValue("actual_device_root"));
                  if (StringUtils.isEmpty(this.currentDeviceId)) {
                     throw new WURFLParsingException("device id is not a valid");
                  }

                  if (!"generic".equals(this.currentDeviceId) && StringUtils.isEmpty(this.currentUserAgent)) {
                     StringBuilder var8;
                     (var8 = new StringBuilder()).append("Device with id ").append(this.currentDeviceId).append(" has an invalid user agent");
                     throw new WURFLParsingException(var8.toString());
                  }

                  if (this.seenDeviceIds.contains(this.currentDeviceId)) {
                     throw new WURFLParsingException("device id " + this.currentDeviceId + " already defined!!!");
                  }

                  if (this.seenUserAgents.contains(this.currentUserAgent)) {
                     throw new WURFLParsingException("user agent [" + this.currentUserAgent + "] already defined");
                  }

                  this.seenUserAgents.add(this.currentUserAgent);
                  this.seenDeviceIds.add(this.currentDeviceId);
                  this.currentCapabilities = new HashMap<>();
                  this.currentCapabilitiesByGroup = new HashMap<>();
                  return;
               }
               break;
            case 5:
               if ("group".equals(var3)) {
                  this.parseState = WurflXmlParseState.i;
                  this.currentGroupId = var4.getValue("id").intern();
                  return;
               }
               break;
            case 6:
               if ("capability".equals(var3)) {
                  this.parseState = WurflXmlParseState.j;
                  if (!"virtual_capabilities".equals(this.currentGroupId)) {
                     this.currentCapabilityName = var4.getValue("name");
                     if (this.includedCapabilities.isEmpty() || this.includedCapabilities.contains(this.currentCapabilityName) || this.currentCapabilityName.startsWith("controlcap_")) {
                        this.currentCapabilityValue = var4.getValue("value");
                        if (StringUtils.isEmpty(this.currentCapabilityName) || this.currentCapabilityValue == null) {
                           throw new WURFLParsingException("device with id " + this.currentDeviceId + " has capability with name or value not valid");
                        }

                        if (this.currentCapabilities.containsKey(this.currentCapabilityName)) {
                           throw new WURFLParsingException("The device with id " + this.currentDeviceId + " defines capability " + this.currentCapabilityName + "more than once");
                        }

                        String var5 = this.currentCapabilityName.intern();
                        if (!"experimental".equals(this.currentGroupId)) {
                           if (StringUtils.isNotEmpty(var3 = this.currentCapabilityValue) && var3.length() > 255) {
                              var3 = var3.substring(0, 255);
                           }

                           this.currentCapabilityValue = var3;
                        }

                        this.currentCapabilities.put(var5, this.currentCapabilityValue);
                        this.currentCapabilitiesByGroup.put(var5, this.currentGroupId);
                     }
                  }
               }
         }

      }
   }

   public final void endElement(String var1, String var2, String var3) {
      switch (WurflXmlParseStateSwitch.a[this.parseState - 1]) {
         case 2:
            if ("wurfl".equals(var3) || "wurfl_patch".equals(var3)) {
               this.parseState = WurflXmlParseState.k;
               return;
            }
            break;
         case 3:
            if ("version".equals(var3)) {
               this.parseState = WurflXmlParseState.b;
               return;
            }
            break;
         case 4:
            if ("devices".equals(var3)) {
               this.parseState = WurflXmlParseState.b;
               return;
            }
            break;
         case 5:
            if ("device".equals(var3)) {
               ModelDevice var4 = (new ModelDevice$Builder(this.currentDeviceId, this.currentUserAgent, this.currentFallback)).setActualDeviceRoot(this.currentActualDeviceRoot).setCapabilities(this.currentCapabilities).setCapabilitiesByGroup(this.currentCapabilitiesByGroup).build();
               this.devices.add(var4);
               if (var4.isActualDeviceRoot()) {
                  this.actualDeviceRootsById.put(this.currentDeviceId, var4);
               }

               this.parseState = WurflXmlParseState.g;
               return;
            }
            break;
         case 6:
            if ("group".equals(var3)) {
               this.parseState = WurflXmlParseState.h;
               return;
            }
            break;
         case 7:
            if ("ver".equals(var3)) {
               this.parseState = WurflXmlParseState.c;
               return;
            }
            break;
         case 8:
            if ("last_updated".equals(var3)) {
               this.parseState = WurflXmlParseState.c;
               return;
            }
            break;
         case 9:
            if ("smid".equals(var3)) {
               this.parseState = WurflXmlParseState.c;
               return;
            }
            break;
         case 10:
            if ("capability".equals(var3)) {
               this.parseState = WurflXmlParseState.i;
            }
      }

   }

   public final void characters(char[] var1, int var2, int var3) {
      switch (WurflXmlParseStateSwitch.a[this.parseState - 1]) {
         case 7:
            this.wurflVersion = (new StringBuilder()).append(var1, var2, var3).toString();
            return;
         case 8:
            this.wurflLastUpdated = (new StringBuilder()).append(var1, var2, var3).toString();
            return;
         case 9:
            this.wurflSmid = (new StringBuilder()).append(var1, var2, var3).toString();
         default:
      }
   }

   String getWurflVersion() {
      return this.wurflVersion;
   }

   String getWurflLastUpdated() {
      return this.wurflLastUpdated;
   }

   String getWurflSmid() {
      return this.wurflSmid;
   }

   boolean isPatch() {
      return this.patch;
   }

   ModelDevices getDevices() {
      return this.devices;
   }
}
