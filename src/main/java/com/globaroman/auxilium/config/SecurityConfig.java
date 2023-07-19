package com.globaroman.auxilium.config;

import com.globaroman.auxilium.model.entity.security.PermissionA;
import com.globaroman.auxilium.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final SecurityUserService securityUserService;

      @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
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
                        .requestMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority(PermissionA.USER_WRITE.getPermission(), PermissionA.USER_UPDATE.getPermission())
                        .anyRequest().authenticated()
                )
                .formLogin(l -> l.loginPage("/auth/login").permitAll()
                        .defaultSuccessUrl("/main"))
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/main"))
                .oauth2Login(config -> config.loginPage("/auth/login")
                        .defaultSuccessUrl("/main")
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService())))
                //.httpBasic( httpBasic ->{})
                .build();

    }
    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return userRequest -> {
            String username = userRequest.getIdToken().getClaim("username");
            System.out.println(username);
            //TODO Could create new user
            UserDetails userDetails = securityUserService.loadUserByUsername(username);
            DefaultOidcUser oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());
            Set<Method> userDetailsMethod = Set.of(UserDetails.class.getMethods());

            return (OidcUser) Proxy.newProxyInstance(SecurityConfig.class.getClassLoader(), new Class[]{UserDetails.class, OidcUser.class}, (proxy, method, args) -> userDetailsMethod.contains(method)
                    ? method.invoke(userDetails, args)
                    : method.invoke(oidcUser, args));
        };
    }

}
