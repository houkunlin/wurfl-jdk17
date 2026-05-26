package com.scientiamobile.wurfl.core.resource.exc;

public class GenericNotDefinedException extends WURFLConsistencyException {
   private static final long serialVersionUID = 10L;

   public GenericNotDefinedException() {
      super((new StringBuilder("Device: ")).append("generic").append(" is not defined").toString());
   }
}
