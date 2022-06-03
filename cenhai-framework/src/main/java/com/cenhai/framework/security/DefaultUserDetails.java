package com.cenhai.framework.security;

import com.cenhai.framework.security.extension.UserDetails;
import com.cenhai.system.domain.SysUserAuth;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultUserDetails implements UserDetails {

    private List<GrantedAuthority> authorities;

    private String password;

    private Long userId;

    private String accessToken;

    private Long loginTime;

    private Long expireTime;

    private String ipaddr;

    private String clientDetails;

    public DefaultUserDetails(){}

    public DefaultUserDetails(SysUserAuth userAuth, List<String> auths){
        this.userId = userAuth.getUserId();
        this.password = userAuth.getCredential();
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String key : auths){
            authorities.add(new SimpleGrantedAuthority(key));
        }
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }


    @Override
    public Long getLoginTime() {
        return loginTime;
    }

    @Override
    public Long getExpireTime() {
        return expireTime;
    }

    @Override
    public String getIpaddr() {
        return ipaddr;
    }

    @Override
    public String getClientDetails() {
        return clientDetails;
    }


    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public void setClientDetails(String clientDetails) {
        this.clientDetails = clientDetails;
    }


    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
