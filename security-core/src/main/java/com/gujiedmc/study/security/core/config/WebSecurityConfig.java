package com.gujiedmc.study.security.core.config;

import com.gujiedmc.study.security.core.config.properties.SecurityConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author duyinchuan
 * @date 2019-09-02
 */
@EnableConfigurationProperties(SecurityConfigProperties.class)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityConfigProperties securityConfigProperties;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Bean
    @Override
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User.withDefaultPasswordEncoder()
                .username("user")
                .password("123456")
                .roles("all","admin")
                .build());
        return userDetailsManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                // 表单登录
                formLogin()
                    // 自定义登录界面，也可以改成接口，通过接口判定返回页面还是json
                    .loginPage(securityConfigProperties.getLoginRequire())
                    // 登录的UsernamePasswordAuthenticationFilter拦截路径，和前台表单提交路径一致
                    .loginProcessingUrl(securityConfigProperties.getFormLoginProcessUrl())
                    // 直接进入登录页面然后登录成功之后的路径，可以使用AuthenticationSuccessHandler代替
                    .defaultSuccessUrl(securityConfigProperties.getDefaultSuccessUrl())
                    // 登录成功处理，会覆盖successUrl配置
                    .successHandler(loginSuccessHandler)
                    // 自定义账号密码参数名称
                    .usernameParameter("username")
                    .passwordParameter("password")
                .and()
                    .authorizeRequests()
                    .antMatchers(securityConfigProperties.getLoginPage(),
                            securityConfigProperties.getLoginRequire(),
                            securityConfigProperties.getFormLoginProcessUrl(),
                            "/favicon.ico")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                .csrf().disable();
    }
}
// 1. loginPage  loginProcessingUrl必须permitAll不然会一直跳转登录页面 无限302
// 2. csrf.disable()需要，否则 302
// 3. successForwardUrl failureForwardUrl 在登录后转发变成了post请求 导致405 POST not supported