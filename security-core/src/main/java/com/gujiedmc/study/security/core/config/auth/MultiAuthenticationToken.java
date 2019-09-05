package com.gujiedmc.study.security.core.config.auth;

import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * 复合登录请求参数封装
 *
 * @author duyinchuan
 * @date 2019-09-05
 */
@ToString
public class MultiAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 账号。可以是手机号，邮箱，用户名
     */
    private String username;
    /**
     * 密码。
     */
    private String password;
    /**
     * 验证码
     */
    private String validateCode;

    public MultiAuthenticationToken(String username, String password, String validateCode) {
        super(null);
        this.username = username;
        this.password = password;
        this.validateCode = validateCode;
    }

    public MultiAuthenticationToken(String username, List<SimpleGrantedAuthority> authorities) {
        super(authorities);
        this.username = username;
    }

    @Override
    public String getPrincipal() {
        return username;
    }

    @Override
    public String getCredentials() {
        return password;
    }
}
