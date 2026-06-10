package com.scientiamobile.wurfl.core.utils;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Aho-Corasick 算法的 Trie 树节点实现。
 * <p>每个节点维护一个字符到子节点的映射（使用 {@link TreeMap} 保证有序），
 * 一个指向失败回退节点的引用，以及一个标识该节点是否为某个关键词结尾的标记。
 * 该类是整个 Aho-Corasick 多模式匹配算法的核心数据结构组成部分。</p>
 */

final class AcTrieNode {

    /**
     * 失败回退指针，匹配失败时跳转到的另一节点
     */
    private AcTrieNode fail;

    /**
     * 标记当前节点是否为某个关键词的结束节点
     */
    private final boolean keywordEnd;

    /** 子节点映射表，键为转移字符，值为对应的子节点 */
    private final TreeMap<Character, AcTrieNode> transitions;

    /**
     * 创建一个非关键词结尾的根节点或中间节点。
     */
    public AcTrieNode() {
        this(false);
    }

    /**
     * 创建一个指定是否为关键词结尾的节点。
     *
     * @param keywordEnd 如果为 {@code true}，表示从根到该节点的路径构成一个完整的关键词
     */
    private AcTrieNode(boolean keywordEnd) {
        this.keywordEnd = keywordEnd;
        this.fail = null;
        this.transitions = new TreeMap<>();
    }

    /**
     * 向 Trie 树中添加一个关键词模式。
     * <p>逐字符向下创建或复用子节点，并在模式最后一个字符对应的节点上标记为关键词结束。</p>
     *
     * @param pattern 待添加的关键词字符串
     */
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

    /**
     * 获取当前节点所有出边的字符到子节点的有序映射。
     *
     * @return 字符到子节点的 SortedMap
     */
    public SortedMap<Character, AcTrieNode> getOutgoingMap() {
        return this.transitions;
    }

    /**
     * 判断当前节点是否为某个关键词的结束节点。
     *
     * @return 如果是关键词结尾则返回 {@code true}
     */
    public boolean isKeywordEnd() {
        return this.keywordEnd;
    }

    /**
     * 获取指定字符对应的子节点。
     *
     * @param c 转移字符
     * @return 对应的子节点，如果不存在则返回 {@code null}
     */
    public AcTrieNode getNext(char c) {
        return this.transitions.get(c);
    }

    /**
     * 获取当前节点所有子节点对应的转移字符集合。
     *
     * @return 所有可用转移字符的集合
     */
    public Set<Character> getNextChars() {
        return this.transitions.keySet();
    }

    /**
     * 获取当前节点的失败回退指针。
     *
     * @return 失败时跳转的节点
     */
    public AcTrieNode getFail() {
        return this.fail;
    }

    /**
     * 设置当前节点的失败回退指针。
     *
     * @param fail 失败时跳转的目标节点
     */
    public void setFail(AcTrieNode fail) {
        this.fail = fail;
    }
}
