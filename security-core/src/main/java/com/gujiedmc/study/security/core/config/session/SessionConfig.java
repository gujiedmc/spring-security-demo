package com.gujiedmc.study.security.core.config.session;

import com.gujiedmc.study.security.core.config.properties.SecurityConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 配置redis分布式session
 *
 * @author duyinchuan
 * @date 2019-09-23
 */
@ConditionalOnClass(name = "org.springframework.session.data.redis.RedisOperationsSessionRepository")
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 120) // session超时时长，单位为秒
@Configuration
public class SessionConfig {

    @Autowired
    private SecurityConfigProperties securityConfigProperties;

    @Bean
    public SessionManagementConfigurer sessionManagementConfigurer() {
        return new SessionManagementConfigurer()// 无效session
                .invalidSessionUrl(securityConfigProperties.getLoginRequire())
                // 同一个用户最多登录次数
                .maximumSessions(securityConfigProperties.getMaximumSessions())
                // 多次登录是否阻止新的登录请求
                .maxSessionsPreventsLogin(securityConfigProperties.isMaxSessionsPreventsLogin())
                // session失效策略
                .expiredSessionStrategy(new CustomSessionExpiredStrategy(true)).and();
    }
}
