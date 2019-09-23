package com.gujiedmc.study.security.core.config.social.weixin.autoconfig;

import com.gujiedmc.study.security.core.config.properties.SocialConfigProperties;
import com.gujiedmc.study.security.core.config.social.weixin.connect.WeixinOAuth2ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;

/**
 * qq登录自动装配
 *
 * @author duyinchuan
 * @date 2019-09-09
 */
@ConditionalOnProperty(prefix = "gujiedmc.social.weixin", name = "app-id")
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@Configuration
public class WeixinOAuth2AutoConfiguration {


    @Configuration
    @EnableSocial
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public static class QQConfigurerAdapter extends SocialConfigurerAdapter {

        @Autowired
        private SocialConfigProperties socialConfigProperties;

        @Override
        public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
            String appId = socialConfigProperties.getWeixin().getAppId();
            String appSecret = socialConfigProperties.getWeixin().getAppSecret();
            String providerId = socialConfigProperties.getWeixin().getProviderId();
            connectionFactoryConfigurer.addConnectionFactory(new WeixinOAuth2ConnectionFactory(providerId, appId, appSecret));
        }
    }
}
