package com.rohit.inventory.security;
/**
 * Marker for the Part 4 JWT flow. Part 3 deliberately does not issue tokens
 * or create a fake authentication endpoint. Part 4 adds JwtService,
 * JwtAuthenticationFilter and CustomUserDetailsService around this contract.
 */
public final class JwtArchitecture {
    private JwtArchitecture() { }
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
}
