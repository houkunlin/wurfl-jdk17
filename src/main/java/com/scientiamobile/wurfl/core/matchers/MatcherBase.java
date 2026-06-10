package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

/**
 * Matcher implementation for identifying  devices and browsers.
 */

abstract class MatcherBase extends AbstractMatcher {
    protected MatcherBase() {
        super();
    }

    protected MatcherBase(WURFLModel wurflModel) {
        super(wurflModel);
    }

    protected MatcherBase(UserAgentNormalizer normalizer) {
        super(normalizer);
    }

    protected MatcherBase(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
        super(normalizer, wurflModel);
    }
}
