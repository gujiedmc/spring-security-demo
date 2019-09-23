package com.gujiedmc.study.security.core.config.social;

import com.gujiedmc.study.security.core.config.social.view.ConnectionOneStatusView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.GenericConnectionStatusView;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * SpringSocial配置
 *
 * @author duyinchuan
 * @date 2019-09-06
 */
@EnableSocial
@Configuration
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Autowired
    private DataSource dataSource;

    @Bean
    public SpringSocialConfigurer springSocialConfigurer(){
        SpringSocialConfigurer springSocialConfigurer = new MySpringSocialConfigurer("/qqLogin");
        // 用户第一次用社交账号登录，跳转页面，可以使用接口引导用户注册，返回一些用户信息，这里简单使用页面处理
        springSocialConfigurer.signupUrl("/signUp.html");
        return springSocialConfigurer;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator)) {
        };
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());
//        repository.setTablePrefix("imooc_");
        if(connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    /**
     * 修改社交登录拦截路径
     */
    public class MySpringSocialConfigurer extends SpringSocialConfigurer {

        private String filterProcessesUrl;

        public MySpringSocialConfigurer(String filterProcessesUrl) {
            this.filterProcessesUrl = filterProcessesUrl;
        }

        @Override
        protected <T> T postProcess(T object) {
            SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
            filter.setFilterProcessesUrl(filterProcessesUrl);
            return (T) filter;
        }

    }

    @Bean("connect/weixinConnected")
    public GenericConnectionStatusView weixinStatusView(){
        return new ConnectionOneStatusView("weixin","微信");
    }
    @Bean("connect/callback.doConnected")
    public GenericConnectionStatusView qqStatusView(){
        return new ConnectionOneStatusView("QQ","QQ");
    }
}
