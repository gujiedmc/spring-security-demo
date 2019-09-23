package com.gujiedmc.study.security.core.config.social.weixin.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.social.oauth2.AccessGrant;

/**
 * 微信令牌，和标准OAuth2协议不同
 *
 * @author duyinchuan
 * @date 2019-09-06
 */
@Data
public class WeixinAccessGrant extends AccessGrant {

    /**
     * 当且仅当该网站应用已获得该用户的userinfo授权时，才会出现该字段。
     */
    private String unionid;
    /**
     * 授权用户唯一标识
     */
    private String openid;

    public WeixinAccessGrant() {
        super("");
    }

    public WeixinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, String unionid, String openid) {
        super(accessToken, scope, refreshToken, expiresIn);
        this.unionid = unionid;
        this.openid = openid;
    }

}
