package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class NameVersionPair implements Serializable {
   private static final long serialVersionUID = 4934582187956400034L;
   private String name = null;
   private String version = null;
   private String[] lastRegexGroups;

   public final String getName() {
      return this.name;
   }

   public final void setName(String name) {
      this.name = name;
   }

   public final String getVersion() {
      return this.version;
   }

   public final void setVersion(String version) {
      this.version = version;
   }

   public NameVersionPair() {
   }

   public final boolean matchAndSet(Pattern pattern, String input, String matchedName, String matchedVersion) {
      if (this.find(pattern, input) != null) {
         this.name = matchedName.trim();
         if (matchedVersion != null) {
            this.version = matchedVersion.trim();
         }

         return true;
      } else {
         this.lastRegexGroups = null;
         return false;
      }
   }

   public final boolean matchAndSetNameFromGroup(Pattern pattern, String input, int nameGroupIndex) {
      Matcher matcher;
      matcher = this.find(pattern, input);
      if (matcher != null) {
         this.name = matcher.group(nameGroupIndex) == null ? "" : matcher.group(nameGroupIndex).trim();
         return true;
      } else {
         this.lastRegexGroups = null;
         return false;
      }
   }

   public final boolean matchAndSetVersionFromGroup(Pattern pattern, String input, int versionGroupIndex) {
      Matcher matcher;
      matcher = this.find(pattern, input);
      if (matcher != null) {
         this.version = matcher.group(versionGroupIndex) == null ? "" : matcher.group(versionGroupIndex).trim();
         return true;
      } else {
         this.lastRegexGroups = null;
         return false;
      }
   }

   public final boolean matchAndSetGroup(Pattern pattern, String input, String matchedName, int versionGroupIndex) {
      Matcher matcher;
      matcher = this.find(pattern, input);
      if (matcher != null) {
         if (matchedName != null) {
            this.name = matchedName.trim();
         }

         String version = matcher.group(versionGroupIndex);
         this.version = version == null ? "" : version.trim();
         return true;
      } else {
         this.lastRegexGroups = null;
         return false;
      }
   }

   public final boolean matchAndSetNameAndGroup(Pattern pattern, String input, int nameGroupIndex) {
      Matcher matcher;
      matcher = this.find(pattern, input);
      if (matcher != null) {
         this.name = matcher.group(1) == null ? "" : matcher.group(1).trim();
         this.version = matcher.group(nameGroupIndex) == null ? "" : matcher.group(nameGroupIndex).trim();
         return true;
      } else {
         this.lastRegexGroups = null;
         return false;
      }
   }

   public final boolean containsAndSetName(String input, String needle, String matchedName) {
      if (StringMatchUtils.indexOf(input, needle) >= 0) {
         this.name = matchedName.trim();
         return true;
      } else {
         return false;
      }
   }

   private Matcher find(Pattern pattern, String input) {
      Matcher matcher;
matcher = pattern.matcher(input);
if (!matcher.find()) {
         this.lastRegexGroups = null;
         return null;
      } else {
         this.lastRegexGroups = new String[matcher.groupCount() + 1];

         for(int i = 0; i < this.lastRegexGroups.length; ++i) {
            this.lastRegexGroups[i] = matcher.group(i);
         }

         return matcher;
      }
   }

   public final String getGroup(int groupIndex) {
      return this.lastRegexGroups == null ? null : this.lastRegexGroups[groupIndex];
   }

   @Override
   public String toString() {
      return "[name: " + this.name + " - version: " + this.version + "]";
   }
}
