package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

abstract class MatcherBase extends AbstractMatcher {
   public MatcherBase() {
      super();
   }

   public MatcherBase(WURFLModel var1) {
      super(var1);
   }

   public MatcherBase(UserAgentNormalizer var1) {
      super(var1);
   }

   public MatcherBase(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }
}
