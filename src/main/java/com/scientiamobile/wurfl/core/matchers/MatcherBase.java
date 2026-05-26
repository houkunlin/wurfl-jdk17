package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

abstract class MatcherBase extends AbstractMatcher {
   public MatcherBase() {
      super();
   }

   public MatcherBase(WURFLModel wurflModel) {
      super(wurflModel);
   }

   public MatcherBase(UserAgentNormalizer normalizer) {
      super(normalizer);
   }

   public MatcherBase(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
      super(normalizer, wurflModel);
   }
}
