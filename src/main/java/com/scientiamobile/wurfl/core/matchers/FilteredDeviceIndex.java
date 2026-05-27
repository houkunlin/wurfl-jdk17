package com.scientiamobile.wurfl.core.matchers;

import org.apache.commons.lang3.Validate;
import org.slf4j.LoggerFactory;

import java.util.*;

final class FilteredDeviceIndex {
    private final MatcherFilter ownerFilter;
    private SortedMap<String, String> userAgentToDeviceId;
    private List<String> userAgents;

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
