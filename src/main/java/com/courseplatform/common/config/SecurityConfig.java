package com.courseplatform.common.config;

import com.courseplatform.auth.jwt.JwtAuthenticationFilter;
import com.courseplatform.common.exception.SecurityExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final SecurityExceptionHandler securityExceptionHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // ❌ Disable default login mechanisms
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                // ❌ Disable CSRF (stateless API)
                .csrf(csrf -> csrf.disable())

                // ✅ Stateless session
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // ✅ Custom 401 handling
                .exceptionHandling(e ->
                        e.authenticationEntryPoint(securityExceptionHandler)
                )

                // ✅ Authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/courses/**",
                                "/api/search/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // ✅ JWT filter
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            throw new UnsupportedOperationException(
                    "Default user details service is disabled"
            );
        };
    }

}
