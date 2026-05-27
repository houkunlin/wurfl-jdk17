package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

class MatcherChain implements Matcher, MatcherFilter {
    private static final Logger logger = LoggerFactory.getLogger(MatcherChain.class);
    private List<Matcher> matchers = new LinkedList<>();
    private List<MatcherFilter> filters = new LinkedList<>();

    public final void addMatcher(Matcher matcher) {
        this.matchers.add(matcher);
        this.filters.add(matcher.getFilter());
    }

    @Override
    public DeviceInfo match(WURFLRequest request) {

        for (Matcher value : this.matchers) {
            Matcher matcher;
            matcher = value;
            matcher.getMatcherName();
            if (matcher.canHandle(request)) {
                return matcher.match(request);
            }
        }

        if (logger.isWarnEnabled()) {
            logger.warn("No any matcher can handle the request: " + request + ", returning generic device.");
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

        for (MatcherFilter matcherFilter : this.filters) {
            MatcherFilter filter;
            filter = matcherFilter;
            if (filter.canHandle(request)) {
                filter.recordMatch(request, deviceId);
                return true;
            }
        }

        return false;
    }

    public final FilteredDeviceIndex getIndex() {
        logger.warn("A Filter of type MatcherChain should never be asked for its FilteredDevices set.");
        FilteredDeviceIndex filteredDeviceIndex = new FilteredDeviceIndex(this);

        for (MatcherFilter filter : this.filters) {
            for (String userAgent : filter.getIndex().getUserAgents()) {
                filteredDeviceIndex.put(userAgent, filter.getIndex().getDeviceIdByUserAgent(userAgent));
            }
        }

        return filteredDeviceIndex;
    }

    public final void sortAll() {
        for (MatcherFilter matcherFilter : this.filters) {
            MatcherFilter filter;
            filter = matcherFilter;
            if (filter instanceof MatcherChain chain) {
                chain.sortAll();
            } else {
                filter.getIndex().sortUserAgents();
            }
        }

    }
}
