package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentWithNeedleCount;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAgentNormalizerChain implements UserAgentNormalizer {
   private final transient Logger log;
   private final List<UserAgentNormalizer> normalizers;

   public UserAgentNormalizerChain() {
      this.log = LoggerFactory.getLogger(this.getClass());
      this.normalizers = new ArrayList<>();
   }

   public UserAgentNormalizerChain(List<UserAgentNormalizer> var1) {
      this.log = LoggerFactory.getLogger(this.getClass());
      this.normalizers = new ArrayList<>();
      this.normalizers.addAll(var1);
   }

   public UserAgentNormalizerChain(UserAgentNormalizer[] var1) {
      this(Arrays.asList(var1));
   }

   public UserAgentNormalizerChain add(UserAgentNormalizer var1) {
      ArrayList<UserAgentNormalizer> var2;
      (var2 = new ArrayList<>(this.normalizers)).add(var1);
      return new UserAgentNormalizerChain(var2);
   }

   public String normalize(String var1) {
      UserAgentWithNeedleCount var3;
      String var2 = (var3 = UserAgentUtils.getAsciiPrintableStringWithNeedleCount(new StringBuilder(var1))).getAsciiPrintableUserAgent();
      if (!var3.hasSpaceChars() && var3.getPlusCharCount() > 2) {
         var2 = plusToSpaceAndMarkEncoded(var2, (WURFLRequest)null);
      }

      if (var3.getPercentageCharCount() > 2) {
         var2 = rawDecodeIfNeeded(var2, (WURFLRequest)null);
      }

      return this.applyChain(var2);
   }

   public String normalize(String var1, WURFLRequest var2) {
      UserAgentWithNeedleCount var4;
      String var3 = (var4 = UserAgentUtils.getAsciiPrintableStringWithNeedleCount(new StringBuilder(var1))).getAsciiPrintableUserAgent();
      if (!var4.hasSpaceChars() && var4.getPlusCharCount() > 2) {
         var3 = plusToSpaceAndMarkEncoded(var3, var2);
      }

      if (var4.getPercentageCharCount() > 2) {
         var3 = rawDecodeIfNeeded(var3, var2);
      }

      return this.applyChain(var3);
   }

   private String applyChain(String var1) {
      for(Iterator<UserAgentNormalizer> var2 = this.normalizers.iterator(); var2.hasNext(); var1 = var2.next().normalize(var1)) {
      }

      return var1;
   }

   private String rawDecodeIfNeeded(String var1, WURFLRequest var2) {
      try {
         var1 = StringMatchUtils.rawdecode(var1);
         if (var2 != null) {
            var2.setUrlEncoded(true);
         }
      } catch (Exception var3) {
         this.log.warn("rawdecoding for user agent " + var1 + " failed", var3);
      }

      return var1;
   }

   private static String plusToSpaceAndMarkEncoded(String var0, WURFLRequest var1) {
      if (var1 != null) {
         var1.setUrlEncoded(true);
      }

      return var0.replace("+", " ");
   }

   public List<UserAgentNormalizer> getAllNormalizers() {
      return Collections.unmodifiableList(this.normalizers);
   }
}
