package com.gujiedmc.study.security.core.repository;

import com.gujiedmc.study.security.core.support.Account;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 用户信息
 *
 * @author duyinchuan
 * @date 2019-09-05
 */
@Repository
public class AccountRepository {

    public static List<Account> CACHED_ACCOUNT = new ArrayList<>();

    static {
        Account account = new Account();
        account.setUsername("account");
        account.setEmail("account@qq.com");
        account.setPhone("13012345678");
        account.setPassword("123456");
        account.setAuthorities(new String[]{"ROLE_ADMIN", "ROLE_ALL"});
        account.setDetails("可爱的");

        CACHED_ACCOUNT.add(account);
    }

    public Optional<Account> getByUsername(String username) {
        Optional<Account> optional = CACHED_ACCOUNT.stream()
                .filter(account -> account.getUsername().equalsIgnoreCase(username))
                .findAny();
        return optional;
    }

    public Optional<Account> getByEmail(String email) {
        Optional<Account> optional = CACHED_ACCOUNT.stream()
                .filter(account -> account.getEmail().equalsIgnoreCase(email))
                .findAny();
        return optional;
    }

    public Optional<Account> getByPhone(String phone) {
        Optional<Account> optional = CACHED_ACCOUNT.stream()
                .filter(account -> account.getPhone().equalsIgnoreCase(phone))
                .findAny();
        return optional;
    }
}
