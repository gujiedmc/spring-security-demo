package com.gujiedmc.study.security.core.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SpringSocial配置
 *
 * @author duyinchuan
 * @date 2019-09-06
 */
@Data
@ConfigurationProperties(prefix = "gujiedmc.social")
public class SocialConfigProperties {

    private SocialProperties weixin;

    private SocialProperties qq;

    @Data
    public static class SocialProperties{
        /**
         * app key
         */
        private String appId;
        /**
         * app secret
         */
        private String appSecret;
        /**
         * 服务商id
         */
        private String providerId;
    }
}
