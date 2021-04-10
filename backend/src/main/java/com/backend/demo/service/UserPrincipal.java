package com.backend.demo.service;

import com.backend.demo.dto.Session;
import com.backend.demo.dto.Users;
import com.backend.demo.exceptions.NotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

@Data
public class UserPrincipal implements UserDetails {

    private Session session;
    @Autowired
    private UsersService usersService;

    public UserPrincipal(Session session) {
        this.session = session;
    }

    public static UserDetails build(Session session) {
        return new UserPrincipal(session);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        if (session == null) {
            throw new NotFoundException("the session not exist!");
        }
        Users users = usersService.findUserById(session.getUsersId());
        return users.getCredentials().getCredential();
    }

    @Override
    public String getUsername() {
        return session.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Calendar.getInstance().before(session.getDate());
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
