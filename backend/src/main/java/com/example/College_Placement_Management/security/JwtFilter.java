package com.example.College_Placement_Management.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHead = request.getHeader("Authorization");
        String token = null;

        if (authHead != null && authHead.startsWith("Bearer ")) {
            token = authHead.substring(7).trim();
        }

        if (token != null && !token.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                Claims claims = jwtService.verifySignatureAndExtractAllClaims(token);
                if (!jwtService.isTokenExpire(token)) {
                    String username = claims.getSubject();
                    String role = claims.get("role", String.class);

                    List<GrantedAuthority> authorities = new ArrayList<>();
                    if (role != null && !role.isBlank()) {
                        String cleanRole = role.trim().toUpperCase();
                        if (!cleanRole.startsWith("ROLE_")) {
                            cleanRole = "ROLE_" + cleanRole;
                        }
                        authorities.add(new SimpleGrantedAuthority(cleanRole));
                    }

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (JwtException | IllegalArgumentException e) {
                // Token is invalid, expired, or malformed.
                // Do not throw 500: allow filter chain to continue so Spring Security handles 401.
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
