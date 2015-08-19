package com.lite.jop.demo.security;

import com.lite.jop.platform.security.AppSecurityManager;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SimpleAppSecurityManager
 *
 * @author LaineyC
 */
public class SimpleAppSecurityManager implements AppSecurityManager {

    private Map<String, String> appSecret = new ConcurrentHashMap<>();
    {
        appSecret.put("test", "DGFG9072ED07449345C4048F344DB93F42AFBFFC");
        appSecret.put("gobang", "971B1EC7563DA71B143EDE15D1E34AAC420C9942");
        appSecret.put("qq", "815C7889B5DFAE39ABEDF3D6B735C609A2EDBF5F");
        appSecret.put("yy", "1FC76F1A7238C0C39435F8220188655D1665450C");
        appSecret.put("11", "33549072ED07449345C4048F344DB93F42AFBFFC");
    }

    public String getSecret(String appKey){
        return appSecret.get(appKey);
    }

    public boolean isValidAppKey(String appKey){
        return appSecret.containsKey(appKey);
    }

}
