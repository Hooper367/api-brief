package com.apibrief3.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.apibrief3.model.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers(
                "/api/v1/auth/**",
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "/webjars/**",
                "/swagger-ui.html"
        )
          .permitAll()
            // USERS
            .requestMatchers(GET,"/api/v1/users").hasRole(ADMIN.name())
            .requestMatchers(GET,"/api/v1/users/{userId}").hasAnyRole(ADMIN.name(), USER.name())
            .requestMatchers(PUT,"/api/v1/users/{userId}").hasAnyRole(ADMIN.name(), USER.name())
            .requestMatchers(DELETE,"/api/v1/users/{userId}").hasAnyRole(ADMIN.name(), USER.name())

            //PRODUCTS
            .requestMatchers(GET,"/api/v1/products", "/api/v1/products/{productId}").hasAnyRole(ADMIN.name(), USER.name())
            .requestMatchers(POST,"/api/v1/products").hasRole(ADMIN.name())
            .requestMatchers(PUT,"/api/v1/products/{productId}").hasRole(ADMIN.name())
            .requestMatchers(DELETE,"/api/v1/products/{productId}").hasRole(ADMIN.name())

            //CATEGORIES
            .requestMatchers(GET,"/api/v1/categories", "/api/v1/categories/{categoryId}").hasAnyRole(ADMIN.name(), USER.name())
            .requestMatchers(POST,"/api/v1/categories").hasRole(ADMIN.name())
            .requestMatchers(PUT,"/api/v1/categories/{categoryId}").hasRole(ADMIN.name())
            .requestMatchers(DELETE,"/api/v1/categories/{categoryId}").hasRole(ADMIN.name())

        .anyRequest()
          .authenticated()
        .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .logout()
        .logoutUrl("/api/v1/auth/logout")
        .addLogoutHandler(logoutHandler)
        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
    ;

    return http.build();
  }
}
