package com.cenhai.framework.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUtils {

    public static Long getUserId(){
        return getUserDetails().getUserId();
    }

    public static SystemUserDetails getUserDetails(){
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (o instanceof SystemUserDetails)
            return (SystemUserDetails) o;
        return null;
    }

    public static List<String> getPerms(){
        Collection<? extends GrantedAuthority> list = getUserDetails().getAuthorities();
        List<String> strings = new ArrayList<>();
        for (GrantedAuthority authority : list){
            strings.add(authority.getAuthority());
        }
        return strings;
    }
}
