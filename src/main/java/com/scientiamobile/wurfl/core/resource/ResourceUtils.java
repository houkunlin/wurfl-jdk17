package com.scientiamobile.wurfl.core.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ResourceUtils {
   private ResourceUtils() {
   }

   public static String getBuildId() {
      String fullBuildId = getFullBuildId();
      String result = fullBuildId;
      if (fullBuildId != null && !"unknown".equals(fullBuildId)) {
         int colonIndex = fullBuildId.indexOf(":");
         result = colonIndex != -1 ? fullBuildId.substring(colonIndex + 1) : fullBuildId;
      }

      return result;
   }

   public static String getFullBuildId() {
      String buildId = "unknown";
      InputStream inputStream = ResourceUtils.class.getResourceAsStream("/ca");
      if (inputStream != null) {
         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

         try {
            String line = reader.readLine();
            if (line != null) {
               buildId = line;
            }
            return buildId;
         } catch (IOException e) {
            // silently ignore read failure
         } finally {
            try {
               reader.close();
            } catch (IOException e) {
               // close failure is non-actionable
            }

         }

         return buildId;
      } else {
         return buildId;
      }
   }
}
