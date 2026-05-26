package com.scientiamobile.wurfl.core;

public final class MarkUp {
   public static final MarkUp WML = new MarkUp("WML", -1);
   public static final MarkUp CHTML = new MarkUp("CHTML", 0);
   public static final MarkUp XHTML_SIMPLE = new MarkUp("XHTML_SIMPLE", 1);
   public static final MarkUp XHTML_ADVANCED = new MarkUp("XHTML_ADVANCED", 2);
   private int value;
   private String name;

   public final String toString() {
      return this.name;
   }

   public final int toValue() {
      return this.value;
   }

   private MarkUp(String name, int value) {
      this.name = name;
      this.value = value;
   }
}
