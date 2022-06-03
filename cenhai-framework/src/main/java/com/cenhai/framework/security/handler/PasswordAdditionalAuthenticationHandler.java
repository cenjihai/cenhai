package com.cenhai.framework.security.handler;

import com.cenhai.common.constant.IdentityType;
import com.cenhai.framework.security.extension.AdditionalAuthenticationHandler;
import com.cenhai.framework.security.extension.GenericAuthenticationToken;
import com.cenhai.framework.security.extension.UserDetails;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordAdditionalAuthenticationHandler implements AdditionalAuthenticationHandler {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void additionalAuthenticationChecks(UserDetails userDetails, GenericAuthenticationToken authenticationToken) {
        if (authenticationToken.getCredentials() == null) {
            throw new BadCredentialsException("用户名或密码错误");
        } else {
            String presentedPassword = authenticationToken.getCredentials().toString();
            if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
                throw new BadCredentialsException("用户名或密码错误");
            }
        }
    }

    @Override
    public boolean supports(IdentityType identityType) {
        return identityType.equals(IdentityType.password);
    }
}
