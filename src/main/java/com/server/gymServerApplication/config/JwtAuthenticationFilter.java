package com.server.gymServerApplication.config;

import com.server.gymServerApplication.service.TokenService;
import com.server.gymServerApplication.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final TokenService tokenService;
    private final UserService userService;

    @Autowired
    public JwtAuthenticationFilter(JwtDecoder jwtDecoder, TokenService tokenService, UserService userService) {
        this.jwtDecoder = jwtDecoder;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            try {
                Jwt jwt = jwtDecoder.decode(token);

                if (jwt.getExpiresAt() == null || jwt.getExpiresAt().isBefore(Instant.now())) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
                    return;
                }

                String email = jwt.getSubject();
                UserDetails user = userService.loadUserByUsername(email);

                if (user == null || !user.isAccountNonLocked()) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found or account locked");
                    return;
                }

                List<String> roles = jwt.getClaimAsStringList("roles");
                if (roles == null || roles.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No roles found in token");
                    return;
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        email, token, AuthorityUtils.createAuthorityList(roles.toArray(new String[0]))
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                e.printStackTrace(); // Log chi tiết, không hiển thị client
                return;
            }
        }

        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");

        filterChain.doFilter(request, response);
    }

}