package com.cenhai.framework.security.extension;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public abstract class AbstractAuthenticationProvider implements AuthenticationProvider {

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String identifier = this.determineUsername(authentication);
        UserDetails user = null;
        try {
            user = this.retrieveUser(identifier, (GenericAuthenticationToken)authentication);
        } catch (UsernameNotFoundException var6) {
            throw new BadCredentialsException(var6.getMessage());
        }
        try {
            this.additionalAuthenticationChecks(user, (GenericAuthenticationToken)authentication);
        } catch (AuthenticationException var7) {
            throw new BadCredentialsException(var7.getMessage());
        }
        return createSuccessAuthentication(authentication.getPrincipal(),authentication,user);
    }

    private Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        GenericAuthenticationToken result = new GenericAuthenticationToken(principal, authentication.getCredentials(), this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(user);
        return result;
    }

    private String determineUsername(Authentication authentication) {
        return authentication.getPrincipal() == null ? "NONE_PROVIDED" : authentication.getName();
    }

    /**
     * 读取用户
     * @param identifier
     * @param authenticationToken
     * @return
     */
    protected abstract UserDetails retrieveUser(Object identifier, GenericAuthenticationToken authenticationToken);

    /**
     * 其他附件信息验证
     * @param userDetails
     * @param authenticationToken
     */
    protected abstract void additionalAuthenticationChecks(UserDetails userDetails, GenericAuthenticationToken authenticationToken);
}
