package com.gujiedmc.study.security.core.config.auth.service;

import com.gujiedmc.study.security.core.config.auth.MultiUser;
import com.gujiedmc.study.security.core.repository.AccountRepository;
import com.gujiedmc.study.security.core.support.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 根据账号查询用户
 *
 * @author duyinchuan
 * @date 2019-09-05
 */
@Component
public class AccountUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public MultiUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new MultiUser(account);
    }
}
