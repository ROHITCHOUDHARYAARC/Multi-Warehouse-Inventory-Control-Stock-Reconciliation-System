package com.rohit.inventory.security;
import java.util.List; import org.springframework.beans.factory.annotation.Value; import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy; import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; import org.springframework.security.authentication.AuthenticationManager; import org.springframework.security.authentication.dao.DaoAuthenticationProvider; import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; import org.springframework.web.cors.CorsConfiguration; import org.springframework.web.cors.CorsConfigurationSource; import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
@Configuration @EnableWebSecurity @EnableMethodSecurity
public class SecurityConfig {
    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http, RestAuthenticationEntryPoint entryPoint, RestAccessDeniedHandler deniedHandler, CorsConfigurationSource corsConfigurationSource, JwtAuthenticationFilter jwtFilter) throws Exception {
        return http.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(corsConfigurationSource))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint).accessDeniedHandler(deniedHandler))
            .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/health", "/actuator/health", "/api/v1/auth/login", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll().requestMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
    @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean DaoAuthenticationProvider authenticationProvider(InventoryUserDetailsService users, PasswordEncoder encoder) { DaoAuthenticationProvider provider = new DaoAuthenticationProvider(users); provider.setPasswordEncoder(encoder); return provider; }
    @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception { return configuration.getAuthenticationManager(); }
    @Bean CorsConfigurationSource corsConfigurationSource(@Value("${app.cors.allowed-origin}") String allowedOrigin) {
        CorsConfiguration config = new CorsConfiguration(); config.setAllowedOrigins(List.of(allowedOrigin)); config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS")); config.setAllowedHeaders(List.of("Authorization","Content-Type","X-Request-ID")); config.setExposedHeaders(List.of("X-Request-ID")); config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); source.registerCorsConfiguration("/**", config); return source;
    }
}
