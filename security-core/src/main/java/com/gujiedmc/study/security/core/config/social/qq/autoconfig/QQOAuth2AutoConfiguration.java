package com.gujiedmc.study.security.core.config.social.qq.autoconfig;

import com.gujiedmc.study.security.core.config.properties.SocialConfigProperties;
import com.gujiedmc.study.security.core.config.social.qq.connect.QQOAuth2ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;

/**
 * qq登录自动装配
 *
 * @author duyinchuan
 * @date 2019-09-09
 */
@ConditionalOnProperty(prefix = "gujiedmc.social.qq",name = "app-id")
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@Configuration
public class QQOAuth2AutoConfiguration {


    @Configuration
    @EnableSocial
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public static class QQConfigurerAdapter extends SocialConfigurerAdapter{

        @Autowired
        private SocialConfigProperties socialConfigProperties;

        @Override
        public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
            String appId = socialConfigProperties.getQq().getAppId();
            String appSecret = socialConfigProperties.getQq().getAppSecret();
            String providerId = socialConfigProperties.getQq().getProviderId();
            connectionFactoryConfigurer.addConnectionFactory(new QQOAuth2ConnectionFactory(providerId, appId, appSecret));
        }
    }
}
