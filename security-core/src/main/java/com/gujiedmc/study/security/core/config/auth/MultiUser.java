package com.gujiedmc.study.security.core.config.auth;

import com.gujiedmc.study.security.core.support.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 复合登录用户信息
 *
 * @author duyinchuan
 * @date 2019-09-05
 */
public class MultiUser implements UserDetails {

    private Account account;

    private List<SimpleGrantedAuthority> authorities;

    public MultiUser(Account account) {
        this.account = account;
        if (account.getAuthorities() != null) {
            List<SimpleGrantedAuthority> collect = Arrays.stream(account.getAuthorities()).
                    map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }
    }

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    public String getEmail() {
        return account.getEmail();
    }

    public String getDetails() {
        return account.getDetails();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
