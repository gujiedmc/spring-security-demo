package com.gujiedmc.study.security.core.config.social.qq.connect;

import com.gujiedmc.study.security.core.config.social.qq.api.QQ;
import com.gujiedmc.study.security.core.config.social.qq.api.impl.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * qq服务连接
 *
 * @author duyinchuan
 * @date 2019-09-09
 */
public class QQOAuth2ServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String clientId;

    public QQOAuth2ServiceProvider(String clientId, String clientSecret) {
        super(buildTemplate(clientId, clientSecret));
        this.clientId = clientId;
    }

    private static OAuth2Template buildTemplate(String clientId, String clientSecret) {
        OAuth2Template oAuth2Template = new QQOAuth2Template(clientId, clientSecret);
        oAuth2Template.setUseParametersForClientAuthentication(true);
        return oAuth2Template;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(clientId, accessToken);
    }
}
