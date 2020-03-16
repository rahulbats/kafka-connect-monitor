package com.connect.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Value("${auth.enabled}")
    private boolean authEnabled;
    @Value("${auth.username}")
    private String username;
    @Value("${auth.password}")
    private String password;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        if(authEnabled)
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception
    {
        if(authEnabled)
        auth.inMemoryAuthentication()
                .withUser(username)
                .password("{noop}"+password)
                .roles("USER");
    }
}
