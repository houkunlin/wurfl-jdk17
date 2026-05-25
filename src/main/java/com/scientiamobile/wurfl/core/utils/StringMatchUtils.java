package com.scientiamobile.wurfl.core.utils;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.strategy.RISMatcher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

public final class StringMatchUtils {
   public static final String NULL_STRING;
   public static final String EMPTY_STRING = "";
   public static final int INDEX_NOT_FOUND = -1;

   private StringMatchUtils() {
   }

   public static int firstSlash(String var0) {
      return ordinalIndexOfOrNotFound(var0, "/", 1);
   }

   public static int secondSlash(String var0) {
      return ordinalIndexOfOrNotFound(var0, "/", 2);
   }

   public static int firstCloseParenthesis(String var0) {
      return ordinalIndexOfOrNotFound(var0, ")", 1);
   }

   public static int firstOpenParenthesis(String var0) {
      return ordinalIndexOfOrNotFound(var0, "(", 1);
   }

   public static int firstSpace(String var0) {
      return ordinalIndexOfOrNotFound(var0, " ", 1);
   }

   public static int ordinalIndexOfOrNotFound(String var0, String var1, int var2) {
      if (var1 == null) {
         return -1;
      } else {
         int var3;
         return (var3 = StringUtils.ordinalIndexOf(var0, var1, var2)) == -1 ? -1 : var3 + var1.length();
      }
   }

   public static int firstSemiColon(String var0) {
      return ordinalIndexOfOrLength(var0, ";", 1);
   }

   public static int indexOfOrLength(String var0, String var1) {
      return indexOfOrLength(var0, var1, 0);
   }

   public static int indexOfOrLength(String var0, String var1, int var2) {
      return ordinalIndexOfOrLength(var0, var1, 1, var2);
   }

   public static int ordinalIndexOfOrLength(String var0, String var1, int var2) {
      return ordinalIndexOfOrLength(var0, var1, var2, 0);
   }

   public static int ordinalIndexOfOrLength(String var0, String var1, int var2, int var3) {
      String var4 = StringUtils.defaultString(var0);
      if (var3 < 0) {
         return var4.length();
      } else {
         int var5;
         if ((var5 = StringUtils.ordinalIndexOf(StringUtils.substring(var4, var3), var1, var2)) >= 0) {
            var5 += var3;
         } else {
            var5 = var0.length();
         }

         return var5;
      }
   }

   public static String risMatch(Collection var0, String var1, int var2) {
      return RISMatcher.INSTANCE.match(var0, var1, var2);
   }

   public static String hierarchyAsString(List var0) {
      StringBuilder var1 = new StringBuilder();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         var1.append(((ModelDevice)var2.next()).getID());
         if (var2.hasNext()) {
            var1.append(" -> ");
         }
      }

      return var1.toString();
   }

   public static int indexOf(String var0, String var1) {
      return StringUtils.indexOf(var0, var1);
   }

   public static int indexOf(String var0, String var1, int var2) {
      return StringUtils.indexOf(var0, var1, var2);
   }

   public static String removeSubstringBefore(String var0, String var1) {
      int var2;
      return (var2 = var0.indexOf(var1)) > 0 ? var0.substring(var2) : var0;
   }

   public static boolean containsAnyOf(String var0, String... var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var0.indexOf(var1[var2]) != -1) {
            return true;
         }
      }

      return false;
   }

   public static boolean containsAnyOfIgnoreCase(String var0, String... var1) {
      return containsAnyOf(var0.toLowerCase(), var1);
   }

   public static boolean startsWithAnyOf(String var0, String... var1) {
      for(String var4 : var1 = var1) {
         if (var0.startsWith(var4)) {
            return true;
         }
      }

      return false;
   }

   public static int indexOfAnyOrLength(String var0, String... var1) {
      return indexOfAnyOrLength(var0, var1, 0);
   }

   public static int indexOfAnyOrLength(String var0, String[] var1, int var2) {
      if (var2 == -1) {
         return var0.length();
      } else {
         int var3;
         return (var3 = StringUtils.indexOfAny(var2 > 0 ? var0.substring(var2) : var0, var1)) >= 0 ? var3 + var2 : var0.length();
      }
   }

   public static boolean containsAllOf(String var0, String... var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var0.indexOf(var1[var2]) == -1) {
            return false;
         }
      }

      return true;
   }

   public static boolean containsAllOf(String var0, List var1) {
      for(int var2 = 0; var2 < var1.size(); ++var2) {
         if (var0.indexOf((String)var1.get(var2)) == -1) {
            return false;
         }
      }

      return true;
   }

   public static String format(Set var0) {
      StringBuilder var1 = new StringBuilder(10);

      for(String var2 : var0) {
         var1.append(var2).append('\n');
      }

      return var1.toString();
   }

   public static String rtrim(String var0, char... var1) {
      int var2;
      for(var2 = var0.length(); var2 > 0 && ArrayUtils.contains(var1, var0.charAt(var2 - 1)); --var2) {
      }

      return var0.substring(0, var2);
   }

   public static Integer firstChar(String var0, char var1) {
      int var2;
      return (var2 = var0.indexOf(var1)) != -1 ? var2 + 1 : -1;
   }

   public static String rawdecode(String var0, String var1) {
      if (StringUtils.isEmpty(var0)) {
         return var0;
      } else {
         int var2 = var0.length();
         ByteArrayOutputStream var3 = new ByteArrayOutputStream(var2);

         try {
            for(int var4 = 0; var4 < var2; ++var4) {
               if (var0.charAt(var4) == '%' && var4 + 2 < var2) {
                  int var5 = var0.charAt(var4 + 1);
                  int var6 = var0.charAt(var4 + 2);
                  var5 = Character.digit((char)var5, 16);
                  var6 = Character.digit((char)var6, 16);
                  if (var5 != -1 && var6 != -1) {
                     var5 = (char)((var5 << 4) + var6);
                     var3.write(var5);
                     var4 += 2;
                  } else {
                     a(var3, var0, var4, var1);
                  }
               } else {
                  a(var3, var0, var4, var1);
               }
            }
         } finally {
            try {
               var3.close();
            } catch (IOException var10) {
            }

         }

         return new String(var3.toByteArray(), var1);
      }
   }

   private static void a(ByteArrayOutputStream var0, String var1, int var2, String var3) {
      char var5 = var1.charAt(var2);
      String var6 = new String(new char[]{var5});

      try {
         var0.write(var6.getBytes(var3));
      } catch (IOException var4) {
         var0.write(var5);
      }
   }

   public static byte[] charToBytesUTFCustom(char var0) {
      byte[] var1;
      (var1 = new byte[2])[0] = (byte)(var0 >> 8);
      var1[1] = (byte)var0;
      return var1;
   }

   public static String rawdecode(String var0) {
      return rawdecode(var0, "UTF-8");
   }

   public static String replaceAll(String var0, Pattern var1, String var2) {
      return var1.matcher(var0).replaceAll(var2);
   }

   static {
      LoggerFactory.getLogger(StringMatchUtils.class);
      NULL_STRING = null;
   }
}
