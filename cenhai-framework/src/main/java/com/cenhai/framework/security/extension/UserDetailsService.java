package com.cenhai.framework.security.extension;

import com.cenhai.common.constant.IdentityType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {

    UserDetails loadUser(String identifier, IdentityType identityType) throws UsernameNotFoundException;
}
