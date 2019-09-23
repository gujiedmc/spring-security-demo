package com.gujiedmc.study.security.core.web.controller;

import com.gujiedmc.study.security.core.config.social.weixin.api.Weixin;
import com.gujiedmc.study.security.core.support.Account;
import com.gujiedmc.study.security.core.support.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author duyinchuan
 * @date 2019-09-01
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @GetMapping("/me")
    public @ResponseBody
    R me(Authentication authentication){
        return R.ok(authentication);
    }

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @PostMapping("/regist")
    public void regist(Account account, HttpServletRequest request) {

        //不管是注册用户还是绑定用户，都会拿到一个用户唯一标识。
        String userId = account.getUsername();
        providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
    }
}
