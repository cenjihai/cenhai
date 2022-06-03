package com.cenhai.framework.security.extension;

import com.cenhai.common.constant.IdentityType;

public interface AdditionalAuthenticationHandler {

    public void additionalAuthenticationChecks(UserDetails userDetails, GenericAuthenticationToken authenticationToken);

    public boolean supports(IdentityType identityType);
}
