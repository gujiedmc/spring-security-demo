package com.gujiedmc.study.security.core.config.auth;

import com.gujiedmc.study.security.core.config.LoginSuccessHandler;
import com.gujiedmc.study.security.core.config.auth.provider.AccountAuthenticationProvider;
import com.gujiedmc.study.security.core.config.auth.provider.EmailAuthenticationProvider;
import com.gujiedmc.study.security.core.config.auth.service.AccountUserDetailsService;
import com.gujiedmc.study.security.core.config.auth.service.EmailUserDetailsService;
import com.gujiedmc.study.security.core.config.properties.SecurityConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 复合登录配置
 *
 * @author duyinchuan
 * @date 2019-09-05
 */
@Configuration
public class MultiAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AccountUserDetailsService accountUserDetailsService;

    @Autowired
    private EmailUserDetailsService emailUserDetailsService;

    @Autowired
    private SecurityConfigProperties securityConfigProperties;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Override
    public void configure(HttpSecurity http) {
        MultiAuthenticationFilter multiAuthenticationFilter = new MultiAuthenticationFilter(securityConfigProperties);
        multiAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        // 设置authenticationManager
        multiAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        http
                .authenticationProvider(new AccountAuthenticationProvider(accountUserDetailsService))
                .authenticationProvider(new EmailAuthenticationProvider(emailUserDetailsService))
                .addFilterAfter(multiAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
