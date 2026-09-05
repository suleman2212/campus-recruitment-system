package com.example.College_Placement_Management.configuration;

import com.example.College_Placement_Management.Service.UserService;
import com.example.College_Placement_Management.security.JwtFilter;
import org.apache.catalina.filters.HttpHeaderSecurityFilter;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    {
        return httpSecurity
                .csrf(a->a.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(a->a
                        .requestMatchers(
                                "/users/login",
                                "/users/register/student",
                                "/users/register/company",
                                "/users/register/college"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/college/fetch").permitAll()
                        .anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .build();

    }
//    @Bean
//    public UserDetailsService userDetailsService()
//    {
//        return new UserService();
//    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
        return provider;
    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration configuration)
    {
        return configuration.getAuthenticationManager();
    }

}
