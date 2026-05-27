package com.scientiamobile.wurfl.core.utils;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.strategy.RISMatcher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

public final class StringMatchUtils {
   public static final String NULL_STRING;
   public static final String EMPTY_STRING = "";
   public static final int INDEX_NOT_FOUND = -1;

   private StringMatchUtils() {
   }

   public static int firstSlash(String value) {
      return ordinalIndexOfOrNotFound(value, "/", 1);
   }

   public static int secondSlash(String value) {
      return ordinalIndexOfOrNotFound(value, "/", 2);
   }

   public static int firstCloseParenthesis(String value) {
      return ordinalIndexOfOrNotFound(value, ")", 1);
   }

   public static int firstOpenParenthesis(String value) {
      return ordinalIndexOfOrNotFound(value, "(", 1);
   }

   public static int firstSpace(String value) {
      return ordinalIndexOfOrNotFound(value, " ", 1);
   }

   public static int ordinalIndexOfOrNotFound(String value, String search, int ordinal) {
      if (search == null) {
         return -1;
      } else {
         int index;
         return (index = StringUtils.ordinalIndexOf(value, search, ordinal)) == -1 ? -1 : index + search.length();
      }
   }

   public static int firstSemiColon(String value) {
      return ordinalIndexOfOrLength(value, ";", 1);
   }

   public static int indexOfOrLength(String value, String search) {
      return indexOfOrLength(value, search, 0);
   }

   public static int indexOfOrLength(String value, String search, int fromIndex) {
      return ordinalIndexOfOrLength(value, search, 1, fromIndex);
   }

   public static int ordinalIndexOfOrLength(String value, String search, int ordinal) {
      return ordinalIndexOfOrLength(value, search, ordinal, 0);
   }

   public static int ordinalIndexOfOrLength(String value, String search, int ordinal, int fromIndex) {
      String nonNullValue = StringUtils.defaultString(value);
      if (fromIndex < 0) {
         return nonNullValue.length();
      } else {
         int index;
         if ((index = StringUtils.ordinalIndexOf(StringUtils.substring(nonNullValue, fromIndex), search, ordinal)) >= 0) {
            index += fromIndex;
         } else {
            index = value.length();
         }

         return index;
      }
   }

   public static String risMatch(Collection<?> userAgents, String userAgent, int userAgentLength) {
      return RISMatcher.INSTANCE.match(userAgents, userAgent, userAgentLength);
   }

   public static String hierarchyAsString(List<ModelDevice> devices) {
      StringBuilder out = new StringBuilder();
      Iterator<ModelDevice> iterator = devices.iterator();

      while(iterator.hasNext()) {
         out.append(iterator.next().getID());
         if (iterator.hasNext()) {
            out.append(" -> ");
         }
      }

      return out.toString();
   }

   public static int indexOf(String value, String search) {
      return value != null && search != null ? value.indexOf(search) : -1;
   }

   public static int indexOf(String value, String search, int fromIndex) {
      return value != null && search != null ? value.indexOf(search, fromIndex) : -1;
   }

   public static String removeSubstringBefore(String value, String search) {
      int index;
      return (index = value.indexOf(search)) > 0 ? value.substring(index) : value;
   }

   public static boolean containsAnyOf(String value, String... searches) {
      for (String s : searches) {
         if (value.indexOf(s) != -1) {
            return true;
         }
      }

      return false;
   }

   public static boolean containsAnyOfIgnoreCase(String value, String... searches) {
      return containsAnyOf(value.toLowerCase(Locale.ENGLISH), searches);
   }

   public static boolean startsWithAnyOf(String value, String... prefixes) {
      for(String prefix : prefixes) {
         if (value.startsWith(prefix)) {
            return true;
         }
      }

      return false;
   }

   public static int indexOfAnyOrLength(String value, String... searches) {
      return indexOfAnyOrLength(value, searches, 0);
   }

   public static int indexOfAnyOrLength(String value, String[] searches, int fromIndex) {
      if (fromIndex == -1) {
         return value.length();
      } else {
         int index;
         return (index = StringUtils.indexOfAny(fromIndex > 0 ? value.substring(fromIndex) : value, searches)) >= 0 ? index + fromIndex : value.length();
      }
   }

   public static boolean containsAllOf(String value, String... searches) {
      for (String s : searches) {
         if (value.indexOf(s) == -1) {
            return false;
         }
      }

      return true;
   }

   public static boolean containsAllOf(String value, List<String> searches) {
      for (String s : searches) {
         if (value.indexOf(s) == -1) {
            return false;
         }
      }

      return true;
   }

   public static String format(Set<String> lines) {
      StringBuilder out = new StringBuilder(64);

      for (String line : lines) {
         out.append(line).append('\n');
      }

      return out.toString();
   }

   public static String rtrim(String value, char... trims) {
      int length;
      for(length = value.length(); length > 0 && ArrayUtils.contains(trims, value.charAt(length - 1)); --length) {
      }

      return value.substring(0, length);
   }

   public static Integer firstChar(String value, char ch) {
      int index;
      return (index = value.indexOf(ch)) != -1 ? index + 1 : -1;
   }

   public static String rawdecode(String value, String encoding) {
      if (StringUtils.isEmpty(value)) {
         return value;
      } else {
         int length = value.length();
         ByteArrayOutputStream buffer = new ByteArrayOutputStream(length);

         try {
            for(int i = 0; i < length; ++i) {
               if (value.charAt(i) == '%' && i + 2 < length) {
                  int highNibble = value.charAt(i + 1);
                  int lowNibble = value.charAt(i + 2);
                  highNibble = Character.digit((char)highNibble, 16);
                  lowNibble = Character.digit((char)lowNibble, 16);
                  if (highNibble != -1 && lowNibble != -1) {
                     int decoded = (char)((highNibble << 4) + lowNibble);
                     buffer.write(decoded);
                     i += 2;
                  } else {
                     writeChar(buffer, value, i, encoding);
                  }
               } else {
                  writeChar(buffer, value, i, encoding);
               }
            }
         } finally {
            try {
               buffer.close();
            } catch (IOException ignore) {
            }

         }

         return new String(buffer.toByteArray(), Charset.forName(encoding));
      }
   }

   private static void writeChar(ByteArrayOutputStream buffer, String value, int index, String encoding) {
      char ch = value.charAt(index);
      String stringValue = new String(new char[]{ch});

      try {
         buffer.write(stringValue.getBytes(encoding));
      } catch (IOException ignore) {
         buffer.write(ch);
      }
   }

   public static byte[] charToBytesUTFCustom(char ch) {
      byte[] out;
      (out = new byte[2])[0] = (byte)(ch >> 8);
      out[1] = (byte)ch;
      return out;
   }

   public static String rawdecode(String value) {
      return rawdecode(value, "UTF-8");
   }

   public static String replaceAll(String value, Pattern pattern, String replacement) {
      return pattern.matcher(value).replaceAll(replacement);
   }

   static {
      LoggerFactory.getLogger(StringMatchUtils.class);
      NULL_STRING = null;
   }
}
