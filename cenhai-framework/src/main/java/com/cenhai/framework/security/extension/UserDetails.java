package com.cenhai.framework.security.extension;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public interface UserDetails extends Serializable {

    Collection<? extends GrantedAuthority> getAuthorities();

    String getPassword();

    Long getUserId();

    /**
     * 用户登陆唯一标识
     */
    String getAccessToken();

    /**
     * 登录时间
     */
    Long getLoginTime();

    /**
     * 过期时间
     */
    Long getExpireTime();

    /**
     * 登录IP
     */
    String getIpaddr();

    /**
     * 登录客户端信息，
     */
    String getClientDetails();
}
