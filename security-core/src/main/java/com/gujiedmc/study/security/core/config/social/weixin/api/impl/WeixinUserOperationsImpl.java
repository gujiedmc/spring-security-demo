package com.gujiedmc.study.security.core.config.social.weixin.api.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gujiedmc.study.security.core.config.social.weixin.WeixinConstants;
import com.gujiedmc.study.security.core.config.social.weixin.api.WeixinUserInfo;
import com.gujiedmc.study.security.core.config.social.weixin.api.WeixinUserOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信请求操作
 *
 * @author duyinchuan
 * @date 2019-09-09
 */
@Slf4j
public class WeixinUserOperationsImpl implements WeixinUserOperations {

    private static final ObjectMapper MAPPER ;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private RestTemplate restTemplate;

    private String accessToken;

    public WeixinUserOperationsImpl(RestTemplate restTemplate, String accessToken) {
        this.restTemplate = restTemplate;
        this.accessToken = accessToken;
    }

    @Override
    public WeixinUserInfo getUserInfo(String openid) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("openid", openid);
        params.put("access_token", accessToken);

        String url = HttpUtil.urlWithForm(WeixinConstants.USER_INFO_URL, params, StandardCharsets.UTF_8, false);

        String response = restTemplate.getForObject(url, String.class, params);
        log.info("根据openid获取微信用户信息:{}", response);
        if (StrUtil.containsIgnoreCase(response, "errcode")) {
            return null;
        }
        WeixinUserInfo profile = null;
        try {
            profile = MAPPER.readValue(response, WeixinUserInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profile;
    }
}
