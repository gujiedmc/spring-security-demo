package com.gujiedmc.study.security.core.web.controller;

import com.gujiedmc.study.security.core.support.R;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
