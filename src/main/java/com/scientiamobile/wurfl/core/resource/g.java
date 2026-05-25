package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.WURFLParsingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

final class WurflXmlHandler extends DefaultHandler {
   private int a;
   private String b;
   private String c;
   private String d;
   private boolean e;
   private String f;
   private String g;
   private String h;
   private Set i;
   private Set j;
   private Map k;
   private Map l;
   private ModelDevices m;
   private final Map n;
   private String o;
   private String p;
   private String q;
   private boolean r;
   private Set s;

   private WurflXmlHandler(Set var1) {
      this.n = new HashMap();
      this.r = false;
      this.s = var1;
      this.a = WurflXmlParseState.a;
   }

   public final void startDocument() {
      super.startDocument();
      this.i = new HashSet();
      this.j = new HashSet();
      this.m = new ModelDevices();
   }

   public final void endDocument() {
   }

   public final void startElement(String var1, String var2, String var3, Attributes var4) {
      if (var3.equals("capability") && this.a != WurflXmlParseState.i) {
         var3 = var4.getValue("name");
         throw new WURFLParsingException("Capability '" + var3 + "'  does not belong to any group");
      } else {
         switch (WurflXmlParseStateSwitch.a[this.a - 1]) {
            case 1:
               this.r = "wurfl_patch".equals(var3);
               if ("wurfl".equals(var3) || this.r) {
                  this.a = WurflXmlParseState.b;
                  return;
               }
               break;
            case 2:
               if ("version".equals(var3)) {
                  this.a = WurflXmlParseState.c;
                  return;
               }

               if ("devices".equals(var3)) {
                  this.a = WurflXmlParseState.g;
                  return;
               }
               break;
            case 3:
               if ("ver".equals(var3)) {
                  this.a = WurflXmlParseState.d;
                  return;
               }

               if ("last_updated".equals(var3)) {
                  this.a = WurflXmlParseState.e;
                  return;
               }

               if ("smid".equals(var3)) {
                  this.a = WurflXmlParseState.f;
                  return;
               }
               break;
            case 4:
               if ("device".equals(var3)) {
                  this.a = WurflXmlParseState.h;
                  this.b = var4.getValue("user_agent");
                  this.c = var4.getValue("id");
                  this.d = var4.getValue("fall_back");
                  this.e = Boolean.valueOf(var4.getValue("actual_device_root"));
                  if (StringUtils.isEmpty(this.c)) {
                     throw new WURFLParsingException("device id is not a valid");
                  }

                  if (!"generic".equals(this.c) && StringUtils.isEmpty(this.b)) {
                     StringBuilder var8;
                     (var8 = new StringBuilder()).append("Device with id ").append(this.c).append(" has an invalid user agent");
                     throw new WURFLParsingException(var8.toString());
                  }

                  if (this.j.contains(this.c)) {
                     throw new WURFLParsingException("device id " + this.c + " already defined!!!");
                  }

                  if (this.i.contains(this.b)) {
                     throw new WURFLParsingException("user agent [" + this.b + "] already defined");
                  }

                  this.i.add(this.b);
                  this.j.add(this.c);
                  this.k = new HashMap();
                  this.l = new HashMap();
                  return;
               }
               break;
            case 5:
               if ("group".equals(var3)) {
                  this.a = WurflXmlParseState.i;
                  this.f = var4.getValue("id").intern();
                  return;
               }
               break;
            case 6:
               if ("capability".equals(var3)) {
                  this.a = WurflXmlParseState.j;
                  if (!"virtual_capabilities".equals(this.f)) {
                     this.g = var4.getValue("name");
                     if (this.s.isEmpty() || this.s.contains(this.g) || this.g.startsWith("controlcap_")) {
                        this.h = var4.getValue("value");
                        if (StringUtils.isEmpty(this.g) || this.h == null) {
                           throw new WURFLParsingException("device with id " + this.c + " has capability with name or value not valid");
                        }

                        if (this.k.containsKey(this.g)) {
                           throw new WURFLParsingException("The device with id " + this.c + " defines capability " + this.g + "more than once");
                        }

                        String var5 = this.g.intern();
                        if (!"experimental".equals(this.f)) {
                           if (StringUtils.isNotEmpty(var3 = this.h) && var3.length() > 255) {
                              var3 = var3.substring(0, 255);
                           }

                           this.h = var3;
                        }

                        this.k.put(var5, this.h);
                        this.l.put(var5, this.f);
                     }
                  }
               }
         }

      }
   }

   public final void endElement(String var1, String var2, String var3) {
      switch (WurflXmlParseStateSwitch.a[this.a - 1]) {
         case 2:
            if ("wurfl".equals(var3) || "wurfl_patch".equals(var3)) {
               this.a = WurflXmlParseState.k;
               return;
            }
            break;
         case 3:
            if ("version".equals(var3)) {
               this.a = WurflXmlParseState.b;
               return;
            }
            break;
         case 4:
            if ("devices".equals(var3)) {
               this.a = WurflXmlParseState.b;
               return;
            }
            break;
         case 5:
            if ("device".equals(var3)) {
               ModelDevice var4 = (new ModelDevice$Builder(this.c, this.b, this.d)).setActualDeviceRoot(this.e).setCapabilities(this.k).setCapabilitiesByGroup(this.l).build();
               this.m.add(var4);
               if (var4.isActualDeviceRoot()) {
                  this.n.put(this.c, var4);
               }

               this.a = WurflXmlParseState.g;
               return;
            }
            break;
         case 6:
            if ("group".equals(var3)) {
               this.a = WurflXmlParseState.h;
               return;
            }
            break;
         case 7:
            if ("ver".equals(var3)) {
               this.a = WurflXmlParseState.c;
               return;
            }
            break;
         case 8:
            if ("last_updated".equals(var3)) {
               this.a = WurflXmlParseState.c;
               return;
            }
            break;
         case 9:
            if ("smid".equals(var3)) {
               this.a = WurflXmlParseState.c;
               return;
            }
            break;
         case 10:
            if ("capability".equals(var3)) {
               this.a = WurflXmlParseState.i;
            }
      }

   }

   public final void characters(char[] var1, int var2, int var3) {
      switch (WurflXmlParseStateSwitch.a[this.a - 1]) {
         case 7:
            this.o = (new StringBuilder()).append(var1, var2, var3).toString();
            return;
         case 8:
            this.p = (new StringBuilder()).append(var1, var2, var3).toString();
            return;
         case 9:
            this.q = (new StringBuilder()).append(var1, var2, var3).toString();
         default:
      }
   }

   // $FF: synthetic method
   WurflXmlHandler(Set var1, byte var2) {
      this(var1);
   }

   // $FF: synthetic method
   static String a(WurflXmlHandler var0) {
      return var0.o;
   }

   // $FF: synthetic method
   static String b(WurflXmlHandler var0) {
      return var0.p;
   }

   // $FF: synthetic method
   static String c(WurflXmlHandler var0) {
      return var0.q;
   }

   // $FF: synthetic method
   static boolean d(WurflXmlHandler var0) {
      return var0.r;
   }

   // $FF: synthetic method
   static ModelDevices e(WurflXmlHandler var0) {
      return var0.m;
   }
}
