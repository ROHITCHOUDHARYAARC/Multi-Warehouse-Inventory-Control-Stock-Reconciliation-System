package com.rohit.inventory.gateway;

import java.nio.charset.StandardCharsets;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import reactor.core.publisher.Mono;

/**
 * Edge-level authentication guard. The Inventory service remains the JWT
 * authority and validates the signature/claims; this filter prevents protected
 * traffic without a bearer credential from entering the service network.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class EdgeAuthenticationFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (isPublic(path) || (authorization != null && authorization.startsWith("Bearer "))) {
            return chain.filter(exchange);
        }
        var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] body = "{\"success\":false,\"message\":\"Bearer token is required\"}"
                .getBytes(StandardCharsets.UTF_8);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body)));
    }

    private boolean isPublic(String path) {
        return path.equals("/api/v1/health")
                || path.equals("/actuator/health")
                || path.equals("/api/v1/auth/login")
                || path.startsWith("/swagger-ui/")
                || path.startsWith("/v3/api-docs");
    }
}
