package com.cenhai.framework.security;

import com.cenhai.common.constant.Constants;
import com.cenhai.common.constant.IdentityType;
import com.cenhai.framework.security.extension.UserDetails;
import com.cenhai.framework.security.extension.UserDetailsService;
import com.cenhai.system.domain.SysRole;
import com.cenhai.system.domain.SysUserAuth;
import com.cenhai.system.service.SysMenuService;
import com.cenhai.system.service.SysRoleService;
import com.cenhai.system.service.SysUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 重新定义接口，支持传入登录方式
 */
@Component
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    private SysUserAuthService userAuthsService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysMenuService menuService;

    @Override
    public UserDetails loadUser(String identifier, IdentityType identityType) throws UsernameNotFoundException {
        SysUserAuth userAuth = userAuthsService.getUserAuthByIdentifierAndIdentityType(identifier, identityType);
        if (userAuth == null){
            if (identityType.equals(IdentityType.password)) {
                throw new UsernameNotFoundException("用户名或密码为空");
            }else {
                throw new InternalAuthenticationServiceException("身份类型不支持");
            }
        }
        List<SysRole> roles = roleService.listRoleIdAndRoleKeyByUserId(userAuth.getUserId());
        List<String> authorities = new ArrayList<>();
        for (SysRole role : roles){
            authorities.add("ROLE_" + role.getRoleKey());
            List<String> permKeys = menuService.listPermKeyByRoleId(role.getRoleId());
            authorities.addAll(permKeys);
        }
        this.check(userAuth);
        return new DefaultUserDetails(userAuth, authorities);
    }

    private void check(SysUserAuth userAuth){
        if (Constants.DISABLE.equals(userAuth.getStatus())){
            throw new LockedException("该用户已禁用");
        }
        if (!Constants.NORMAL.equals(userAuth.getVerified())){
            throw new LockedException("登录方式未验证");
        }
    }
}