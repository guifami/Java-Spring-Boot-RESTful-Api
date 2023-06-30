package com.jsprestfulapi.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private JwtTokenProvider _tokenProvider;

    public JwtConfigurer(JwtTokenProvider tokenProvider){
        this._tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http){
        JwtTokenFilter customFilter = new JwtTokenFilter(_tokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
