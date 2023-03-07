package com.ivand.shopfullstack.security;

import com.ivand.shopfullstack.model.Client;
import com.ivand.shopfullstack.repository.ClientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public UserDetailsService userDetailsService(ClientRepository clientRepository) {
        return username -> {
            Client client = clientRepository.findByUsername(username);
            if (client != null) {
                return client;
            }
            throw new UsernameNotFoundException(
                    "User '" + username + "' not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                    .mvcMatchers("/cart").hasRole("USER")
//                    .mvcMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                        .loginPage("/login")
                            .defaultSuccessUrl("/main")
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .csrf()
                        .ignoringAntMatchers("/h2-console/**")
                .and()
                    .headers()
                        .frameOptions()
                            .sameOrigin()
                .and()
                    .build();
    }

}
