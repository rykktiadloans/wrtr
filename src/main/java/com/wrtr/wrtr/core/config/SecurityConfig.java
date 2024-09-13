package com.wrtr.wrtr.core.config;

import com.wrtr.wrtr.core.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.support.MultipartFilter;

/**
 * Configuration of Spring Security
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Bean that handles the permissions of different endpoints
     * @param http HttpSecurity object we configure
     * @return The security filter chain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        hiddenHttpMethodFilter.setMethodParam("_method");
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/**", "/scripts/**", "/styles/**", "/images/**", "/static/**",  "/register", "/", "/upload-dir/**", "/user/**", "/search/users/**", "/error").permitAll()

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form.loginPage("/login").permitAll())
                .logout((logout) -> logout.logoutUrl("/logout").permitAll())
                .addFilterAfter(hiddenHttpMethodFilter, CsrfFilter.class);

        return http.build();
    }

    /**
     * Returns a UserDetailsService object
     * @return UserDetailsService
     */
    @Bean
    public org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
        return new UserService();
    }

    /**
     * Returns an object we use for password encryption
     * @return Password encryptor
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * The provider of the authentication
     * @return DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


}
