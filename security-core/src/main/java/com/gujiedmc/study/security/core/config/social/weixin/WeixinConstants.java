package com.gujiedmc.study.security.core.config.social.weixin;

/**
 * 微信常量
 *
 * @author duyinchuan
 * @date 2019-09-09
 */
public interface WeixinConstants {
    /**
     * 用户授权跳转的页面
     */
    String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect";
    /**
     * 检查access_token有效性的地址
     */
    String AUTHENTICATE_URL ="https://api.weixin.qq.com/sns/auth";
    /**
     * 通过授权码获取凭证的地址
     */
    String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    /**
     * 请求用户详情的地址
     */
    String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";
    /**
     * 刷新token的地址
     */
    String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
}
