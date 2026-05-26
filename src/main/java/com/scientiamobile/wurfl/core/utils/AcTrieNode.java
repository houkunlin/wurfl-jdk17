package com.scientiamobile.wurfl.core.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

final class AcTrieNode {
   private AcTrieNode fail;
   private boolean keywordEnd;
   private TreeMap<Character, AcTrieNode> transitions;

   public AcTrieNode() {
      this(false);
   }

   private AcTrieNode(boolean keywordEnd) {
      this.keywordEnd = keywordEnd;
      this.fail = null;
      this.transitions = new TreeMap<>();
   }

   public final void addPattern(String pattern) {
      AcTrieNode current = this;

      while(true) {
         AcTrieNode next;
         if ((next = current.transitions.get(pattern.charAt(0))) == null) {
            if (pattern.length() == 1) {
               next = new AcTrieNode(true);
               current.transitions.put(pattern.charAt(0), next);
               return;
            }

            next = new AcTrieNode(false);
            current.transitions.put(pattern.charAt(0), next);
            pattern = pattern.substring(1);
            current = next;
         } else {
            if (pattern.length() <= 1) {
               return;
            }

            pattern = pattern.substring(1);
            current = next;
         }
      }
   }

   public final TreeMap<Character, AcTrieNode> getOutgoingMap() {
      return this.transitions;
   }

   public final boolean isKeywordEnd() {
      return this.keywordEnd;
   }

   public final int getOutgoingCount() {
      return this.transitions.size();
   }

   public final AcTrieNode getOutgoingAt(int index) {
      Iterator<Map.Entry<Character, AcTrieNode>> it = this.transitions.entrySet().iterator();
      int i = 0;

      while(it.hasNext()) {
         if (i == index) {
            return (AcTrieNode)((Map.Entry)it.next()).getValue();
         }

         ++i;
         it.next();
      }

      return null;
   }

   public final AcTrieNode getNext(char c) {
      return this.transitions.get(c);
   }

   public final Set<Character> getNextChars() {
      return this.transitions.keySet();
   }

   public final void setFail(AcTrieNode fail) {
      this.fail = fail;
   }

   public final AcTrieNode getFail() {
      return this.fail;
   }
}
