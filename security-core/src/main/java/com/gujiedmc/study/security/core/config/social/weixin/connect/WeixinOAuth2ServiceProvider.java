package com.gujiedmc.study.security.core.config.social.weixin.connect;

import com.gujiedmc.study.security.core.config.social.weixin.api.Weixin;
import com.gujiedmc.study.security.core.config.social.weixin.api.impl.WeixinImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * 请求微信服务
 *
 * @author duyinchuan
 * @date 2019-09-06
 */
public class WeixinOAuth2ServiceProvider extends AbstractOAuth2ServiceProvider<Weixin> {

    /**
     * 创建
     */
    public WeixinOAuth2ServiceProvider(String appId, String appSecret) {
        super(createOAuth2Template(appId, appSecret));
    }

    private static OAuth2Template createOAuth2Template(String clientId, String clientSecret) {
        OAuth2Template oAuth2Template = new WeixinOAuth2Template(clientId, clientSecret);
        oAuth2Template.setUseParametersForClientAuthentication(true);
        return oAuth2Template;
    }


    @Override
    public Weixin getApi(String accessToken) {
        return new WeixinImpl(accessToken);
    }

}
