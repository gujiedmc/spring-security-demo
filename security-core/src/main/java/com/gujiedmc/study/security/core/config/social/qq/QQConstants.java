package com.gujiedmc.study.security.core.config.social.qq;

/**
 * qq地址常量
 *
 * @author duyinchuan
 * @date 2019-09-09
 */
public interface QQConstants {

    /**
     * 用户授权跳转的页面
     */
    String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";
    /**
     * 通过授权码获取凭证的地址
     */
    String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";
    /**
     * 获取openid地址
     */
    String OPEN_ID_URL = "https://graph.qq.com/oauth2.0/me?access_token=";
    /**
     * 请求用户详情的地址
     */
    String USER_INFO_URL = "https://graph.qq.com/user/get_user_info";
}
