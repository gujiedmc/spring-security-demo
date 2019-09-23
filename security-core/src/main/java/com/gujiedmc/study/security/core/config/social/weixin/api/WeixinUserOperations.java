package com.gujiedmc.study.security.core.config.social.weixin.api;

/**
 * 微信用户操作
 *
 * @author duyinchuan
 * @date 2019-09-09
 */
public interface WeixinUserOperations {

    WeixinUserInfo getUserInfo(String openid);
}
