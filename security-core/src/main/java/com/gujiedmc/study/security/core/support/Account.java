package com.gujiedmc.study.security.core.support;

import lombok.Data;

/**
 * 账号信息
 *
 * @author duyinchuan
 * @date 2019-09-05
 */
@Data
public class Account {

    private String username;

    private String email;

    private String password;

    private String phone;

    private String[] authorities;

    private String details;
}
