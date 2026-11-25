package com.examly.springapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/marks/**").hasAnyRole("STUDENT", "TEACHER", "PRINCIPAL")
                        .requestMatchers(HttpMethod.POST, "/api/marks/**").hasAnyRole("TEACHER", "PRINCIPAL")
                        .requestMatchers(HttpMethod.DELETE, "/api/marks/**").hasRole("PRINCIPAL")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails student = User.withUsername("student")
                .password(passwordEncoder.encode("student@123"))
                .roles("STUDENT")
                .build();

        UserDetails teacher = User.withUsername("teacher")
                .password(passwordEncoder.encode("teacher@123"))
                .roles("TEACHER")
                .build();

        UserDetails principal = User.withUsername("principal")
                .password(passwordEncoder.encode("principal@123"))
                .roles("PRINCIPAL")
                .build();

        return new InMemoryUserDetailsManager(student, teacher, principal);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
