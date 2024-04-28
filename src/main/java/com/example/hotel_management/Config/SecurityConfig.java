package com.example.hotel_management.Config;

import com.example.hotel_management.Security.CustomAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    /**
     * Constructor for dependency injection process
     * @param customAuthenticationFailureHandler: A class for custom failure handler when login
     */
    @Autowired
    public SecurityConfig(CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
    }

    /**
     * Create bean passwordEncoder used for whole project
     * @return
     * BCryptPasswordEncoder object
     */
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Point that authentication will use own database
     * @param dataSource: Defined datasource (SQL server)
     * @return
     * JdbcUserDetailsManager object
     */
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    /**
     * Config Spring Security filter
     * @param httpSecurity: HttpSecurity object which is provided by Spring
     * @return
     * HttpSecurity object which is built
     * @throws Exception
     * Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        //Custom login form
        //Redirect login form to home page
        //Config customAuthenticationFailureHandler
        httpSecurity
                .formLogin(form -> form
                        .loginProcessingUrl("/authenticateTheUser")
                        .successForwardUrl("/home")
                        .defaultSuccessUrl("/", true)
                        .failureHandler(customAuthenticationFailureHandler)
                        .loginPage("/login").permitAll());

        String[] staticResources = {
                "/static/**",
                "/css/**",
                "/images/**",
                "/fonts/**",
                "/js/**",};

        //Point that /login API can be access without inhibition
        //Point that /first-page is default url when login successfully
        //Config default directory for Front-end
        //Config some API request
        httpSecurity
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/test").permitAll() // For testing
                        .requestMatchers("/hotel-detail/**").permitAll()
                        .requestMatchers("/suggest").permitAll()
                        .requestMatchers("/search").permitAll()
                        .requestMatchers("/get-sorted-hotels-details").permitAll()
                        .requestMatchers("/get-sorted-hotels-details/**").permitAll()
                        .requestMatchers("/loading-user-page/**").permitAll()
                        .requestMatchers("/loading-user-page").permitAll()
                        .requestMatchers(staticResources).permitAll()
                        .requestMatchers("/resources/**").permitAll()
                        .requestMatchers("/password").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/home/**").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/book-now").permitAll()
                        .requestMatchers("first-page").permitAll()
                        .requestMatchers("/first-page/**").permitAll()
                        .requestMatchers("forget-password").permitAll()
                        .requestMatchers("/login").anonymous()
                        .anyRequest()
                        .authenticated());

        httpSecurity
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("remember-me")
                        .permitAll());

        //Config logout API and delete cookies
        //Config rememberMe feature
        httpSecurity
                .rememberMe(rememberMe -> rememberMe
                        .tokenValiditySeconds(3600*24*30));

        return httpSecurity.build();
    }
}