package com.gujiedmc.study.security.core.config.social.qq.connect;

import cn.hutool.core.util.StrUtil;
import com.gujiedmc.study.security.core.config.social.qq.QQConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * qq请求定制
 *
 * @author duyinchuan
 * @date 2019-09-10
 */
@Slf4j
public class QQOAuth2Template extends OAuth2Template {

    public QQOAuth2Template(String clientId, String clientSecret) {
        super(clientId, clientSecret, QQConstants.AUTHORIZE_URL, QQConstants.ACCESS_TOKEN_URL);
    }

    /**
     * 因为qq返回的数据并不是json，所以需要自行处理
     * 返回格式：access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14
     *
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);

        log.info("获取accessToke的响应：" + responseStr);

        String[] items = StrUtil.split(responseStr, "&");

        String accessToken = StrUtil.subAfter(items[0], "=", false);
        Long expiresIn = new Long(StrUtil.subAfter(items[1], "=", false));
        String refreshToken = StrUtil.subAfter(items[2], "=", false);

        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }

    /**
     * 解析字符串响应
     * @return
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
    }
}
