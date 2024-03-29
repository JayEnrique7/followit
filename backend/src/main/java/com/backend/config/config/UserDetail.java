package com.backend.config.config;

import com.backend.dto.Session;
import com.backend.service.SessionService;
import com.backend.config.service.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetail implements UserDetailsService {

    private final SessionService sessionService;

    public UserDetail(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Session session = sessionService.findUuid(id);
        return UserPrincipal.build(session);
    }
}
