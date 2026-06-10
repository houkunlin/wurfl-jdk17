package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

/**
 * 匹配器基础抽象类，是 {@link AbstractMatcher} 的直接子类。
 * <p>该类没有增加新的行为，仅作为继承体系中的一个中间层，
 * 为具体的设备/浏览器匹配器提供多种构造方式：</p>
 * <ul>
 *   <li>无参构造（不进行模型校验）</li>
 *   <li>带 {@link WURFLModel} 的构造（进行必需设备 ID 校验）</li>
 *   <li>带 {@link UserAgentNormalizer} 的构造（支持 User-Agent 预处理）</li>
 *   <li>同时带两者的构造</li>
 * </ul>
 * <p>大多数具体的品牌匹配器（如 {@code SamsungMatcher}、{@code NokiaMatcher}）都继承自此类。</p>
 */

abstract class MatcherBase extends AbstractMatcher {
    protected MatcherBase() {
        super();
    }

    protected MatcherBase(WURFLModel wurflModel) {
        super(wurflModel);
    }

    protected MatcherBase(UserAgentNormalizer normalizer) {
        super(normalizer);
    }

    protected MatcherBase(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
        super(normalizer, wurflModel);
    }
}
