package com.cenhai.framework.security.extension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenericAuthenticationProvider extends AbstractAuthenticationProvider{

    @Autowired
    private List<AdditionalAuthenticationHandler> additionalAuthenticationHandlers;

    @Autowired
    private UserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private volatile String userNotFoundEncodedPassword;

    /**
     * 读取用户
     *
     * @param identifier
     * @param authenticationToken
     * @return
     */
    @Override
    protected UserDetails retrieveUser(Object identifier, GenericAuthenticationToken authenticationToken) {
        this.prepareTimingAttackProtection();
        try {
            if (authenticationToken.getIdentityType() == null)throw new InternalAuthenticationServiceException("IdentityType is null");
            UserDetails loadedUser = userDetailsService.loadUser(identifier.toString(),authenticationToken.getIdentityType());
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("UserDetails is null");
            } else {
                return loadedUser;
            }
        } catch (UsernameNotFoundException var4) {
            this.mitigateAgainstTimingAttack(authenticationToken);
            throw var4;
        } catch (InternalAuthenticationServiceException | LockedException var5) {
            throw var5;
        } catch (Exception var7) {
            throw new InternalAuthenticationServiceException(var7.getMessage(), var7);
        }
    }

    /**
     * 其他附件信息验证
     *
     * @param userDetails
     * @param authenticationToken
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, GenericAuthenticationToken authenticationToken) {
        for (AdditionalAuthenticationHandler additionalAuthenticationHandler : additionalAuthenticationHandlers){
            if (additionalAuthenticationHandler.supports(authenticationToken.getIdentityType())){
                additionalAuthenticationHandler.additionalAuthenticationChecks(userDetails,authenticationToken);
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return GenericAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private void prepareTimingAttackProtection() {
        if (this.userNotFoundEncodedPassword == null) {
            this.userNotFoundEncodedPassword = this.passwordEncoder.encode("userNotFoundPassword");
        }
    }

    private void mitigateAgainstTimingAttack(GenericAuthenticationToken authentication) {
        if (authentication.getCredentials() != null) {
            String presentedPassword = authentication.getCredentials().toString();
            this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
        }

    }
}
