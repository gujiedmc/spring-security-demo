package com.gujiedmc.study.security.core.config.auth.service;

import com.gujiedmc.study.security.core.config.auth.MultiUser;
import com.gujiedmc.study.security.core.repository.AccountRepository;
import com.gujiedmc.study.security.core.support.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 根据邮箱查询用户
 *
 * @author duyinchuan
 * @date 2019-09-05
 */
@Component
public class EmailUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public MultiUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.getByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new MultiUser(account);
    }
}
