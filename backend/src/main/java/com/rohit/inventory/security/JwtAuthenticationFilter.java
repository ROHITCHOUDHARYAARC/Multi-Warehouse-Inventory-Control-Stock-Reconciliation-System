package com.rohit.inventory.security;

import io.jsonwebtoken.JwtException; import jakarta.servlet.FilterChain; import jakarta.servlet.ServletException; import jakarta.servlet.http.HttpServletRequest; import jakarta.servlet.http.HttpServletResponse; import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.security.core.userdetails.UserDetails; import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService; private final InventoryUserDetailsService userDetailsService;
    public JwtAuthenticationFilter(JwtService jwtService, InventoryUserDetailsService userDetailsService) { this.jwtService = jwtService; this.userDetailsService = userDetailsService; }
    @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(JwtArchitecture.AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(JwtArchitecture.BEARER_PREFIX) && SecurityContextHolder.getContext().getAuthentication() == null) {
            try { String email = jwtService.parse(header.substring(JwtArchitecture.BEARER_PREFIX.length())).getSubject(); UserDetails user = userDetailsService.loadUserByUsername(email); if (user.isEnabled() && user.isAccountNonLocked()) SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())); }
            catch (JwtException | IllegalArgumentException ignored) { SecurityContextHolder.clearContext(); }
        }
        filterChain.doFilter(request, response);
    }
}
