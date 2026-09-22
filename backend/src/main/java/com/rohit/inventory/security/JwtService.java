package com.rohit.inventory.security;

import io.jsonwebtoken.Claims; import io.jsonwebtoken.Jwts; import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets; import java.time.Instant; import java.util.Collection; import java.util.Date; import java.util.List; import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value; import org.springframework.security.core.GrantedAuthority; import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final SecretKey key; private final long expirationSeconds; private final String issuer;
    public JwtService(@Value("${app.security.jwt-secret}") String secret, @Value("${app.security.jwt-expiration}") long expirationSeconds, @Value("${app.security.jwt-issuer}") String issuer) {
        if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) throw new IllegalStateException("JWT_SECRET must be at least 32 bytes");
        if (expirationSeconds <= 0 || expirationSeconds > 86400) throw new IllegalStateException("JWT_EXPIRATION must be between 1 and 86400 seconds");
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); this.expirationSeconds = expirationSeconds; this.issuer = issuer;
    }
    public String generate(UUID userId, String email, Collection<? extends GrantedAuthority> authorities) {
        Instant now = Instant.now(); List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).filter(a -> a.startsWith("ROLE_")).map(a -> a.substring(5)).sorted().toList();
        return Jwts.builder().subject(email).claim("userId", userId.toString()).claim("roles", roles).issuer(issuer).issuedAt(Date.from(now)).expiration(Date.from(now.plusSeconds(expirationSeconds))).signWith(key).compact();
    }
    public Claims parse(String token) { return Jwts.parser().verifyWith(key).requireIssuer(issuer).build().parseSignedClaims(token).getPayload(); }
    public long expirationSeconds() { return expirationSeconds; }
}
