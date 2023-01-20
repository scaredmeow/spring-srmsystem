package com.code.srmsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UserAccountService userAccountService;

    public SecurityConfiguration(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Bean

    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/",
                        "/signup")
                .permitAll()
                .antMatchers("/requests")
                .authenticated()
                .antMatchers("/requests/student", "/requests/student/**")
                .hasRole("STUDENT")
                .antMatchers("/requests/admin", "/requests/admin/check", "/requests/admin/check/**",
                        "/requests/admin/search", "/requests/admin/search/**")
                .hasAnyRole("ADMIN", "EMPLOYEE")
                .antMatchers("/requests/admin/employee",
                        "/requests/admin/employee/**")
                .hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/").permitAll()
                .defaultSuccessUrl("/requests")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
                .logoutSuccessUrl("/");
        return http.build();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userAccountService);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
