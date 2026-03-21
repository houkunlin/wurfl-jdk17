package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentWithNeedleCount;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAgentNormalizerChain implements UserAgentNormalizer {
  private final transient Logger a = LoggerFactory.getLogger(getClass());
  
  private final List b = new ArrayList();
  
  public UserAgentNormalizerChain() {}
  
  public UserAgentNormalizerChain(List paramList) {
    this.b.addAll(paramList);
  }
  
  public UserAgentNormalizerChain(UserAgentNormalizer[] paramArrayOfUserAgentNormalizer) {
    this(Arrays.asList(paramArrayOfUserAgentNormalizer));
  }
  
  public UserAgentNormalizerChain add(UserAgentNormalizer paramUserAgentNormalizer) {
    ArrayList<UserAgentNormalizer> arrayList;
    (arrayList = new ArrayList<UserAgentNormalizer>(this.b)).add(paramUserAgentNormalizer);
    return new UserAgentNormalizerChain(arrayList);
  }
  
  public String normalize(String paramString) {
    UserAgentWithNeedleCount userAgentWithNeedleCount;
    String str = (userAgentWithNeedleCount = UserAgentUtils.getAsciiPrintableStringWithNeedleCount(new StringBuilder(paramString))).getAsciiPrintableUserAgent();
    if (!userAgentWithNeedleCount.hasSpaceChars() && userAgentWithNeedleCount.getPlusCharCount() > 2)
      str = b(str, null); 
    if (userAgentWithNeedleCount.getPercentageCharCount() > 2)
      str = a(str, null); 
    return a(str);
  }
  
  public String normalize(String paramString, WURFLRequest paramWURFLRequest) {
    UserAgentWithNeedleCount userAgentWithNeedleCount;
    String str = (userAgentWithNeedleCount = UserAgentUtils.getAsciiPrintableStringWithNeedleCount(new StringBuilder(paramString))).getAsciiPrintableUserAgent();
    if (!userAgentWithNeedleCount.hasSpaceChars() && userAgentWithNeedleCount.getPlusCharCount() > 2)
      str = b(str, paramWURFLRequest); 
    if (userAgentWithNeedleCount.getPercentageCharCount() > 2)
      str = a(str, paramWURFLRequest); 
    return a(str);
  }
  
  private String a(String paramString) {
    Iterator<UserAgentNormalizer> iterator = this.b.iterator();
    while (iterator.hasNext())
      paramString = ((UserAgentNormalizer)iterator.next()).normalize(paramString); 
    return paramString;
  }
  
  private String a(String paramString, WURFLRequest paramWURFLRequest) {
    try {
      paramString = StringMatchUtils.rawdecode(paramString);
      if (paramWURFLRequest != null)
        paramWURFLRequest.setUrlEncoded(true); 
    } catch (Exception exception) {
      this.a.warn("rawdecoding for user agent " + paramString + " failed", exception);
    } 
    return paramString;
  }
  
  private static String b(String paramString, WURFLRequest paramWURFLRequest) {
    if (paramWURFLRequest != null)
      paramWURFLRequest.setUrlEncoded(true); 
    return StringUtils.replace(paramString, "+", " ");
  }
  
  public List getAllNormalizers() {
    return Collections.unmodifiableList(this.b);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\UserAgentNormalizerChain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */