package com.scientiamobile.wurfl.core;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

final class k extends DefaultHandler {
  private k(j paramj) {}
  
  public final void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes) {
    if (paramString3.equals("wurfl-api-config")) {
      paramString1 = paramAttributes.getValue("engine-target");
      if (j.a(this.a) == null) {
        if (paramString1 == null || "performance".equals(paramString1) || "accuracy".equals(paramString1)) {
          j.a(this.a, EngineTarget.defaultTarget);
          return;
        } 
        j.a(this.a, EngineTarget.fastDesktopBrowserMatch);
      } 
    } 
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\k.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */