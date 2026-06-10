package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentWithNeedleCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * User-Agent 规范化器链的实现，按顺序执行一组规范化器。
 * <p>该类本身也实现了 {@link UserAgentNormalizer} 接口，因此可以嵌套组合。
 * 处理流程如下：</p>
 * <ol>
 *   <li>将原始 UA 转换为可打印 ASCII 字符串</li>
 *   <li>如果 UA 中缺少空格但包含大量加号，则将加号替换为空格（标记为 URL 编码）</li>
 *   <li>如果 UA 中包含大量百分号，则尝试进行 URL 解码</li>
 *   <li>依次执行链中所有规范化器的 normalize 方法</li>
 * </ol>
 */
public class UserAgentNormalizerChain implements UserAgentNormalizer {
    private static final Logger log = LoggerFactory.getLogger(UserAgentNormalizerChain.class);

    /**
     * 规范化器列表，按添加顺序依次执行。
     */
    private final List<UserAgentNormalizer> normalizers;

    public UserAgentNormalizerChain() {

        this.normalizers = new ArrayList<>();
    }

    /**
     * 使用给定的规范化器列表创建链。
     *
     * @param normalizers 规范化器列表
     */
    public UserAgentNormalizerChain(List<UserAgentNormalizer> normalizers) {

        this.normalizers = new ArrayList<>();
        this.normalizers.addAll(normalizers);
    }

    public UserAgentNormalizerChain(UserAgentNormalizer[] normalizers) {
        this(Arrays.asList(normalizers));
    }

    /**
     * Plu s opac en dar kncoded.
     */

    private static String plusToSpaceAndMarkEncoded(String userAgent, WURFLRequest request) {
        if (request != null) {
            request.setUrlEncoded(true);
        }

        return userAgent.replace("+", " ");
    }

    /**
     * Add.
 */

    public UserAgentNormalizerChain add(UserAgentNormalizer normalizer) {
        ArrayList<UserAgentNormalizer> newNormalizers = new ArrayList<>(this.normalizers);
        newNormalizers.add(normalizer);
        return new UserAgentNormalizerChain(newNormalizers);
    }

    @Override
    /**
     * 对原始 User-Agent 字符串执行规范化处理（不关联 WURFLRequest 对象）。
     *
     * @param rawUserAgent 原始 User-Agent 字符串
     * @return 规范化后的 User-Agent 字符串
     */
    public String normalize(String rawUserAgent) {
        return normalize(rawUserAgent, null);
    }

    public String normalize(String rawUserAgent, WURFLRequest request) {
        UserAgentWithNeedleCount needleCount = UserAgentUtils.getAsciiPrintableStringWithNeedleCount(new StringBuilder(rawUserAgent));
        String userAgent = needleCount.getAsciiPrintableUserAgent();
        if (!needleCount.hasSpaceChars() && needleCount.getPlusCharCount() > 2) {
            userAgent = plusToSpaceAndMarkEncoded(userAgent, request);
        }

        if (needleCount.getPercentageCharCount() > 2) {
            userAgent = rawDecodeIfNeeded(userAgent, request);
        }

        return this.applyChain(userAgent);
    }

    /**
     * Appl yhain.
 */

    private String applyChain(String userAgent) {
        for (Iterator<UserAgentNormalizer> iterator = this.normalizers.iterator(); iterator.hasNext(); userAgent = iterator.next().normalize(userAgent)) {
        }

        return userAgent;
    }

    /**
     * 对 User-Agent 进行 URL 解码（如果需要）。
     * <p>当检测到 UA 中包含大量百分号时（超过 2 个），说明可能经过了 URL 编码，
     * 尝试使用 rawdecode 方法进行解码。如果解码失败，记录警告日志并返回原始值。</p>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @param request   WURFL 请求对象，用于标记编码状态，可为 null
     * @return 解码后的 User-Agent 字符串，解码失败则返回原始值
     */
    private String rawDecodeIfNeeded(String userAgent, WURFLRequest request) {
        try {
            userAgent = StringMatchUtils.rawdecode(userAgent);
            if (request != null) {
                request.setUrlEncoded(true);
            }
        } catch (RuntimeException e) {
            log.warn("rawdecoding for user agent {} failed", userAgent, e);
        }

        return userAgent;
    }

    /**
     * Returns the al lormalizers.
 */

    public List<UserAgentNormalizer> getAllNormalizers() {
        return Collections.unmodifiableList(this.normalizers);
    }
}
