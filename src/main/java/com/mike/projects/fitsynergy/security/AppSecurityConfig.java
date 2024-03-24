package com.mike.projects.fitsynergy.security;

import com.mike.projects.fitsynergy.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.mike.projects.fitsynergy.security.model.SystemUserRole.*;
import static com.mike.projects.fitsynergy.security.model.SystemUserPermission.*;

@Configuration
@RequiredArgsConstructor
//@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class AppSecurityConfig {

    private final PasswordEncoder passwordEncoder;
//    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityConfig(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf(csrf -> csrf.disable());

        httpSecurity.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/assets/**", "/main-assets/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/api/users/sign-up").permitAll()
                        .requestMatchers("/api/construct/**").hasAnyRole(TRAINER.name(), ADMIN.name())
                        .requestMatchers("/api/admin/**").hasRole(ADMIN.name())
                        .anyRequest().authenticated()

        );

        httpSecurity.formLogin(login -> login
                .loginPage("/api/users/log-in").permitAll()
                .loginProcessingUrl("/api/users/log-in")
                .successForwardUrl("/api/dashboard/")
                .usernameParameter("username")
                .passwordParameter("password"));

        return httpSecurity.build();
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder);
//        provider.setUserDetailsService(authenticationService());
//        return provider;
//    }
//
//    @Bean
//    public UserDetailsService authenticationService(){
////        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
////        provider.setPasswordEncoder(passwordEncoder);
////        provider.setUserDetailsService(userDetailsService);
////        return provider;
//        UserDetails mikeUser = User
//                .builder()
//                .username("rydukmishaaa@gmail.com")
//                .password(passwordEncoder.encode("Ragnervi1"))
//                .roles("CLIENT")
//                .build();
//
//        return new InMemoryUserDetailsManager(mikeUser);
//    }

}
