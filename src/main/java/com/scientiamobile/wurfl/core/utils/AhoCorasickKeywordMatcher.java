package com.scientiamobile.wurfl.core.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class AhoCorasickKeywordMatcher {
   private int[] failureLinks;
   private boolean[] terminal;
   private char[][] transitionChars;
   private int[][] transitionTargets;

   public AhoCorasickKeywordMatcher(List keywords) {
      ArrayList nodes = new ArrayList();
      TrieNode root = new TrieNode();
      Iterator it = keywords.iterator();

      while(it.hasNext()) {
         String keyword;
         if ((keyword = (String)it.next()) != null && keyword.length() != 0) {
            root.addKeyword(keyword);
         }
      }

      LinkedList queue = new LinkedList();
      root.setFailureLink(root);
      nodes.add(root);

      for(int i = 0; i < root.getTransitionCount(); ++i) {
         TrieNode child;
         (child = root.getTransitionAt(i)).setFailureLink(root);
         queue.add(child);
      }

      while(!queue.isEmpty()) {
         TrieNode current = (TrieNode)queue.poll();
         nodes.add(current);

         for(Character c : current.getTransitionChars()) {
            TrieNode next = current.getTransition(c);
            queue.add(next);

            TrieNode failure;
            for(failure = current.getFailureLink(); failure.getTransition(c) == null && failure != root; failure = failure.getFailureLink()) {
            }

            if ((failure = failure.getTransition(c)) != null) {
               next.setFailureLink(failure);
            } else {
               next.setFailureLink(root);
            }
         }
      }

      this.failureLinks = new int[nodes.size()];
      this.terminal = new boolean[nodes.size()];
      this.transitionChars = new char[nodes.size()][];
      this.transitionTargets = new int[nodes.size()][];

      for(int i = 0; i < nodes.size(); ++i) {
         TrieNode node = (TrieNode)nodes.get(i);
         this.failureLinks[i] = nodes.indexOf(node.getFailureLink());
         this.terminal[i] = node.isTerminal();
         Set entrySet = node.getTransitions().entrySet();
         this.transitionChars[i] = new char[entrySet.size()];
         this.transitionTargets[i] = new int[entrySet.size()];
         int j = 0;

         for(Object entryObj : entrySet) {
            Map.Entry entry = (Map.Entry)entryObj;
            this.transitionChars[i][j] = (Character)entry.getKey();
            this.transitionTargets[i][j] = nodes.indexOf(entry.getValue());
            ++j;
         }
      }

   }

   public final boolean matchesAny(String input) {
      char[] chars = input.toLowerCase().toCharArray();
      int state = 0;

      label34:
      for(char c : chars) {
         while(true) {
            char[] availableChars = this.transitionChars[state];

            for(int i = 0; i < availableChars.length; ++i) {
               if (availableChars[i] == c) {
                  state = this.transitionTargets[state][i];
                  if (this.terminal[state]) {
                     return true;
                  }
                  continue label34;
               }
            }

            if (state == 0) {
               break;
            }

            state = this.failureLinks[state];
         }
      }

      return false;
   }
}

