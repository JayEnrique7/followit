package com.backend.demo.config;

import com.backend.demo.dto.Session;
import com.backend.demo.model.JsonBody;
import com.backend.demo.service.SessionService;
import com.backend.demo.service.VerifyJWTService;
import com.google.gson.Gson;
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

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    SessionService sessionService;
    @Autowired
    VerifyJWTService verifyJWTService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull FilterChain filterChain) throws ServletException, IOException {
        Session session = null;
        try {
            final String authorizationHeader = httpServletRequest.getHeader("Authorization");

            if (authorizationHeader != null) {
                String jwt = authorizationHeader.substring(7);
                session = sessionService.findSessionByToken(jwt);
                String username = verifyJWTService.getSub(jwt);

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().startsWith("JWT expired")) {
                sessionService.sessionDelete(session);
            }
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

