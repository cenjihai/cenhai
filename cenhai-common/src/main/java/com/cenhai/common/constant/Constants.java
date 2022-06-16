package com.cenhai.common.constant;

/**
 * 系统常量
 * @author cenjihai
 */
public class Constants {

    /**
     * 禁用
     */
    public final static String DISABLE = "1";

    /**
     * 正常
     */
    public final static String NORMAL = "0";

    /**
     * 是
     */
    public final static String YES = "Y";

    /**
     * 否
     */
    public final static String NO = "N";

    /**
     * 暴露资源前缀
     */
    public final static String RESOURCE_PREFIX = "/resource";

    /**
     * 验证码redis 缓存key
     */
    public final static String CAPTCHA_REDIS_KEY = "Authorization:captcha:";

    /**
     * 验证码缓存时间 2分钟
     */
    public final static Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 默认身份认证类型
     */
    public final static String DEFAULT_SECURITY_IDENTITY_TYPE = "password";
}
