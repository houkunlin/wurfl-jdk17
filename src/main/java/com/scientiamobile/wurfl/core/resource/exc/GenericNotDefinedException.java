package com.scientiamobile.wurfl.core.resource.exc;

import org.apache.commons.lang3.text.StrBuilder;

public class GenericNotDefinedException extends WURFLConsistencyException {
   private static final long serialVersionUID = 10L;

   public GenericNotDefinedException() {
      super((new StrBuilder("Device: ")).append("generic").append(" is not defined").toString());
   }
}
