package com.qhuy.authenms.config;

import com.qhuy.authenms.filter.AuthenFilter;
import com.qhuy.authenms.jwtUtil.JwlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UnAuthoziedUserAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwlUtil util;

    @Autowired
    private AuthenFilter filter;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
//        return builder.userDetailsService(userDetailsService).passwordEncoder(encoder).and().build();
//    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(encoder);
//
//        return authProvider;
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable().authorizeHttpRequests().antMatchers("/api/user/login", "/api/user/save").permitAll()
                .antMatchers("/api/user/getall", "/api/user/get/{id}").hasAuthority("user")
                .anyRequest().authenticated().and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
