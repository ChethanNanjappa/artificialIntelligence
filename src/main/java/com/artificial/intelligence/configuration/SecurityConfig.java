package com.artificial.intelligence.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                //CSRF tokens are mainly required for
                // browser-based sessions where cookies are used for authentication.
                // For stateless JSON APIs (especially when using token auth or Basic + HTTPS), people often disable CSRF.
                .csrf(csrf -> csrf.disable())
                //Starts the access-control rules for incoming HTTP exchanges (WebFlux equivalent of
                // authorizeRequests in MVC).
                .authorizeExchange(auth -> auth
                        //This rule says: for POST requests to /stream/getStream require authentication.
                        .pathMatchers(HttpMethod.POST, "/stream/getStream").authenticated()
                        //A catch-all rule that permits every other request (any method, any path) without authentication.
                        .anyExchange().permitAll()
                )
                //If a protected resource is requested without credentials, Spring Security will issue a 401 and
                // include a WWW-Authenticate: Basic realm="Realm" header (this tells clients they should
                .httpBasic(basic -> {
                }) // enable basic authentication
                .build();
    }


}
