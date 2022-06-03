package com.cenhai.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cenhai")
public class SystemConfig {

    private String name;

    private String version;

    private static String profile;

    public static String getAvatarPath(){
        return getProfile() + "/avatar";
    }

    public static String getDownloadPath()
    {
        return getProfile() + "/download";
    }

    public static String getUploadPath(){
        return getProfile() + "/uploads";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        SystemConfig.profile = profile;
    }
}
