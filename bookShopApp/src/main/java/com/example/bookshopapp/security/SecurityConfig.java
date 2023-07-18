package com.example.bookshopapp.security;

import com.example.bookshopapp.security.jwt.JWTRequestFilter;


import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final BookShopUserDetailsService userDetailsService;
    private final JWTRequestFilter jwtRequestFilter;
    private final JWTPersistLogoutHandler jwtPersistLogoutHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/my").authenticated()
                .antMatchers("/profile").authenticated() //доступ для аутентифицированных
                .antMatchers("/**").permitAll()
                .and()
                .authenticationProvider(authenticationProvider())
                .formLogin()
                .loginPage("/signin")
                .failureUrl("/signin")
                .and()
                .exceptionHandling()
                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(jwtPersistLogoutHandler)
                .logoutSuccessUrl("/signin")
                .deleteCookies("token")
                .and()
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("/my")
//                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(defaultOauthUserService()))
                );

//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //отключение убирается
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    private OAuth2UserService<OidcUserRequest, OidcUser> defaultOauthUserService() {
//        return userRequest -> {
//            String email = userRequest.getIdToken().getClaim("email");
//            // TODO: 24.04.2022 create user userService.create
//            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
////            new OidcUserService().loadUser()
//            DefaultOidcUser oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());
//
//            Set<Method> userDetailsMethods = Set.of(UserDetails.class.getMethods());
//
//            return (OidcUser) Proxy.newProxyInstance(SecurityConfig.class.getClassLoader(),
//                    new Class[]{UserDetails.class, OidcUser.class},
//                    (proxy, method, args) -> userDetailsMethods.contains(method)
//                            ? method.invoke(userDetails, args)
//                            : method.invoke(oidcUser, args));
//        };
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
