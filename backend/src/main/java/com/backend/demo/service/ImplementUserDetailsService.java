package com.backend.demo.service;

import com.backend.demo.config.security.EncryptSecret;
import com.backend.demo.dto.Users;
import com.backend.demo.model.SessionRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ImplementUserDetailsService implements UserDetailsService {

    private final UsersService usersService;

    public ImplementUserDetailsService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users users = usersService.findUserByEmail(s);
        return new User(users.getEmail(), users.getCredentials().getCredential(), new ArrayList<>());
    }
}
