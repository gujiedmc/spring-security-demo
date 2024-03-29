package com.gujiedmc.study.security.core.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author duyinchuan
 * @date 2019-09-03
 */
@ConfigurationProperties(prefix = "gujiedmc.security")
@Data
public class SecurityConfigProperties {
    /**
     * 登录页面ui路径
     */
    private String loginPage = "/login.html";
    /**
     * 未登录处理链接，可以直接使用{@link #loginPage}，也可以使用接口处理
     */
    private String loginRequire = "/auth/require";
    /**
     * 表单登录处理路径，需要和前端登录路径匹配
     */
    private String formLoginProcessUrl = "/auth/form";
    /**
     * 自定义复合登录处理路径，需要和前端登录路径匹配
     */
    private String loginProcessUrl = "/auth/login";
    /**
     * 默认成功路径，登录成功时跳转路径
     */
    private String defaultSuccessUrl = "/index.html";
    /**
     * 登录成功时，跳转方式
     */
    private LoginType loginType = LoginType.HTML;
    /**
     * 退出登录处理路径，需要和前端登录路径匹配
     */
    private String logoutUrl = "/auth/logout";
    /**
     * 同一个用户在系统中的最大session数，默认不限制
     */
    private int maximumSessions = Integer.MAX_VALUE;
    /**
     * 达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉
     */
    private boolean maxSessionsPreventsLogin;
}
