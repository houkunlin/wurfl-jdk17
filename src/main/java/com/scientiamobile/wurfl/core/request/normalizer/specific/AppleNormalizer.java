package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppleNormalizer implements UserAgentNormalizer {
   private static final Pattern a = Pattern.compile("^[^/]+?/[\\d\\.]+? \\(i[A-Za-z]+; iOS ([\\d\\.]+); Scale/[\\d\\.]+\\)");
   private static final Pattern b = Pattern.compile("^server-bag \\[iPhone OS,([\\d\\.]+),");
   private static final Pattern c = Pattern.compile("^i(?:Phone|Pad|Pod)\\d+?,\\d+?/([\\d\\.]+)");
   private static final Pattern d = Pattern.compile("i(?:Phone|Pad|Pod)\\d+?,\\d+? iOS/([\\d_]+)");
   private static final Pattern e = Pattern.compile("^iOSClientSDK/\\d+\\.+[0-9\\.]+ +?\\((Mozilla.+)\\)$");
   private static final Pattern f = Pattern.compile("CPU iOS \\d+?\\.\\d+?");
   private static final Pattern g = Pattern.compile("(CPU(?: iPhone)? OS [\\d\\.]+ like)");

   private static Matcher a(String var0, Pattern var1) {
      if (var0 != null && var1 != null) {
         Matcher var2;
         return (var2 = var1.matcher(var0)).find() ? var2 : null;
      } else {
         return null;
      }
   }

   public String normalize(String var1) {
      Matcher var2;
      if ((var2 = a(var1, a)) == null) {
         var2 = a(var1, b);
      }

      if (var2 == null) {
         var2 = a(var1, c);
      }

      if (var2 == null) {
         var2 = a(var1, d);
      }

      if (var2 != null) {
         String var10 = var2.group(1).replace(".", "_");
         StringBuilder var8 = new StringBuilder(50);
         if (var1.contains("iPad")) {
            return var8.append("Mozilla/5.0 (iPad; CPU OS ").append(var10).append(" like Mac OS X) AppleWebKit/538.39.2 (KHTML, like Gecko) Version/7.0 Mobile/12A4297e Safari/9537.53 ").append(var1).toString();
         } else if (var1.contains("iPod touch")) {
            return var8.append("Mozilla/5.0 (iPod touch; CPU iPhone OS ").append(var10).append(" like Mac OS X) AppleWebKit/538.41 (KHTML, like Gecko) Version/7.0 Mobile/12A307 Safari/9537.53 ").append(var1).toString();
         } else {
            return var1.contains("iPod") ? var8.append("Mozilla/5.0 (iPod; CPU iPhone OS ").append(var10).append(" like Mac OS X) AppleWebKit/538.41 (KHTML, like Gecko) Version/7.0 Mobile/12A307 Safari/9537.53 ").append(var1).toString() : var8.append("Mozilla/5.0 (iPhone; CPU iPhone OS ").append(var10).append(" like Mac OS X) AppleWebKit/601.1.10 (KHTML, like Gecko) Version/8.0 Mobile/12E155 Safari/600.1.4 ").append(var1).toString();
         }
      } else {
         Pattern var3 = e;
         Matcher var10000 = var1 != null && var3 != null ? ((var2 = var3.matcher(var1)).matches() ? var2 : null) : null;
         var2 = var10000;
         if (var10000 != null) {
            return var2.group(1);
         } else {
            String var9;
            if (a(var1, f) != null && (var2 = a(var9 = var1.contains("iPad") ? var1.replace("CPU iOS", "CPU OS") : var1.replace("CPU iOS", "CPU iPhone OS"), g)) != null) {
               String var7 = var2.group(1).replace(".", "_");
               return var9.replace(" U;", "").replaceAll("CPU(?: iPhone)? OS ([\\d\\.]+) like", var7);
            } else {
               return var1;
            }
         }
      }
   }
}
