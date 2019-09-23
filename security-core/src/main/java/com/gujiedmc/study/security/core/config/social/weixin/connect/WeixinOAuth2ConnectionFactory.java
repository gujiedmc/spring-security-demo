package com.gujiedmc.study.security.core.config.social.weixin.connect;

import com.gujiedmc.study.security.core.config.social.weixin.api.WeixinAccessGrant;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * 微信社交登录connection创建
 * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN
 *
 * @author duyinchuan
 * @date 2019-09-06
 */
public class WeixinOAuth2ConnectionFactory extends OAuth2ConnectionFactory {

    public WeixinOAuth2ConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new WeixinOAuth2ServiceProvider(appId, appSecret), null);
    }

    /**
     * 微信可以直接从凭证中获取openid
     *
     * @return
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        return ((WeixinAccessGrant) accessGrant).getOpenid();
    }

    @Override
    public Connection createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), (OAuth2ServiceProvider) getServiceProvider(), new WeixinApiAdapter(extractProviderUserId(accessGrant)));
    }

    @Override
    public Connection createConnection(ConnectionData data) {
        return new OAuth2Connection<>(data, (OAuth2ServiceProvider) getServiceProvider(), new WeixinApiAdapter(data.getProviderId()));
    }
}
