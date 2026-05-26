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

   public UserAgentNormalizerChain(List<UserAgentNormalizer> normalizers) {
      this.log = LoggerFactory.getLogger(this.getClass());
      this.normalizers = new ArrayList<>();
      this.normalizers.addAll(normalizers);
   }

   public UserAgentNormalizerChain(UserAgentNormalizer[] normalizers) {
      this(Arrays.asList(normalizers));
   }

   public UserAgentNormalizerChain add(UserAgentNormalizer normalizer) {
      ArrayList<UserAgentNormalizer> newNormalizers = new ArrayList<>(this.normalizers);
      newNormalizers.add(normalizer);
      return new UserAgentNormalizerChain(newNormalizers);
   }

   public String normalize(String rawUserAgent) {
      UserAgentWithNeedleCount needleCount = UserAgentUtils.getAsciiPrintableStringWithNeedleCount(new StringBuilder(rawUserAgent));
      String userAgent = needleCount.getAsciiPrintableUserAgent();
      if (!needleCount.hasSpaceChars() && needleCount.getPlusCharCount() > 2) {
         userAgent = plusToSpaceAndMarkEncoded(userAgent, (WURFLRequest)null);
      }

      if (needleCount.getPercentageCharCount() > 2) {
         userAgent = rawDecodeIfNeeded(userAgent, (WURFLRequest)null);
      }

      return this.applyChain(userAgent);
   }

   public String normalize(String rawUserAgent, WURFLRequest request) {
      UserAgentWithNeedleCount needleCount = UserAgentUtils.getAsciiPrintableStringWithNeedleCount(new StringBuilder(rawUserAgent));
      String userAgent = needleCount.getAsciiPrintableUserAgent();
      if (!needleCount.hasSpaceChars() && needleCount.getPlusCharCount() > 2) {
         userAgent = plusToSpaceAndMarkEncoded(userAgent, request);
      }

      if (needleCount.getPercentageCharCount() > 2) {
         userAgent = rawDecodeIfNeeded(userAgent, request);
      }

      return this.applyChain(userAgent);
   }

   private String applyChain(String userAgent) {
      for(Iterator<UserAgentNormalizer> iterator = this.normalizers.iterator(); iterator.hasNext(); userAgent = iterator.next().normalize(userAgent)) {
      }

      return userAgent;
   }

   private String rawDecodeIfNeeded(String userAgent, WURFLRequest request) {
      try {
         userAgent = StringMatchUtils.rawdecode(userAgent);
         if (request != null) {
            request.setUrlEncoded(true);
         }
      } catch (Exception e) {
         this.log.warn("rawdecoding for user agent " + userAgent + " failed", e);
      }

      return userAgent;
   }

   private static String plusToSpaceAndMarkEncoded(String userAgent, WURFLRequest request) {
      if (request != null) {
         request.setUrlEncoded(true);
      }

      return userAgent.replace("+", " ");
   }

   public List<UserAgentNormalizer> getAllNormalizers() {
      return Collections.unmodifiableList(this.normalizers);
   }
}
