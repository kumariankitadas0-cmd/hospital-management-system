package com.company.hospitalsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            http
                    .authorizeHttpRequests(auth -> auth
                            // 1. PUBLIC FILES & PAGES (Must be first)
                            .requestMatchers("/css/**", "/register/**", "/login/**").permitAll()

                            // 2. ADMIN RESTRICTED ROUTES (Must be in the middle)
                            .requestMatchers("/deletePatient/**", "/deleteDoctor/**").hasRole("ADMIN")

                            // 3. CATCH-ALL FOR EVERYTHING ELSE (Must be absolutely last)
                            .anyRequest().authenticated()
                    )
                    .formLogin(form -> form
                            .loginPage("/login")
                            .loginProcessingUrl("/login")
                            .defaultSuccessUrl("/", true)
                            .permitAll()
                    )
                    .logout(logout -> logout
                            .logoutSuccessUrl("/login?logout")
                            .permitAll()
                    );

            return http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}