package com.gujiedmc.study.security.core.config.social.qq.connect;

import com.gujiedmc.study.security.core.config.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * qq连接创建
 *
 * @author duyinchuan
 * @date 2019-09-09
 */
public class QQOAuth2ConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    public QQOAuth2ConnectionFactory(String providerId, String clientId, String clientSecret) {
        super(providerId, new QQOAuth2ServiceProvider(clientId, clientSecret), new QQApiAdapter());
    }


}
