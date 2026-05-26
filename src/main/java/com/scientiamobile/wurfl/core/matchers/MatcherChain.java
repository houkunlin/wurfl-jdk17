package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MatcherChain implements Matcher, MatcherFilter {
   private List<Matcher> matchers = new LinkedList<>();
   private final Logger logger = LoggerFactory.getLogger(MatcherChain.class);
   private List<MatcherFilter> filters = new LinkedList<>();

   public final void addMatcher(Matcher matcher) {
      this.matchers.add(matcher);
      this.filters.add(matcher.getFilter());
   }

   public DeviceInfo match(WURFLRequest request) {
      Iterator<Matcher> var2 = this.matchers.iterator();

      while(var2.hasNext()) {
         Matcher var3;
         (var3 = var2.next()).getMatcherName();
         if (var3.canHandle(request)) {
            return var3.match(request);
         }
      }

      if (this.logger.isWarnEnabled()) {
         this.logger.warn("No any matcher can handle the request: " + request + ", returning generic device.");
      }

      return new DeviceInfo("generic", MatchType.none, this.getMatcherName(), "MatcherChain", request.getOriginalUserAgent(), "");
   }

   public boolean canHandle(WURFLRequest request) {
      return true;
   }

   public String normalize(String userAgent) {
      return userAgent;
   }

   public MatcherFilter getFilter() {
      return this;
   }

   public String getMatcherName() {
      return "MatcherChain";
   }

   public final boolean recordMatch(WURFLRequest request, String deviceId) {
      Iterator<MatcherFilter> var3 = this.filters.iterator();

      while(var3.hasNext()) {
         MatcherFilter var4;
         if ((var4 = var3.next()).canHandle(request)) {
            var4.recordMatch(request, deviceId);
            return true;
         }
      }

      return false;
   }

   public final FilteredDeviceIndex getIndex() {
      this.logger.warn("A Filter of type MatcherChain should never be asked for its FilteredDevices set.");
      FilteredDeviceIndex var1 = new FilteredDeviceIndex(this);
      Iterator<MatcherFilter> var2 = this.filters.iterator();

      while(var2.hasNext()) {
         MatcherFilter var3;
         for(Object userAgentObj : (var3 = var2.next()).getIndex().getUserAgents()) {
            String userAgent = (String)userAgentObj;
            var1.put(userAgent, var3.getIndex().getDeviceIdByUserAgent(userAgent));
         }
      }

      return var1;
   }

   public final void sortAll() {
      Iterator<MatcherFilter> var1 = this.filters.iterator();

      while(var1.hasNext()) {
         MatcherFilter var2;
         if ((var2 = var1.next()) instanceof MatcherChain) {
            ((MatcherChain)var2).sortAll();
         } else {
            var2.getIndex().sortUserAgents();
         }
      }

   }
}
