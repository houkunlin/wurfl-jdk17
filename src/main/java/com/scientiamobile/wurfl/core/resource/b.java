package com.scientiamobile.wurfl.core.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ResourceInput {
   private static final Logger a = LoggerFactory.getLogger(ResourceInput.class);
   private URI b;
   private InputStream c;
   // $FF: synthetic field
   private static boolean d = !ResourceInput.class.desiredAssertionStatus();

   public ResourceInput(String var1) {
      Validate.notEmpty(var1, "The path must be not empty");

      try {
         this.b = a(var1);
      } catch (URISyntaxException var2) {
         throw new RuntimeException(var2);
      }
   }

   public ResourceInput(File var1) {
      Validate.notNull(var1, "The file must be not null");
      this.b = var1.toURI();
   }

   public ResourceInput(URI var1) {
      Validate.notNull(var1, "The URI must be not null");
      this.b = var1;
   }

   public ResourceInput(InputStream var1, String var2) {
      Validate.notNull(var1, "The stream must be not null");
      Validate.notNull(var2, "The fileName must be not null");
      if (!(var1 instanceof ZipInputStream) && !(var1 instanceof GZIPInputStream)) {
         try {
            if (var2.toLowerCase().endsWith(".zip")) {
               this.c = a(var1);
            } else if (var2.toLowerCase().endsWith(".gz")) {
               this.c = new GZIPInputStream(var1);
            } else {
               this.c = var1;
            }
         } catch (IOException var3) {
            a.error(var3.toString());
         }
      } else {
         this.c = var1;
      }
   }

   public final String a() {
      String var1;
      if (this.b != null) {
         var1 = this.b.toString();
      } else {
         var1 = "Stream resource";
      }

      return var1;
   }

   private static URI a(String var0) {
      if (!d && !StringUtils.isNotBlank(var0)) {
         throw new AssertionError("The path must be not blank");
      } else {
         File var1;
         URI var2;
         if ((var1 = new File(var0)).exists() && var1.isFile() && var1.canRead()) {
            var2 = var1.toURI();
         } else {
            StrBuilder var3;
            (var3 = new StrBuilder(var0)).replaceAll(" ", "%20");
            if (SystemUtils.IS_OS_WINDOWS && StringUtils.contains(var0, "\\")) {
               var3.replaceAll("\\\\", "/");
            }

            if (!var3.contains(':')) {
               while(var3.startsWith("/")) {
                  var3 = var3.deleteCharAt(0);
               }

               var3.insert(0, "file:///");
            }

            var2 = URI.create(var3.toString());
         }

         return var2;
      }
   }

   private InputStream a(URI var1) {
      try {
         Object var4;
         if (var1.getScheme().equals("classpath")) {
            StrBuilder var2;
            (var2 = new StrBuilder()).append(var1.toString());
            var2.replaceFirst("classpath:", "");
            var4 = this.getClass().getResourceAsStream(var2.toString());
         } else {
            var4 = var1.toURL().openConnection().getInputStream();
         }

         if (var1.getPath().toLowerCase().endsWith(".zip")) {
            var4 = a((InputStream)var4);
         } else if (var1.getPath().toLowerCase().endsWith(".gz")) {
            var4 = new GZIPInputStream((InputStream)var4);
         }

         return (InputStream)var4;
      } catch (IOException var3) {
         a.error("Error opening stream URI:" + var1.toString());
         throw new RuntimeException(var3);
      }
   }

   private static InputStream a(InputStream var0) {
      ZipInputStream var1;
      (var1 = new ZipInputStream(var0)).getNextEntry();
      return var1;
   }

   private void e() {
      try {
         a.info("closing input stream: " + this.c.getClass().getSimpleName());
         this.c.close();
      } catch (IOException var1) {
         a.warn("Error closing stream");
      }

      this.c = null;
   }

   public final void b() {
      if (this.c != null) {
         this.e();
      }

      this.b = null;
   }

   public final InputStream c() {
      if (this.c == null) {
         if (this.b == null) {
            throw new RuntimeException("The resource can not be read, the stream is null");
         }

         this.c = this.a(this.b);
      }

      return this.c;
   }

   public final void d() {
      if (this.c.markSupported()) {
         try {
            this.c.reset();
            return;
         } catch (IOException var1) {
         }
      }

      this.e();
   }
}
