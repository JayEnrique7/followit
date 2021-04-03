package com.backend.demo.config;

import com.backend.demo.dto.Session;
import com.backend.demo.dto.Users;
import com.backend.demo.model.JsonBody;
import com.backend.demo.repository.SessionRepository;
import com.backend.demo.repository.UsersRepository;
import com.backend.demo.service.VerifyJWTService;
import com.google.gson.Gson;
import io.jsonwebtoken.JwtException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.io.PrintWriter;
import java.util.Optional;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    VerifyJWTService verifyJWTService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authorizationHeader = httpServletRequest.getHeader("Authorization");

            String username = null;
            String jwt = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = verifyJWTService.getSub(jwt);
            }


            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                Users users = usersRepository.findUserByEmail(verifyJWTService.getSub(jwt)).iterator().next();
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                Optional<Session> session = sessionRepository.findSessionByToken(jwt);

                if (session.isPresent() && verifyJWTService.validateToken(jwt, users, session.stream().iterator().next())) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (JwtException e) {
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");

            PrintWriter out = httpServletResponse.getWriter();
            out.print(createJsonBody());
            out.flush();
        }
    }
    private String createJsonBody() {
        JsonBody jsonBody = new JsonBody(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(), "Invalid token");
        return new Gson().toJson(jsonBody);
    }

}

