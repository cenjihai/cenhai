package com.cenhai.framework.security;

import com.cenhai.common.constant.Constants;
import com.cenhai.system.domain.SysUserAuth;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SystemUserDetails implements UserDetails {

    private List<GrantedAuthority> authorities;

    private String password;

    private Long userId;

    private String accessToken;

    private Long loginTime;

    private Long expireTime;

    private String ipaddr;

    private String clientDetails;

    private String username;

    private SysUserAuth userAuth;

    public SystemUserDetails(SysUserAuth userAuth, List<String> authorities){
        this.userAuth = userAuth;
        this.password = userAuth.getCredential();
        this.userId = userAuth.getUserId();
        this.username = userAuth.getIdentifier();
        this.authorities = new ArrayList<>();
        for (String key : authorities){
            this.authorities.add(new SimpleGrantedAuthority(key));
        }

    }

    public SystemUserDetails(){}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Constants.NORMAL.equals(userAuth.getStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Constants.YES.equals(userAuth.getVerified());
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getUserId() {
        return userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public String getIpaddr() {
        return ipaddr;
    }

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

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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

    public void setUsername(String username) {
        this.username = username;
    }
}
