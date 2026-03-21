package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.WURFLParsingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

final class g extends DefaultHandler {
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
  
  private final Map n = new HashMap<Object, Object>();
  
  private String o;
  
  private String p;
  
  private String q;
  
  private boolean r = false;
  
  private Set s;
  
  private g(Set paramSet) {
    this.s = paramSet;
    this.a = h.a;
  }
  
  public final void startDocument() {
    super.startDocument();
    this.i = new HashSet();
    this.j = new HashSet();
    this.m = new ModelDevices();
  }
  
  public final void endDocument() {}
  
  public final void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes) {
    Attributes attributes = paramAttributes;
    paramString2 = paramString3;
    g g1 = this;
    if (paramString2.equals("capability") && g1.a != h.i) {
      paramString3 = attributes.getValue("name");
      throw new WURFLParsingException("Capability '" + paramString3 + "'  does not belong to any group");
    } 
    switch (f.a[this.a - 1]) {
      case 1:
        this.r = "wurfl_patch".equals(paramString3);
        if ("wurfl".equals(paramString3) || this.r) {
          this.a = h.b;
          return;
        } 
        break;
      case 2:
        if ("version".equals(paramString3)) {
          this.a = h.c;
          return;
        } 
        if ("devices".equals(paramString3)) {
          this.a = h.g;
          return;
        } 
        break;
      case 3:
        if ("ver".equals(paramString3)) {
          this.a = h.d;
          return;
        } 
        if ("last_updated".equals(paramString3)) {
          this.a = h.e;
          return;
        } 
        if ("smid".equals(paramString3)) {
          this.a = h.f;
          return;
        } 
        break;
      case 4:
        if ("device".equals(paramString3)) {
          this.a = h.h;
          Attributes attributes1 = paramAttributes;
          (g1 = this).b = attributes1.getValue("user_agent");
          g1.c = attributes1.getValue("id");
          g1.d = attributes1.getValue("fall_back");
          g1.e = Boolean.valueOf(attributes1.getValue("actual_device_root")).booleanValue();
          if (StringUtils.isEmpty(g1.c))
            throw new WURFLParsingException("device id is not a valid"); 
          if (!"generic".equals(g1.c) && StringUtils.isEmpty(g1.b)) {
            StringBuilder stringBuilder;
            (stringBuilder = new StringBuilder()).append("Device with id ").append(g1.c).append(" has an invalid user agent");
            throw new WURFLParsingException(stringBuilder.toString());
          } 
          if (g1.j.contains(g1.c))
            throw new WURFLParsingException("device id " + g1.c + " already defined!!!"); 
          if (g1.i.contains(g1.b))
            throw new WURFLParsingException("user agent [" + g1.b + "] already defined"); 
          g1.i.add(g1.b);
          g1.j.add(g1.c);
          g1.k = new HashMap<Object, Object>();
          g1.l = new HashMap<Object, Object>();
          return;
        } 
        break;
      case 5:
        if ("group".equals(paramString3)) {
          this.a = h.i;
          this.f = paramAttributes.getValue("id").intern();
          return;
        } 
        break;
      case 6:
        if ("capability".equals(paramString3)) {
          this.a = h.j;
          Attributes attributes1 = paramAttributes;
          g1 = this;
          if (!"virtual_capabilities".equals(g1.f)) {
            g1.g = attributes1.getValue("name");
            if (g1.s.isEmpty() || g1.s.contains(g1.g) || g1.g.startsWith("controlcap_")) {
              g1.h = attributes1.getValue("value");
              if (StringUtils.isEmpty(g1.g) || g1.h == null)
                throw new WURFLParsingException("device with id " + g1.c + " has capability with name or value not valid"); 
              if (g1.k.containsKey(g1.g))
                throw new WURFLParsingException("The device with id " + g1.c + " defines capability " + g1.g + "more than once"); 
              String str = g1.g.intern();
              if (!"experimental".equals(g1.f)) {
                if (StringUtils.isNotEmpty(paramString3 = g1.h) && paramString3.length() > 255)
                  paramString3 = paramString3.substring(0, 255); 
                g1.h = paramString3;
              } 
              g1.k.put(str, g1.h);
              g1.l.put(str, g1.f);
            } 
          } 
        } 
        break;
    } 
  }
  
  public final void endElement(String paramString1, String paramString2, String paramString3) {
    switch (f.a[this.a - 1]) {
      case 2:
        if ("wurfl".equals(paramString3) || "wurfl_patch".equals(paramString3)) {
          this.a = h.k;
          return;
        } 
        break;
      case 3:
        if ("version".equals(paramString3)) {
          this.a = h.b;
          return;
        } 
        break;
      case 7:
        if ("ver".equals(paramString3)) {
          this.a = h.c;
          return;
        } 
        break;
      case 8:
        if ("last_updated".equals(paramString3)) {
          this.a = h.c;
          return;
        } 
        break;
      case 9:
        if ("smid".equals(paramString3)) {
          this.a = h.c;
          return;
        } 
        break;
      case 4:
        if ("devices".equals(paramString3)) {
          this.a = h.b;
          return;
        } 
        break;
      case 5:
        if ("device".equals(paramString3)) {
          g g1 = this;
          ModelDevice modelDevice = (new ModelDevice$Builder(g1.c, g1.b, g1.d)).setActualDeviceRoot(g1.e).setCapabilities(g1.k).setCapabilitiesByGroup(g1.l).build();
          g1.m.add(modelDevice);
          if (modelDevice.isActualDeviceRoot())
            g1.n.put(g1.c, modelDevice); 
          this.a = h.g;
          return;
        } 
        break;
      case 6:
        if ("group".equals(paramString3)) {
          this.a = h.h;
          return;
        } 
        break;
      case 10:
        if ("capability".equals(paramString3))
          this.a = h.i; 
        break;
    } 
  }
  
  public final void characters(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
    switch (f.a[this.a - 1]) {
      case 7:
        this.o = (new StringBuilder()).append(paramArrayOfchar, paramInt1, paramInt2).toString();
        return;
      case 8:
        this.p = (new StringBuilder()).append(paramArrayOfchar, paramInt1, paramInt2).toString();
        return;
      case 9:
        this.q = (new StringBuilder()).append(paramArrayOfchar, paramInt1, paramInt2).toString();
        break;
    } 
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\g.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
