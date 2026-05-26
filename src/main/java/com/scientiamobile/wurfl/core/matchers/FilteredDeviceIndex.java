package com.scientiamobile.wurfl.core.matchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.commons.lang3.Validate;
import org.slf4j.LoggerFactory;

final class FilteredDeviceIndex {
   private SortedMap<String, String> userAgentToDeviceId;
   private List<String> userAgents;
   private final MatcherFilter ownerFilter;

   public FilteredDeviceIndex(MatcherFilter ownerFilter) {
      LoggerFactory.getLogger(this.getClass());
      this.userAgentToDeviceId = new TreeMap<>();
      this.userAgents = new ArrayList<>();
      this.ownerFilter = ownerFilter;
   }

   public final Collection<String> getUserAgents() {
      return this.userAgents;
   }

   public final void sortUserAgents() {
      Collections.sort(this.userAgents);
   }

   public final String getDeviceIdByUserAgent(String userAgent) {
      Validate.notNull(userAgent, "The userAgent is empty");
      return this.userAgentToDeviceId.get(userAgent);
   }

   public final void put(String userAgent, String deviceId) {
      Validate.notNull(userAgent, "user-agent cannot be null");
      Validate.notEmpty(deviceId, "The deviceId is empty");
      this.userAgentToDeviceId.put(userAgent, deviceId);
      this.userAgents.add(userAgent);
   }

   @Override
   public String toString() {
      return this.ownerFilter.getMatcherName() + this.userAgentToDeviceId.values();
   }
}
