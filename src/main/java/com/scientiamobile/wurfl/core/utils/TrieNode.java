package com.scientiamobile.wurfl.core.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

final class TrieNode {
   private TrieNode failureLink;
   private boolean terminal;
   private TreeMap transitions;

   public TrieNode() {
      this(false);
   }

   private TrieNode(boolean terminal) {
      this.terminal = terminal;
      this.failureLink = null;
      this.transitions = new TreeMap();
   }

   public final void addKeyword(String keyword) {
      TrieNode current = this;

      while(true) {
         TrieNode next;
         if ((next = (TrieNode)current.transitions.get(keyword.charAt(0))) == null) {
            if (keyword.length() == 1) {
               next = new TrieNode(true);
               current.transitions.put(keyword.charAt(0), next);
               return;
            }

            next = new TrieNode(false);
            current.transitions.put(keyword.charAt(0), next);
            keyword = keyword.substring(1);
            current = next;
         } else {
            if (keyword.length() <= 1) {
               return;
            }

            keyword = keyword.substring(1);
            current = next;
         }
      }
   }

   public final TreeMap getTransitions() {
      return this.transitions;
   }

   public final boolean isTerminal() {
      return this.terminal;
   }

   public final int getTransitionCount() {
      return this.transitions.size();
   }

   public final TrieNode getTransitionAt(int index) {
      Iterator it = this.transitions.entrySet().iterator();
      int i = 0;

      while(it.hasNext()) {
         if (i == index) {
            return (TrieNode)((Map.Entry)it.next()).getValue();
         }

         ++i;
         it.next();
      }

      return null;
   }

   public final TrieNode getTransition(char c) {
      return (TrieNode)this.transitions.get(c);
   }

   public final Set getTransitionChars() {
      return this.transitions.keySet();
   }

   public final void setFailureLink(TrieNode failureLink) {
      this.failureLink = failureLink;
   }

   public final TrieNode getFailureLink() {
      return this.failureLink;
   }
}

