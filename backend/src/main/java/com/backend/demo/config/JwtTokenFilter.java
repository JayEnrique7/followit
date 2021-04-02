package com.backend.demo.config;

import com.backend.demo.config.token.CreateJWT;
import com.backend.demo.dto.Session;
import com.backend.demo.dto.Users;
import com.backend.demo.repository.SessionRepository;
import com.backend.demo.repository.UsersRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    SessionRepository sessionRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = httpServletRequest.getHeader("Authorization");

        String username = null;
        String jwt = null;

        CreateJWT createJWT = new CreateJWT();

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = createJWT.getSub(jwt);
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            Users users = usersRepository.findUserByEmail(createJWT.getSub(jwt)).iterator().next();
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            Optional<Session> session = sessionRepository.findSessionByToken(jwt);

            if (session.isPresent() && createJWT.validateToken(jwt, users, session.stream().iterator().next())) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}

