package com.globaroman.auxilium.config;

import com.globaroman.auxilium.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final SecurityUserService securityUserService;

    @Autowired
    public SecurityConfig(PasswordEncoderConfig passwordEncoderConfig, SecurityUserService securityUserService) {
        this.passwordEncoderConfig = passwordEncoderConfig;
        this.securityUserService = securityUserService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder ayth) throws Exception {
        ayth
                .userDetailsService(securityUserService)
                .passwordEncoder(passwordEncoderConfig.passwordEncoder());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/main").permitAll()
                        .requestMatchers(HttpMethod.GET, "/new").permitAll()
                        .requestMatchers(HttpMethod.POST, "/new").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(l -> l.loginPage("/auth/login").permitAll()
                        .defaultSuccessUrl("/auth/success"))
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/auth/login"))
                .httpBasic( httpBasic ->{})
                .build();

    }

}
