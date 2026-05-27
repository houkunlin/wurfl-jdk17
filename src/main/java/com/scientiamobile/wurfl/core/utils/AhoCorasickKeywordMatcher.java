package com.scientiamobile.wurfl.core.utils;

import java.util.*;

public final class AhoCorasickKeywordMatcher {
    private final int[] failStateByState;
    private final boolean[] terminalByState;
    private final char[][] transitionCharsByState;
    private final int[][] transitionTargetsByState;

    public AhoCorasickKeywordMatcher(List<String> keywords) {
        ArrayList<AcTrieNode> nodes = new ArrayList<>();
        AcTrieNode root = new AcTrieNode();

        for (String s : keywords) {
            String keyword;
            keyword = s;
            if (keyword != null && !keyword.isEmpty()) {
                root.addPattern(keyword);
            }
        }

        LinkedList<AcTrieNode> queue = new LinkedList<>();
        root.setFail(root);
        nodes.add(root);

        for (int i = 0; i < root.getOutgoingCount(); ++i) {
            AcTrieNode child = root.getOutgoingAt(i);
            if (child != null) {
                child.setFail(root);
                queue.add(child);
            }
        }

        while (!queue.isEmpty()) {
            AcTrieNode current = queue.poll();
            nodes.add(current);

            for (Character c : current.getNextChars()) {
                AcTrieNode next = current.getNext(c);
                queue.add(next);

                AcTrieNode failure = current.getFail();
                while (failure != root && failure.getNext(c) == null) {
                    failure = failure.getFail();
                }
                if (failure.getNext(c) != null) {
                    failure = failure.getNext(c);
                }
                next.setFail(failure);
            }
        }

        this.failStateByState = new int[nodes.size()];
        this.terminalByState = new boolean[nodes.size()];
        this.transitionCharsByState = new char[nodes.size()][];
        this.transitionTargetsByState = new int[nodes.size()][];

        for (int i = 0; i < nodes.size(); ++i) {
            AcTrieNode node = nodes.get(i);
            this.failStateByState[i] = nodes.indexOf(node.getFail());
            this.terminalByState[i] = node.isKeywordEnd();
            Set<Map.Entry<Character, AcTrieNode>> entrySet = node.getOutgoingMap().entrySet();
            this.transitionCharsByState[i] = new char[entrySet.size()];
            this.transitionTargetsByState[i] = new int[entrySet.size()];
            int j = 0;

            for (Map.Entry<Character, AcTrieNode> entry : entrySet) {
                this.transitionCharsByState[i][j] = entry.getKey();
                this.transitionTargetsByState[i][j] = nodes.indexOf(entry.getValue());
                ++j;
            }
        }

    }

    private static final int KEYWORD_FOUND = -1;

    public boolean matchesAny(String input) {
        char[] chars = input.toLowerCase(Locale.ENGLISH).toCharArray();
        int state = 0;

        for (char c : chars) {
            state = advanceState(c, state);
            if (state == KEYWORD_FOUND) {
                return true;
            }
        }
        return false;
    }

    private int advanceState(char c, int state) {
        while (true) {
            char[] available = this.transitionCharsByState[state];
            for (int i = 0; i < available.length; i++) {
                if (available[i] == c) {
                    int next = this.transitionTargetsByState[state][i];
                    return this.terminalByState[next] ? KEYWORD_FOUND : next;
                }
            }
            if (state == 0) {
                return 0;
            }
            state = this.failStateByState[state];
        }
    }
}
