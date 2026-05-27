package com.scientiamobile.wurfl.core.utils;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

final class AcTrieNode {
    private AcTrieNode fail;
    private final boolean keywordEnd;
    private final TreeMap<Character, AcTrieNode> transitions;

    public AcTrieNode() {
        this(false);
    }

    private AcTrieNode(boolean keywordEnd) {
        this.keywordEnd = keywordEnd;
        this.fail = null;
        this.transitions = new TreeMap<>();
    }

    public void addPattern(String pattern) {
        AcTrieNode current = this;
        while (true) {
            char firstChar = pattern.charAt(0);
            AcTrieNode next = current.transitions.get(firstChar);
            if (next == null) {
                boolean isEnd = pattern.length() == 1;
                next = new AcTrieNode(isEnd);
                current.transitions.put(firstChar, next);
                if (isEnd) return;
            } else if (pattern.length() == 1) {
                return;
            }
            pattern = pattern.substring(1);
            current = next;
        }
    }

    public SortedMap<Character, AcTrieNode> getOutgoingMap() {
        return this.transitions;
    }

    public boolean isKeywordEnd() {
        return this.keywordEnd;
    }

    public int getOutgoingCount() {
        return this.transitions.size();
    }

    public AcTrieNode getOutgoingAt(int index) {
        int i = 0;
        for (AcTrieNode node : this.transitions.values()) {
            if (i == index) {
                return node;
            }
            i++;
        }
        return null;
    }

    public AcTrieNode getNext(char c) {
        return this.transitions.get(c);
    }

    public Set<Character> getNextChars() {
        return this.transitions.keySet();
    }

    public AcTrieNode getFail() {
        return this.fail;
    }

    public void setFail(AcTrieNode fail) {
        this.fail = fail;
    }
}
