package com.scientiamobile.wurfl.core.resource;

import java.io.InputStream;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;

public class XmlFileLoader {
  private final b a;
  
  private DefaultHandler b;
  
  public XmlFileLoader(String paramString, DefaultHandler paramDefaultHandler) {
    this.a = new b(paramString);
    this.b = paramDefaultHandler;
  }
  
  public final boolean parseFile() {
    InputStream inputStream = this.a.c();
    try {
      SAXParserFactory.newInstance().newSAXParser().parse(inputStream, this.b);
      return true;
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    } finally {
      inputStream.close();
    } 
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\XmlFileLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */