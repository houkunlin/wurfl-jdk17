package com.scientiamobile.wurfl.core;

public final class MarkUp {
   public static final MarkUp WML = new MarkUp("WML", -1);
   public static final MarkUp CHTML = new MarkUp("CHTML", 0);
   public static final MarkUp XHTML_SIMPLE = new MarkUp("XHTML_SIMPLE", 1);
   public static final MarkUp XHTML_ADVANCED = new MarkUp("XHTML_ADVANCED", 2);
   private int a;
   private String b;

   public final String toString() {
      return this.b;
   }

   public final int toValue() {
      return this.a;
   }

   private MarkUp(String var1, int var2) {
      this.b = var1;
      this.a = var2;
   }
}
