package com.pskwiercz.tacocloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userRepositoryUserDetailsService")
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new StandardPasswordEncoder("53cr3t");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/design", "/orders", "/orders/current")
                    .access("hasRole('ROLE_USER')")
                    .antMatchers("/", "/**").access("permitAll")
                .and()
                    .formLogin()
                    .loginPage("/login")
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .csrf()
                    .ignoringAntMatchers("/h2-console/**")
                .and()
                    .headers()
                    .frameOptions()
                    .sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());

// in-memory authorization
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//        auth.inMemoryAuthentication()
//                .withUser("user1")
//                    .password(encoder.encode("aaaa"))
//                    .authorities("ROLE_USER")
//                .and()
//                .withUser("user2")
//                    .password(encoder.encode("bbbb"))
//                    .authorities("ROLE_USER");
    }
}
