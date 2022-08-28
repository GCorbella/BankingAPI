package com.bank.bank.security;

import com.bank.bank.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration  {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConf) throws Exception {
        return authConf.getAuthenticationManager();
    }




    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                .mvcMatchers(HttpMethod.PATCH, "/modify-password").hasAnyRole("USER", "ADMIN", "CONTRIBUTOR")
                .mvcMatchers(HttpMethod.PATCH, "/make-contributor").hasAnyRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/new-post").hasAnyRole("ADMIN", "CONTRIBUTOR")
                .mvcMatchers(HttpMethod.POST, "/new-author").hasAnyRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH, "/update-post").hasAnyRole("ADMIN", "CONTRIBUTOR")
                .mvcMatchers(HttpMethod.PATCH, "/update-author").hasAnyRole("ADMIN", "CONTRIBUTOR")
                .mvcMatchers(HttpMethod.DELETE, "/delete-author").hasAnyRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/delete-post").hasAnyRole("ADMIN")
                .anyRequest().permitAll();

        httpSecurity.csrf().disable();

        return httpSecurity.build();

    }

}
