package com.scientiamobile.wurfl.core.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ResourceInput {
   private static final Logger LOG = LoggerFactory.getLogger(ResourceInput.class);
   private URI uri;
   private InputStream stream;
   private static boolean ASSERTIONS_DISABLED = !ResourceInput.class.desiredAssertionStatus();

   public ResourceInput(String path) {
      Validate.notEmpty(path, "The path must be not empty");
      this.uri = parseUri(path);
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
         } catch (IOException e) {
            LOG.error(e.toString());
         }
      } else {
         this.stream = stream;
      }
   }

   public final String getResourceName() {
      if (this.uri != null) {
         return this.uri.toString();
      } else {
         return "Stream resource";
      }
   }

   private static URI parseUri(String path) {
      if (!ASSERTIONS_DISABLED && !StringUtils.isNotBlank(path)) {
         throw new AssertionError("The path must be not blank");
      } else {
         File file;
         URI uri;
         if ((file = new File(path)).exists() && file.isFile() && file.canRead()) {
            uri = file.toURI();
         } else {
            String uriString = path.replace(" ", "%20");
            if (SystemUtils.IS_OS_WINDOWS && path.contains("\\")) {
               uriString = uriString.replace("\\", "/");
            }

            if (!uriString.contains(":")) {
               while(uriString.startsWith("/")) {
                  uriString = uriString.substring(1);
               }

               uriString = "file:///" + uriString;
            }

            uri = URI.create(uriString);
         }

         return uri;
      }
   }

   private InputStream openStream(URI uri) {
      try {
         InputStream inputStream;
         if (uri.getScheme().equals("classpath")) {
            String resourcePath = uri.toString().replaceFirst("classpath:", "");
            inputStream = this.getClass().getResourceAsStream(resourcePath);
         } else {
            inputStream = uri.toURL().openConnection().getInputStream();
         }

         if (uri.getPath().toLowerCase().endsWith(".zip")) {
            inputStream = unwrapZip(inputStream);
         } else if (uri.getPath().toLowerCase().endsWith(".gz")) {
            inputStream = new GZIPInputStream(inputStream);
         }

         return inputStream;
      } catch (IOException e) {
         LOG.error("Error opening stream URI: {}", uri.toString());
         throw new RuntimeException(e);
      }
   }

   private static InputStream unwrapZip(InputStream stream) {
      try {
         ZipInputStream zipInputStream;
         (zipInputStream = new ZipInputStream(stream)).getNextEntry();
         return zipInputStream;
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private void closeStream() {
      try {
         LOG.info("closing input stream: {}", this.stream.getClass().getSimpleName());
         this.stream.close();
      } catch (IOException e) {
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
         } catch (IOException e) {
         }
      }

      this.closeStream();
   }
}
