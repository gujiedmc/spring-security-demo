package com.gujiedmc.study.security.core.config.auth;

import com.gujiedmc.study.security.core.config.properties.SecurityConfigProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 复合登录过滤器
 *
 * @author duyinchuan
 * @date 2019-09-05
 */
public class MultiAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    // 注入配置文件
    private SecurityConfigProperties securityConfigProperties;

    protected MultiAuthenticationFilter(SecurityConfigProperties securityConfigProperties) {
        super(securityConfigProperties.getLoginProcessUrl());
        this.securityConfigProperties = securityConfigProperties;
    }

    /**
     * 尝试登录
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String validateCode = request.getParameter("validateCode");

        MultiAuthenticationToken multiAuthenticationToken = new MultiAuthenticationToken(username, password, validateCode);

        Authentication authenticate = this.getAuthenticationManager().authenticate(multiAuthenticationToken);

        return authenticate;
    }
}
