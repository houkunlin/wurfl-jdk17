package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class EmailClientUserAgentMatcher extends AbstractA {
  private static String b = "mozilla_thunderbird";

  private static String c = "ms_outlook";

  private static final Pattern d = Pattern.compile("Microsoft Outlook ([0-9]+).");

  private static final Pattern e = Pattern.compile("^MacOutlook ([0-9]+).");

  private static final String[] f = (String[])UserAgentUtils.getMobileBrowsers().toArray((Object[])new String[UserAgentUtils.getMobileBrowsers().size()]);

  private static final List g;

  public static final String[] EMAIL_CLIENTS = new String[] { "Thunderbird", "Outlook", "Lotus-Notes", "Eudora/", "Evolution/", "PocoMail/", "The Bat!", "Postbox/", "Airmail" };

  public EmailClientUserAgentMatcher(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    super(paramUserAgentNormalizer, paramWURFLModel);
  }

  public boolean canHandle(WURFLRequest paramWURFLRequest) {
    return ((paramWURFLRequest._internalIsEmailClient() && !StringMatchUtils.containsAnyOf(paramWURFLRequest.getDeviceUserAgent(), new String[] { "Office", "office" })) || (paramWURFLRequest.getDeviceUserAgent().contains("Spark/") && paramWURFLRequest.getDeviceUserAgent().contains("CFNetwork/")));
  }

  public String getMatcherName() {
    return "EmailClientMatcher";
  }

  public String getBucketMatcherName() {
    return "EmailClient";
  }

  protected final String a(String paramString) {
    if (paramString.contains("Thunderbird"))
      paramString = paramString.substring(paramString.indexOf("Thunderbird"));
    if (d.matcher(paramString).find())
      paramString = paramString.substring(paramString.indexOf("Microsoft Outlook"));
    if (e.matcher(paramString).find())
      paramString = paramString.substring(paramString.indexOf("MacOutlook"));
    int i;
    return ((i = paramString.indexOf(".")) != -1) ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i) : "generic";
  }

  protected final Set a() {
    HashSet<?> hashSet;
    (hashSet = new HashSet<>()).addAll(g);
    return hashSet;
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    String str;
    return (str = paramWURFLRequest.getDeviceUserAgent()).contains("Thunderbird") ? b : (str.contains("Outlook-Express") ? "outlook_express" : (str.contains("MacOutlook") ? "mac_outlook" : ((str.contains("Outlook") && str.contains("CFNetwork")) ? "ms_outlook_ios_ver1" : (str.contains("Outlook") ? c : (str.contains("Lotus-Notes") ? "lotus_notes_ver1" : (str.contains("Eudora/") ? "eudora_ver1" : (str.contains("Evolution/") ? "evolution_ver1" : (str.contains("PocoMail/") ? "pocomail_ver1" : (str.contains("The Bat!") ? "thebat_ver1" : (str.contains("Postbox/") ? "postbox_ver1" : ((str.contains("Airmail") && str.contains("CFNetwork") && !str.contains("x86_64")) ? "airmail_ios_ver1" : (str.contains("Airmail") ? "airmail_ver1" : ((str.contains("Spark/") && str.contains("CFNetwork")) ? "spark_ios_ver1" : (StringMatchUtils.containsAnyOf(str, f) ? "generic_mobile_email_client" : "generic_email_client"))))))))))))));
  }

  static {
    (g = new ArrayList<String>()).add(b);
    g.add(c);
    g.add("mac_outlook");
    g.add("ms_outlook_ios_ver1");
    g.add("outlook_express");
    g.add("generic_email_client");
    g.add("generic_mobile_email_client");
    g.add("lotus_notes_ver1");
    g.add("eudora_ver1");
    g.add("evolution_ver1");
    g.add("pocomail_ver1");
    g.add("thebat_ver1");
    g.add("postbox_ver1");
    g.add("airmail_ver1");
    g.add("airmail_ios_ver1");
    g.add("spark_ios_ver1");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\EmailClientUserAgentMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
