package com.gujiedmc.study.security.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gujiedmc.study.security.core.config.properties.LoginType;
import com.gujiedmc.study.security.core.config.properties.SecurityConfigProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理
 *
 * @author duyinchuan
 * @date 2019-09-03
 */
@AllArgsConstructor
@Component
@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private SecurityConfigProperties securityConfigProperties;

    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登录成功:{}", authentication.getName());

        // 如果配置为返回json则返回当前用户信息
        if (LoginType.JSON.equals(securityConfigProperties.getLoginType())) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        }
        // 否则使用默认跳转
        else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
