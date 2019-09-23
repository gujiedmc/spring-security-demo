package com.gujiedmc.study.security.core.config.social.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

/**
 * spring social 根据id查询用户
 *
 * @author duyinchuan
 * @date 2019-09-10
 */
@Slf4j
@Service
public class SocialUserDetailsServiceImpl implements SocialUserDetailsService {

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        return buildUser(userId);
    }

    private SocialUserDetails buildUser(String userId) {
        // 根据用户名查找用户信息
        //根据查找到的用户信息判断用户是否被冻结
        String password = "123456";
//        String password = passwordEncoder.encode("123456");
        log.info("数据库密码是:" + password);
        return new SocialUser(userId, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
