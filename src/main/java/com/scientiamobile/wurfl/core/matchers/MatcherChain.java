package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of Matcher Chain.
 */

class MatcherChain implements Matcher, MatcherFilter {
    private static final Logger logger = LoggerFactory.getLogger(MatcherChain.class);
    private List<Matcher> matchers = new LinkedList<>();
    private List<MatcherFilter> filters = new LinkedList<>();

    /**
     * Ad datcher.
     */

    public final void addMatcher(Matcher matcher) {
        this.matchers.add(matcher);
        this.filters.add(matcher.getFilter());
    }

    @Override
/**
 * Attempts to match the given request to a device.
 * @param request the WURFL request
 * @return device info for the matched device
 */

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
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        return true;
    }

    @Override
/**
 * Normalizes the given User-Agent string.
 * @param userAgent the raw User-Agent string
 * @return the normalized User-Agent string
 */

    public String normalize(String userAgent) {
        return userAgent;
    }

    public MatcherFilter getFilter() {
        return this;
    }

    @Override
/**
 * Returns the matche rame.
 */

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

    /**
     * Returns the index.
 */

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

    /**
     * Sor tll.
 */

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
