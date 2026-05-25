package com.scientiamobile.wurfl.core.resource;

import java.io.Serializable;
import java.util.Comparator;

final class ModelDeviceUserAgentComparator implements Serializable, Comparator<ModelDevice> {
   private static final long serialVersionUID = 101L;
   static final ModelDeviceUserAgentComparator INSTANCE = new ModelDeviceUserAgentComparator();

   private ModelDeviceUserAgentComparator() {
   }

   public final int compare(ModelDevice left, ModelDevice right) {
      return left.getUserAgent().compareTo(right.getUserAgent());
   }
}

