package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 名称-版本对的封装类，用于表示操作系统或浏览器的识别结果。
 * <p>提供了一系列正则匹配方法，支持从 User-Agent 字符串中提取名称和版本信息。
 * 匹配过程中会保存最后一次正则匹配的分组结果，供后续查询。</p>
 */

public final class NameVersionPair implements Serializable {
    @Serial
    private static final long serialVersionUID = 4934582187956400034L;
    private String name = null;
    private String version = null;
    private String[] lastRegexGroups;

    public NameVersionPair() {
    }

    /**
     * 获取名称。
     *
     * @return 名称字符串
     */

    public final String getName() {
        return this.name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    /**
     * 获取版本。
     *
     * @return 版本字符串
     */

    public final String getVersion() {
        return this.version;
    }

    public final void setVersion(String version) {
        this.version = version;
    }

    /**
     * 使用正则表达式匹配输入字符串，若匹配成功则设置名称和版本。
     *
     * @param pattern        正则模式
     * @param input          输入字符串
     * @param matchedName    匹配成功后设置的名值
     * @param matchedVersion 匹配成功后设置的版本值（可为 {@code null}）
     * @return 如果匹配成功返回 {@code true}
     */

    public final boolean matchAndSet(Pattern pattern, String input, String matchedName, String matchedVersion) {
        if (this.find(pattern, input) != null) {
            this.name = matchedName.trim();
            if (matchedVersion != null) {
                this.version = matchedVersion.trim();
            }

            return true;
        } else {
            this.lastRegexGroups = null;
            return false;
        }
    }

    /**
     * 使用正则表达式匹配输入字符串，从指定分组中提取名称。
     *
     * @param pattern        正则模式
     * @param input          输入字符串
     * @param nameGroupIndex 名称所在的分组索引
     * @return 如果匹配成功返回 {@code true}
     */

    public final boolean matchAndSetNameFromGroup(Pattern pattern, String input, int nameGroupIndex) {
        Matcher matcher;
        matcher = this.find(pattern, input);
        if (matcher != null) {
            this.name = matcher.group(nameGroupIndex) == null ? "" : matcher.group(nameGroupIndex).trim();
            return true;
        } else {
            this.lastRegexGroups = null;
            return false;
        }
    }

    /**
     * 使用正则表达式匹配输入字符串，从指定分组中提取版本。
     *
     * @param pattern          正则模式
     * @param input            输入字符串
     * @param versionGroupIndex 版本所在的分组索引
     * @return 如果匹配成功返回 {@code true}
     */

    public final boolean matchAndSetVersionFromGroup(Pattern pattern, String input, int versionGroupIndex) {
        Matcher matcher;
        matcher = this.find(pattern, input);
        if (matcher != null) {
            this.version = matcher.group(versionGroupIndex) == null ? "" : matcher.group(versionGroupIndex).trim();
            return true;
        } else {
            this.lastRegexGroups = null;
            return false;
        }
    }

    /**
     * 使用正则表达式匹配输入字符串，从指定分组中提取版本，名称使用固定值。
     *
     * @param pattern          正则模式
     * @param input            输入字符串
     * @param matchedName      匹配成功后设置的名称（可为 {@code null}）
     * @param versionGroupIndex 版本所在的分组索引
     * @return 如果匹配成功返回 {@code true}
     */

    public final boolean matchAndSetGroup(Pattern pattern, String input, String matchedName, int versionGroupIndex) {
        Matcher matcher;
        matcher = this.find(pattern, input);
        if (matcher != null) {
            if (matchedName != null) {
                this.name = matchedName.trim();
            }

            String version = matcher.group(versionGroupIndex);
            this.version = version == null ? "" : version.trim();
            return true;
        } else {
            this.lastRegexGroups = null;
            return false;
        }
    }

    /**
     * 使用正则表达式匹配输入字符串，从分组 1 提取名称，从指定分组提取版本。
     *
     * @param pattern          正则模式
     * @param input            输入字符串
     * @param nameGroupIndex   版本所在的分组索引（名称始终从分组 1 获取）
     * @return 如果匹配成功返回 {@code true}
     */

    public final boolean matchAndSetNameAndGroup(Pattern pattern, String input, int nameGroupIndex) {
        Matcher matcher;
        matcher = this.find(pattern, input);
        if (matcher != null) {
            this.name = matcher.group(1) == null ? "" : matcher.group(1).trim();
            this.version = matcher.group(nameGroupIndex) == null ? "" : matcher.group(nameGroupIndex).trim();
            return true;
        } else {
            this.lastRegexGroups = null;
            return false;
        }
    }

    /**
     * 在输入字符串中查找指定内容，若存在则设置名称。
     *
     * @param input        输入字符串
     * @param needle       要查找的内容
     * @param matchedName  匹配成功后设置的名称
     * @return 如果找到指定内容返回 {@code true}
     */

    public final boolean containsAndSetName(String input, String needle, String matchedName) {
        if (StringMatchUtils.indexOf(input, needle) >= 0) {
            this.name = matchedName.trim();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 使用正则模式在输入字符串中查找匹配，并保存所有分组结果。
     *
     * @param pattern 正则模式
     * @param input   输入字符串
     * @return 匹配器对象，如果未找到匹配则返回 {@code null}
     */

    private Matcher find(Pattern pattern, String input) {
        Matcher matcher;
        matcher = pattern.matcher(input);
        if (!matcher.find()) {
            this.lastRegexGroups = null;
            return null;
        } else {
            this.lastRegexGroups = new String[matcher.groupCount() + 1];

            for (int i = 0; i < this.lastRegexGroups.length; ++i) {
                this.lastRegexGroups[i] = matcher.group(i);
            }

            return matcher;
        }
    }

    /**
     * 获取最后一次正则匹配的指定分组内容。
     *
     * @param groupIndex 分组索引
     * @return 分组内容，如果未进行过匹配则返回 {@code null}
     */

    public final String getGroup(int groupIndex) {
        return this.lastRegexGroups == null ? null : this.lastRegexGroups[groupIndex];
    }

    @Override
    /**
     * 返回名称-版本对的字符串表示，格式为 {@code [name: 名称 - version: 版本]}。
     *
     * @return 字符串表示
     */

    public String toString() {
        return "[name: " + this.name + " - version: " + this.version + "]";
    }
}
