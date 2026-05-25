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
   private static final Logger LOG = LoggerFactory.getLogger(ResourceInput.class);
   private URI uri;
   private InputStream stream;
   private static boolean ASSERTIONS_DISABLED = !ResourceInput.class.desiredAssertionStatus();

   public ResourceInput(String path) {
      Validate.notEmpty(path, "The path must be not empty");

      try {
         this.uri = parseUri(path);
      } catch (URISyntaxException var2) {
         throw new RuntimeException(var2);
      }
   }

   public ResourceInput(File file) {
      Validate.notNull(file, "The file must be not null");
      this.uri = file.toURI();
   }

   public ResourceInput(URI uri) {
      Validate.notNull(uri, "The URI must be not null");
      this.uri = uri;
   }

   public ResourceInput(InputStream stream, String fileName) {
      Validate.notNull(stream, "The stream must be not null");
      Validate.notNull(fileName, "The fileName must be not null");
      if (!(stream instanceof ZipInputStream) && !(stream instanceof GZIPInputStream)) {
         try {
            if (fileName.toLowerCase().endsWith(".zip")) {
               this.stream = unwrapZip(stream);
            } else if (fileName.toLowerCase().endsWith(".gz")) {
               this.stream = new GZIPInputStream(stream);
            } else {
               this.stream = stream;
            }
         } catch (IOException var3) {
            LOG.error(var3.toString());
         }
      } else {
         this.stream = stream;
      }
   }

   public final String getResourceName() {
      String var1;
      if (this.uri != null) {
         var1 = this.uri.toString();
      } else {
         var1 = "Stream resource";
      }

      return var1;
   }

   private static URI parseUri(String path) {
      if (!ASSERTIONS_DISABLED && !StringUtils.isNotBlank(path)) {
         throw new AssertionError("The path must be not blank");
      } else {
         File var1;
         URI var2;
         if ((var1 = new File(path)).exists() && var1.isFile() && var1.canRead()) {
            var2 = var1.toURI();
         } else {
            StrBuilder var3;
            (var3 = new StrBuilder(path)).replaceAll(" ", "%20");
            if (SystemUtils.IS_OS_WINDOWS && StringUtils.contains(path, "\\")) {
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

   private InputStream openStream(URI uri) {
      try {
         Object var4;
         if (uri.getScheme().equals("classpath")) {
            StrBuilder var2;
            (var2 = new StrBuilder()).append(uri.toString());
            var2.replaceFirst("classpath:", "");
            var4 = this.getClass().getResourceAsStream(var2.toString());
         } else {
            var4 = uri.toURL().openConnection().getInputStream();
         }

         if (uri.getPath().toLowerCase().endsWith(".zip")) {
            var4 = unwrapZip((InputStream)var4);
         } else if (uri.getPath().toLowerCase().endsWith(".gz")) {
            var4 = new GZIPInputStream((InputStream)var4);
         }

         return (InputStream)var4;
      } catch (IOException var3) {
         LOG.error("Error opening stream URI:" + uri.toString());
         throw new RuntimeException(var3);
      }
   }

   private static InputStream unwrapZip(InputStream stream) {
      ZipInputStream var1;
      (var1 = new ZipInputStream(stream)).getNextEntry();
      return var1;
   }

   private void closeStream() {
      try {
         LOG.info("closing input stream: " + this.stream.getClass().getSimpleName());
         this.stream.close();
      } catch (IOException var1) {
         LOG.warn("Error closing stream");
      }

      this.stream = null;
   }

   public final void close() {
      if (this.stream != null) {
         this.closeStream();
      }

      this.uri = null;
   }

   public final InputStream openInputStream() {
      if (this.stream == null) {
         if (this.uri == null) {
            throw new RuntimeException("The resource can not be read, the stream is null");
         }

         this.stream = this.openStream(this.uri);
      }

      return this.stream;
   }

   public final void reset() {
      if (this.stream.markSupported()) {
         try {
            this.stream.reset();
            return;
         } catch (IOException var1) {
         }
      }

      this.closeStream();
   }
}

