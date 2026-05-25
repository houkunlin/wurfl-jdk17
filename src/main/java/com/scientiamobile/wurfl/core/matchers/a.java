package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
abstract class a extends AbstractMatcher {
   public a() {
      super();
   }

   public a(WURFLModel var1) {
      super(var1);
   }

   public a(UserAgentNormalizer var1) {
      super(var1);
   }

   public a(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }
}
