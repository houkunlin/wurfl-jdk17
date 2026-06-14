package com.scientiamobile.wurfl.core.utils;

import java.util.*;

/**
 * Aho-Corasick 多模式关键词匹配器。
 * <p>将一组关键词编译为一个确定有限自动机（DFA），
 * 可在 O(n) 时间复杂度内一次性检测输入文本是否包含任意一个注册的关键词，
 * 而无需为每个关键词单独进行字符串匹配。
 * WURFL 引擎使用该匹配器快速检测 User-Agent 中是否包含移动设备、
 * 桌面浏览器、智能电视或爬虫相关的关键词。</p>
 */

public final class AhoCorasickKeywordMatcher {

    /**
     * 每个状态的失败回退状态索引
     */
    private final int[] failStateByState;

    /**
     * 标记每个状态是否为某个关键词的终止状态
     */
    private final boolean[] terminalByState;

    /**
     * 每个终止状态对应的关键词（仅当 {@link #terminalByState} 对应位为 {@code true} 时有值）
     */
    private final String[] keywordByState;

    /**
     * 每个状态的可用转移字符列表（字符数组）
     */
    private final char[][] transitionCharsByState;

    /**
     * 每个状态的转移目标状态列表（与 transitionCharsByState 一一对应）
     */
    private final int[][] transitionTargetsByState;

    /**
     * 使用一组关键词构建 Aho-Corasick 自动机。
     * <p>首先构建 Trie 树，然后通过 BFS 计算每个节点的失败回退链接，
     * 最后将 Trie 树序列化为紧凑的数组结构，以提高运行时匹配效率。</p>
     *
     * @param keywords 关键词列表，null 或空字符串的元素将被忽略
     */
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
        this.keywordByState = new String[size];
        this.transitionCharsByState = new char[size][];
        this.transitionTargetsByState = new int[size][];

        // 建立节点对象到索引的映射（第二遍循环需要此映射来解析引用）
        Map<AcTrieNode, Integer> nodeIndex = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            AcTrieNode node = nodes.get(i);
            nodeIndex.put(node, i);
            this.failStateByState[i] = 0;
            this.terminalByState[i] = node.isKeywordEnd();
            this.keywordByState[i] = node.isKeywordEnd() ? node.getKeyword() : null;
        }

        // 将 Trie 树序列化为紧凑的并行数组结构
        for (int i = 0; i < size; i++) {
            AcTrieNode node = nodes.get(i);
            this.failStateByState[i] = nodeIndex.get(node.getFail());
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
     * 从根节点开始，通过 BFS 构建每个节点的失败回退链接。
     * <p>失败链接的作用是：当当前节点无法匹配输入字符时，自动机回退到
     * 另一个拥有最长相同后缀的节点继续匹配，从而实现线性时间内的多模式匹配。</p>
     *
     * @param root Trie 树根节点
     * @return BFS 遍历顺序的节点列表（根节点在第一个位置）
     */
    private static List<AcTrieNode> buildFailureLinks(AcTrieNode root) {
        List<AcTrieNode> nodes = new ArrayList<>();
        root.setFail(root);
        nodes.add(root);

        LinkedList<AcTrieNode> queue = new LinkedList<>();
        // 第一层子节点的失败链接直接指向根节点
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

                // 沿失败链接向上回溯，寻找能匹配当前字符的节点
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

    /**
     * 标记状态：表示已找到关键词（匹配终止）
     */
    private static final int KEYWORD_FOUND = -1;

    /**
     * 检查输入文本中是否包含任意已注册的关键词。
     * <p>先将输入转换为小写（关键词匹配不区分大小写），
     * 然后逐字符驱动自动机转移，一旦进入终止状态即返回对应的关键词。</p>
     *
     * @param input 待检查的输入字符串
     * @return 第一个匹配到的完整关键词，若无匹配返回 {@code null}
     */
    public String matchKeyword(String input) {
        String lower = input.toLowerCase(Locale.ENGLISH);
        int state = 0;

        for (int i = 0; i < lower.length(); i++) {
            state = advanceState(lower.charAt(i), state);
            if (state == KEYWORD_FOUND) {
                // 回退到上一个字符的状态，找到实际匹配的终止状态
                return findMatchedKeyword(lower, i);
            }
        }
        return null;
    }

    /**
     * 从当前位置回退查找匹配的具体关键词。
     * <p>在 {@link #advanceState} 返回 {@link #KEYWORD_FOUND} 后调用，
     * 从当前位置向前逐字符回溯，确定哪个关键词实际匹配。</p>
     */
    private String findMatchedKeyword(String input, int endPos) {
        int state = 0;
        for (int i = 0; i <= endPos; i++) {
            char c = input.charAt(i);
            int[] targets = this.transitionTargetsByState[state];
            char[] chars = this.transitionCharsByState[state];
            int nextState = -1;
            for (int j = 0; j < chars.length; j++) {
                if (chars[j] == c) {
                    nextState = targets[j];
                    break;
                }
            }
            if (nextState < 0) {
                // 回退到失败链接
                int fs = this.failStateByState[state];
                while (fs != 0) {
                    int[] ft = this.transitionTargetsByState[fs];
                    char[] fc = this.transitionCharsByState[fs];
                    for (int j = 0; j < fc.length; j++) {
                        if (fc[j] == c) {
                            nextState = ft[j];
                            break;
                        }
                    }
                    if (nextState >= 0) break;
                    fs = this.failStateByState[fs];
                }
                if (nextState < 0) {
                    state = 0;
                    continue;
                }
            }
            state = nextState;
            if (this.terminalByState[state]) {
                return this.keywordByState[state];
            }
            // 也检查失败链接上的终止状态
            int fs = this.failStateByState[state];
            while (fs != 0) {
                if (this.terminalByState[fs]) {
                    return this.keywordByState[fs];
                }
                fs = this.failStateByState[fs];
            }
        }
        return null;
    }

    /**
     * 检查输入文本中是否包含任意已注册的关键词。
     * <p>先将输入转换为小写（关键词匹配不区分大小写），
     * 然后逐字符驱动自动机转移，一旦进入终止状态即返回找到。</p>
     *
     * @param input 待检查的输入字符串
     * @return 如果包含任意已注册的关键词则返回 {@code true}
     */
    public boolean matchesAny(String input) {
        String lower = input.toLowerCase(Locale.ENGLISH);
        int state = 0;

        for (int i = 0; i < lower.length(); i++) {
            state = advanceState(lower.charAt(i), state);
            if (state == KEYWORD_FOUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据当前状态和输入字符，驱动自动机转移到下一个状态。
     * <p>如果当前状态有针对该字符的转移，则进行转移；
     * 否则回退到失败链接指向的状态继续尝试，直到回到根节点。
     * 若转移到的目标状态是终止状态，则返回 {@link #KEYWORD_FOUND}。</p>
     *
     * @param c     输入字符
     * @param state 当前状态
     * @return 下一个状态索引，如果匹配到关键词则返回 {@value #KEYWORD_FOUND}
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
