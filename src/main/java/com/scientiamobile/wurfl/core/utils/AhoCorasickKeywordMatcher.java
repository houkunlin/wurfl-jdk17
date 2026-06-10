package com.scientiamobile.wurfl.core.utils;

import java.util.*;

/**
 * Matcher implementation for identifying Aho Corasick Keyword devices and browsers.
 */

public final class AhoCorasickKeywordMatcher {
    private final int[] failStateByState;
    private final boolean[] terminalByState;
    private final char[][] transitionCharsByState;
    private final int[][] transitionTargetsByState;

    public AhoCorasickKeywordMatcher(List<String> keywords) {
        AcTrieNode root = new AcTrieNode();
        for (String keyword : keywords) {
            if (keyword != null && !keyword.isEmpty()) {
                root.addPattern(keyword);
            }
        }
        List<AcTrieNode> nodes = buildFailureLinks(root);
        int size = nodes.size();

        this.failStateByState = new int[size];
        this.terminalByState = new boolean[size];
        this.transitionCharsByState = new char[size][];
        this.transitionTargetsByState = new int[size][];

        Map<AcTrieNode, Integer> nodeIndex = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            nodeIndex.put(nodes.get(i), i);
        }

        for (int i = 0; i < size; i++) {
            AcTrieNode node = nodes.get(i);
            this.failStateByState[i] = nodeIndex.get(node.getFail());
            this.terminalByState[i] = node.isKeywordEnd();
            Set<Map.Entry<Character, AcTrieNode>> entries = node.getOutgoingMap().entrySet();
            this.transitionCharsByState[i] = new char[entries.size()];
            this.transitionTargetsByState[i] = new int[entries.size()];
            int j = 0;
            for (Map.Entry<Character, AcTrieNode> entry : entries) {
                this.transitionCharsByState[i][j] = entry.getKey();
                this.transitionTargetsByState[i][j] = nodeIndex.get(entry.getValue());
                j++;
            }
        }
    }

    /**
     * Buil dailur einks.
     */

    private static List<AcTrieNode> buildFailureLinks(AcTrieNode root) {
        List<AcTrieNode> nodes = new ArrayList<>();
        root.setFail(root);
        nodes.add(root);

        LinkedList<AcTrieNode> queue = new LinkedList<>();
        for (Character c : root.getNextChars()) {
            AcTrieNode child = root.getNext(c);
            child.setFail(root);
            queue.add(child);
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
        return nodes;
    }

    private static final int KEYWORD_FOUND = -1;

    /**
     * Checks whether the input contains any of the registered keywords.
     * @param input the input string to check
     * @return true if any keyword is found
 */

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

    /**
     * Advanc etate.
 */

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
