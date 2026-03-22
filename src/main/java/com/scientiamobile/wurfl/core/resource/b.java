package com.scientiamobile.wurfl.core.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class b {
  private static final Logger a;

  private URI b;

  private InputStream c;

  public b(String paramString) {
    Validate.notEmpty(paramString, "The path must be not empty");
    try {
      this.b = a(paramString);
      return;
    } catch (URISyntaxException uRISyntaxException) {
      throw new RuntimeException(uRISyntaxException);
    }
  }

  public b(File paramFile) {
    Validate.notNull(paramFile, "The file must be not null");
    this.b = paramFile.toURI();
  }

  public b(URI paramURI) {
    Validate.notNull(paramURI, "The URI must be not null");
    this.b = paramURI;
  }

  public b(InputStream paramInputStream, String paramString) {
    Validate.notNull(paramInputStream, "The stream must be not null");
    Validate.notNull(paramString, "The fileName must be not null");
    if (!(paramInputStream instanceof ZipInputStream) && !(paramInputStream instanceof GZIPInputStream))
      try {
        if (paramString.toLowerCase().endsWith(".zip")) {
          this.c = a(paramInputStream);
          return;
        }
        if (paramString.toLowerCase().endsWith(".gz")) {
          this.c = new GZIPInputStream(paramInputStream);
          return;
        }
        this.c = paramInputStream;
        return;
      } catch (IOException iOException) {
        a.error(iOException.toString());
        return;
      }
    this.c = (InputStream)iOException;
  }

  public final String a() {
    String str;
    if (this.b != null) {
      str = this.b.toString();
    } else {
      str = "Stream resource";
    }
    return str;
  }

  private static URI a(String paramString) {
    URI uRI;
    if (!StringUtils.isNotBlank(paramString))
      throw new AssertionError("The path must be not blank");
    File file;
    if ((file = new File(paramString)).exists() && file.isFile() && file.canRead()) {
      uRI = file.toURI();
    } else {
      StrBuilder strBuilder;
      (strBuilder = new StrBuilder((String)uRI)).replaceAll(" ", "%20");
      if (SystemUtils.IS_OS_WINDOWS && StringUtils.contains((String)uRI, "\\"))
        strBuilder.replaceAll("\\\\", "/");
      if (!strBuilder.contains(':')) {
        while (strBuilder.startsWith("/"))
          strBuilder = strBuilder.deleteCharAt(0);
        strBuilder.insert(0, "file:///");
      }
      uRI = URI.create(strBuilder.toString());
    }
    return uRI;
  }

  private InputStream a(URI paramURI) {
    try {
      InputStream inputStream;
      if (paramURI.getScheme().equals("classpath")) {
        StrBuilder strBuilder;
        (strBuilder = new StrBuilder()).append(paramURI.toString());
        strBuilder.replaceFirst("classpath:", "");
        inputStream = getClass().getResourceAsStream(strBuilder.toString());
      } else {
        inputStream = paramURI.toURL().openConnection().getInputStream();
      }
      if (paramURI.getPath().toLowerCase().endsWith(".zip")) {
        inputStream = a(inputStream);
      } else if (paramURI.getPath().toLowerCase().endsWith(".gz")) {
        inputStream = new GZIPInputStream(inputStream);
      }
    } catch (IOException iOException) {
      a.error("Error opening stream URI:" + paramURI.toString());
      throw new RuntimeException(iOException);
    }
    return (InputStream)iOException;
  }

  private static InputStream a(InputStream paramInputStream) {
    (paramInputStream = new ZipInputStream(paramInputStream)).getNextEntry();
    return paramInputStream;
  }

  private void e() {
    try {
      a.info("closing input stream: " + this.c.getClass().getSimpleName());
      this.c.close();
    } catch (IOException iOException) {
      a.warn("Error closing stream");
    }
    this.c = null;
  }

  public final void b() {
    if (this.c != null)
      e();
    this.b = null;
  }

  public final InputStream c() {
    if (this.c == null)
      if (this.b != null) {
        this.c = a(this.b);
      } else {
        throw new RuntimeException("The resource can not be read, the stream is null");
      }
    return this.c;
  }

  public final void d() {
    if (this.c.markSupported())
      try {
        this.c.reset();
        return;
      } catch (IOException iOException) {}
    e();
  }

  static {
    a = LoggerFactory.getLogger(b.class);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\resource\b.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
