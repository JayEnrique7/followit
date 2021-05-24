package com.backend.config.config;

import com.backend.config.token.VerifyJWTService;
import com.backend.dto.Session;
import com.backend.config.utils.JsonBody;
import com.backend.service.SessionService;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final SessionService sessionService;
    private final VerifyJWTService verifyJWTService;
    private final UserDetail userDetail;

    public JwtTokenFilter(SessionService sessionService, UserDetail userDetail, VerifyJWTService verifyJWTService) {
        this.sessionService = sessionService;
        this.userDetail = userDetail;
        this.verifyJWTService = verifyJWTService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return "/login".equals(path);
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull FilterChain filterChain) throws ServletException, IOException {
        Session session = null;
        try {
            final String authorizationHeader = httpServletRequest.getHeader("Authorization");

            if (authorizationHeader != null) {
                String jwt = authorizationHeader.substring(7);
                session = sessionService.findSessionByToken(jwt);

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetail.loadUserByUsername(session.getUuid());
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

