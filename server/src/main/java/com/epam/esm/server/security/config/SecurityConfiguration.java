package com.epam.esm.server.security.config;

import com.epam.esm.server.security.BadCredentialsFilter;
import com.epam.esm.server.security.TokenFilter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final BadCredentialsFilter badCredentialsFilter;
    private final TokenFilter tokenFilter;

    public SecurityConfiguration(BadCredentialsFilter badCredentialsFilter, TokenFilter tokenFilter) {
        this.badCredentialsFilter = badCredentialsFilter;
        this.tokenFilter = tokenFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(badCredentialsFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(tokenFilter, BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();
    }
}
