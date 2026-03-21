package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.WURFLResourceException;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.SAXParserFactory;
import org.slf4j.LoggerFactory;

public class XMLResource implements WURFLResource {
  private final b a;
  
  private String b;
  
  private Set c;
  
  private String d;
  
  private static final SAXParserFactory e = SAXParserFactory.newInstance();
  
  public XMLResource(String paramString) {
    this.d = paramString;
    this.a = new b(paramString);
  }
  
  public XMLResource(File paramFile) {
    this.d = paramFile.getAbsolutePath();
    this.a = new b(paramFile);
  }
  
  public XMLResource(URI paramURI) {
    this.a = new b(paramURI);
  }
  
  public XMLResource(InputStream paramInputStream, String paramString) {
    this.a = new b(paramInputStream, paramString);
  }
  
  public c getData(String... paramVarArgs) {
    String[] arrayOfString = paramVarArgs;
    XMLResource xMLResource = this;
    if (arrayOfString != null) {
      xMLResource.c = new HashSet(arrayOfString.length);
      int i = (arrayOfString = arrayOfString).length;
      for (byte b1 = 0; b1 < i; b1++) {
        String str = arrayOfString[b1];
        xMLResource.c.add(str);
      } 
    } else {
      xMLResource.c = new HashSet(0);
    } 
    c c = a(this.a.c());
    this.a.d();
    return c;
  }
  
  public String getOriginalPath() {
    return this.d;
  }
  
  public String getInfo() {
    return this.a.a();
  }
  
  public String getVersion() {
    return this.b;
  }
  
  public void release() {
    this.a.b();
  }
  
  private c a(InputStream paramInputStream) {
    g g = new g(this.c, (byte)0);
    try {
      e.newSAXParser().parse(paramInputStream, g);
    } catch (Exception exception) {
      throw new WURFLResourceException(this, exception);
    } 
    String str2 = getInfo();
    String str1 = g.a(g);
    String str3 = g.b(g);
    String str4 = g.c(g);
    this.b = (str1 == null || str1.length() == 0) ? ((str3 == null || str3.length() == 0) ? "(no version info)" : str3) : str1;
    boolean bool = g.d(g);
    ModelDevices modelDevices = g.e(g);
    return new c(str2, this.b, bool, modelDevices, str4);
  }
  
  static {
    LoggerFactory.getLogger(XMLResource.class);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\XMLResource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */