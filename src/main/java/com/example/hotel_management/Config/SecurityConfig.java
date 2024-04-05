package com.example.hotel_management.Config;

//import com.example.hotel_management.failureHandler.CustomAuthenticationFailureHandler;
import com.example.hotel_management.Security.CustomAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    //Create bean passwordEncoder used for whole project
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Point that authentication will use own database
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    //Config Spring Security filter
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {


        //Custom login form
        //Redirect login form to home page
        //Config customAuthenticationFailureHandler
        //Point that /login API can be access without inhibition
        httpSecurity
                .formLogin(form -> form
                        .loginProcessingUrl("/authenticateTheUser")
                        .successForwardUrl("/home")
                        .defaultSuccessUrl("/first-page", true)
                        .failureHandler(customAuthenticationFailureHandler)
                        .loginPage("/login").permitAll());

        //Config default directory for Front-end
        String[] staticResources = {
                "/css/**",
                "/images/**",
                "/fonts/**",
                "/js/**",};

        //Config some API request
        httpSecurity
                .authorizeHttpRequests(auth->auth
//                                .anyRequest().permitAll()); //For testing
                        .requestMatchers(staticResources).permitAll()
                        .requestMatchers("/resources/**").permitAll()
                        .requestMatchers("/password").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/booknow").permitAll()
                        .requestMatchers("first-page").permitAll()
                        .requestMatchers("/login").anonymous()
                        .anyRequest()
                        .authenticated());

        //Config logout API and delete cookies
        httpSecurity
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("remember-me")
                        .permitAll());

        //Config rememberMe feature
        httpSecurity
                .rememberMe(rememberMe -> rememberMe
                        .tokenValiditySeconds(3600*24*30));

        return httpSecurity.build();
    }
}