package com.cenhai.framework.security;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cenhai.common.constant.Constants;
import com.cenhai.system.domain.SysRole;
import com.cenhai.system.domain.SysUserAuth;
import com.cenhai.system.service.SysMenuService;
import com.cenhai.system.service.SysRoleService;
import com.cenhai.system.service.SysUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserAuthService userAuthService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysMenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserAuth userAuth = userAuthService.getOne(new LambdaQueryWrapper<SysUserAuth>()
                .eq(SysUserAuth::getIdentifier,username)
                .eq(SysUserAuth::getIdentityType,Constants.DEFAULT_SECURITY_IDENTITY_TYPE));
        if (userAuth == null){
            throw new UsernameNotFoundException("用户名或密码为空");
        }
        List<SysRole> roles = roleService.listByUserId(userAuth.getUserId());
        List<String> authorities = new ArrayList<>();
        for (SysRole role : roles){
            authorities.add("ROLE_" + role.getRoleKey());
        }
        authorities.addAll(menuService.listMenuPermByUserId(userAuth.getUserId()));
        return new SystemUserDetails(userAuth, authorities);
    }
}
