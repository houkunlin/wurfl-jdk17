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

   @Override
   public DeviceInfo match(WURFLRequest request) {
      Iterator<Matcher> matcherIterator = this.matchers.iterator();

      while(matcherIterator.hasNext()) {
         Matcher matcher;
         (matcher = matcherIterator.next()).getMatcherName();
         if (matcher.canHandle(request)) {
            return matcher.match(request);
         }
      }

      if (this.logger.isWarnEnabled()) {
         this.logger.warn("No any matcher can handle the request: " + request + ", returning generic device.");
      }

      return new DeviceInfo("generic", MatchType.none, this.getMatcherName(), "MatcherChain", request.getOriginalUserAgent(), "");
   }

   @Override
   public boolean canHandle(WURFLRequest request) {
      return true;
   }

   @Override
   public String normalize(String userAgent) {
      return userAgent;
   }

   public MatcherFilter getFilter() {
      return this;
   }

   @Override
   public String getMatcherName() {
      return "MatcherChain";
   }

   public final boolean recordMatch(WURFLRequest request, String deviceId) {
      Iterator<MatcherFilter> filterIterator = this.filters.iterator();

      while(filterIterator.hasNext()) {
         MatcherFilter filter;
         if ((filter = filterIterator.next()).canHandle(request)) {
            filter.recordMatch(request, deviceId);
            return true;
         }
      }

      return false;
   }

   public final FilteredDeviceIndex getIndex() {
      this.logger.warn("A Filter of type MatcherChain should never be asked for its FilteredDevices set.");
      FilteredDeviceIndex filteredDeviceIndex = new FilteredDeviceIndex(this);
      Iterator<MatcherFilter> filterIterator = this.filters.iterator();

      while(filterIterator.hasNext()) {
         MatcherFilter filter = filterIterator.next();
         for(Object userAgentObj : filter.getIndex().getUserAgents()) {
            String userAgent = (String)userAgentObj;
            filteredDeviceIndex.put(userAgent, filter.getIndex().getDeviceIdByUserAgent(userAgent));
         }
      }

      return filteredDeviceIndex;
   }

   public final void sortAll() {
      Iterator<MatcherFilter> filterIterator = this.filters.iterator();

      while(filterIterator.hasNext()) {
         MatcherFilter filter;
         if ((filter = filterIterator.next()) instanceof MatcherChain) {
            ((MatcherChain)filter).sortAll();
         } else {
            filter.getIndex().sortUserAgents();
         }
      }

   }
}
