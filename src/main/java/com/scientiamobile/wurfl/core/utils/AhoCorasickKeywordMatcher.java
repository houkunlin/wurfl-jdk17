package com.scientiamobile.wurfl.core.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class AhoCorasickKeywordMatcher {
   private int[] failStateByState;
   private boolean[] terminalByState;
   private char[][] transitionCharsByState;
   private int[][] transitionTargetsByState;

   public AhoCorasickKeywordMatcher(List<String> keywords) {
      ArrayList<AcTrieNode> nodes = new ArrayList<>();
      AcTrieNode root = new AcTrieNode();
      Iterator<String> it = keywords.iterator();

      while(it.hasNext()) {
         String keyword;
         if ((keyword = it.next()) != null && keyword.length() != 0) {
            root.addPattern(keyword);
         }
      }

      LinkedList<AcTrieNode> queue = new LinkedList<>();
      root.setFail(root);
      nodes.add(root);

      for(int i = 0; i < root.getOutgoingCount(); ++i) {
         AcTrieNode child;
         (child = root.getOutgoingAt(i)).setFail(root);
         queue.add(child);
      }

      while(!queue.isEmpty()) {
         AcTrieNode current = queue.poll();
         nodes.add(current);

         for(Character c : current.getNextChars()) {
            AcTrieNode next = current.getNext(c);
            queue.add(next);

            AcTrieNode failure;
            for(failure = current.getFail(); failure.getNext(c) == null && failure != root; failure = failure.getFail()) {
            }

            if ((failure = failure.getNext(c)) != null) {
               next.setFail(failure);
            } else {
               next.setFail(root);
            }
         }
      }

      this.failStateByState = new int[nodes.size()];
      this.terminalByState = new boolean[nodes.size()];
      this.transitionCharsByState = new char[nodes.size()][];
      this.transitionTargetsByState = new int[nodes.size()][];

      for(int i = 0; i < nodes.size(); ++i) {
         AcTrieNode node = nodes.get(i);
         this.failStateByState[i] = nodes.indexOf(node.getFail());
         this.terminalByState[i] = node.isKeywordEnd();
         Set<Map.Entry<Character, AcTrieNode>> entrySet = node.getOutgoingMap().entrySet();
         this.transitionCharsByState[i] = new char[entrySet.size()];
         this.transitionTargetsByState[i] = new int[entrySet.size()];
         int j = 0;

         for(Map.Entry<Character, AcTrieNode> entry : entrySet) {
            this.transitionCharsByState[i][j] = entry.getKey();
            this.transitionTargetsByState[i][j] = nodes.indexOf(entry.getValue());
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
            char[] availableChars = this.transitionCharsByState[state];

            for(int i = 0; i < availableChars.length; ++i) {
               if (availableChars[i] == c) {
                  state = this.transitionTargetsByState[state][i];
                  if (this.terminalByState[state]) {
                     return true;
                  }
                  continue label34;
               }
            }

            if (state == 0) {
               break;
            }

            state = this.failStateByState[state];
         }
      }

      return false;
   }
}
