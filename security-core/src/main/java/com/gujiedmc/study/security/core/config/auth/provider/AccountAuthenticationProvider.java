package com.gujiedmc.study.security.core.config.auth.provider;

import com.gujiedmc.study.security.core.config.auth.MultiAuthenticationToken;
import com.gujiedmc.study.security.core.config.auth.MultiUser;
import com.gujiedmc.study.security.core.config.auth.service.AccountUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * 账号密码登录
 *
 * @author duyinchuan
 * @date 2019-09-05
 */
@Slf4j
public class AccountAuthenticationProvider implements AuthenticationProvider {

    private AccountUserDetailsService accountUserDetailsService;

    public AccountAuthenticationProvider(AccountUserDetailsService accountUserDetailsService) {
        this.accountUserDetailsService = accountUserDetailsService;
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("通过账号登录认证:{}", authentication);
        MultiAuthenticationToken multiAuthenticationToken = (MultiAuthenticationToken) authentication;
        String principal = multiAuthenticationToken.getPrincipal();
        if (!EMAIL_PATTERN.matcher(principal).matches()) {
            return null;
        }

        MultiUser user = accountUserDetailsService.loadUserByUsername(principal);

        this.checkAuthentication(multiAuthenticationToken, user);

        // 封装登录成功的认证信息
        MultiAuthenticationToken successToken = new MultiAuthenticationToken(multiAuthenticationToken.getPrincipal(), user.getAuthorities());
        successToken.setAuthenticated(true);
        successToken.setDetails(user.getDetails());
        log.info("账号登录成功:{}", successToken);
        return successToken;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (MultiAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private void checkAuthentication(MultiAuthenticationToken authentication, MultiUser user) {
        if (user.getPassword() == null) {
            throw new BadCredentialsException("user's password is necessary");
        }
        if (!authentication.getCredentials().equalsIgnoreCase(user.getPassword())) {
            throw new BadCredentialsException("password is not correct");
        }
    }
}
