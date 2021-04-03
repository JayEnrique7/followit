package com.backend.demo.config;

import com.backend.demo.dto.Session;
import com.backend.demo.model.JsonBody;
import com.backend.demo.repository.SessionRepository;
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

import static com.backend.demo.constant.PathConstant.URL_LOGIN;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    VerifyJWTService verifyJWTService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authorizationHeader = httpServletRequest.getHeader("Authorization");
            final String uri = httpServletRequest.getRequestURI();
            final String restMethod = httpServletRequest.getMethod();

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

                String jwt = authorizationHeader.substring(7);
                Optional<Session> findToken = sessionRepository.findSessionByToken(jwt);

                if (findToken.isPresent()) {
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
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null &&
                    !uri.equalsIgnoreCase(URL_LOGIN) &&
                    !restMethod.equalsIgnoreCase("POST")) {

                ((HttpServletResponse) httpServletResponse).setStatus(HttpStatus.UNAUTHORIZED.value());
                ((HttpServletResponse) httpServletResponse).setContentType("application/json");
                ((HttpServletResponse) httpServletResponse).setCharacterEncoding("UTF-8");

                PrintWriter out = ((HttpServletResponse) httpServletResponse).getWriter();
                out.print(createJsonBody());
                out.flush();
                return;
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

