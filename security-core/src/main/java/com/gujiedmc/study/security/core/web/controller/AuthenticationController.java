package com.gujiedmc.study.security.core.web.controller;

import com.gujiedmc.study.security.core.config.properties.SecurityConfigProperties;
import com.gujiedmc.study.security.core.support.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录状态
 *
 * @author duyinchuan
 * @date 2019-09-03
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityConfigProperties securityConfigProperties;

    /**
     * 用户未登录时处理。
     * 如果能拿到之前的请求，并且连接是否以html结尾，是则跳转之前的页面，否则返回json数据
     */
    @SneakyThrows
    @GetMapping("/require")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R authenticationRequire(HttpServletRequest request, HttpServletResponse response) {
        // 查看是否有之前跳转的请求
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的链接:{}", redirectUrl);
            if (redirectUrl.endsWith(".html")) {
                redirectStrategy.sendRedirect(request, response,
                        securityConfigProperties.getLoginPage());
            }
        }
        return R.ok("尚未登录");
    }
}
