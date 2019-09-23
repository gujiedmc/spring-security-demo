package com.gujiedmc.study.security.core.config.social.weixin.connect;

import com.gujiedmc.study.security.core.config.social.weixin.WeixinConstants;
import com.gujiedmc.study.security.core.config.social.weixin.api.WeixinAccessGrant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 微信rest请求，微信非标准参数，需要自定义处理
 *
 * @author duyinchuan
 * @date 2019-09-09
 */
@Slf4j
public class WeixinOAuth2Template extends OAuth2Template {

    private String clientId;

    private String clientSecret;

    public WeixinOAuth2Template(String clientId, String clientSecret) {
        super(clientId, clientSecret, WeixinConstants.AUTHORIZE_URL, WeixinConstants.ACCESS_TOKEN_URL);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return buildAuthenticateUrl(parameters);
    }

    /**
     * 跳转微信授权页面，微信的非标准参数
     *
     * @param parameters
     * @return
     */
    @Override
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        parameters.set("appid", clientId);
        parameters.set("response_type", "code");
        parameters.set("scope", "snsapi_login");
        return super.buildAuthenticateUrl(parameters);
    }

    /**
     * 微信授权码处理时，参数非标准需要特殊处理
     *
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        parameters.set("appid", parameters.getFirst("client_id"));
        parameters.set("secret", parameters.getFirst("client_secret"));
        parameters.remove("client_id");
        parameters.remove("client_secret");
        return super.postForAccessGrant(accessTokenUrl, parameters);
    }

    /**
     * 自定义微信授权令牌
     */
    @Override
    protected AccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, Map<String, Object> response) {
        return new WeixinAccessGrant(accessToken, scope, refreshToken, expiresIn, (String) response.get("unionid"), (String) response.get("openid"));
    }

    /**
     * 解析字符串响应
     *
     * @return
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.getMessageConverters().add(new WechatMappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    /**
     * 微信返回的数据为json格式，但是响应头为text/plain，默认配置不能转化，所以需要手动配置一个解析器
     */
    public static class WechatMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

        public WechatMappingJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>(1);
            mediaTypes.add(MediaType.TEXT_PLAIN);
            setSupportedMediaTypes(mediaTypes);
        }

    }
}












