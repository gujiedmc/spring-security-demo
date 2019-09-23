package com.gujiedmc.study.security.core.config.social.qq.api.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gujiedmc.study.security.core.config.social.qq.QQConstants;
import com.gujiedmc.study.security.core.config.social.qq.api.QQUserInfo;
import com.gujiedmc.study.security.core.config.social.qq.api.QQUserOperations;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * QQ用户操作实现
 *
 * @author duyinchuan
 * @date 2019-09-09
 */
@Slf4j
public class QQUserOperationsImpl implements QQUserOperations {

    private String clientId;

    private String accessToken;

    private RestTemplate restTemplate;

    private String openId;

    private static final ObjectMapper MAPPER ;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public QQUserOperationsImpl(String clientId, String accessToken, RestTemplate restTemplate) {
        this.clientId = clientId;
        this.restTemplate = restTemplate;
        this.accessToken = accessToken;
        openId = exchangeForOpenId(clientId, accessToken, restTemplate);

    }

    /**
     * 获取openid
     * 返回数据格式：callback( {"client_id":"100550231","openid":"209C8D0151594D0317ED1708503FF5A1"} );
     *
     * @param clientId
     * @param accessToken
     * @param restTemplate
     * @return
     */
    private static String exchangeForOpenId(String clientId, String accessToken, RestTemplate restTemplate) {
        String result = restTemplate.getForObject(QQConstants.OPEN_ID_URL + accessToken, String.class);
        log.info("QQ获取openid返回数据:{}", result);
        return StrUtil.subBetween(result, "\"openid\":\"", "\"} );").trim();
    }

    @SneakyThrows
    @Override
    public QQUserInfo getUserInfo() {
        HashMap<String, Object> uriVariables = new HashMap<>(4);
        uriVariables.put("oauth_consumer_key", clientId);
        uriVariables.put("openid", openId);
        uriVariables.put("access_token", accessToken);
        String url = HttpUtil.urlWithForm(QQConstants.USER_INFO_URL, uriVariables, StandardCharsets.UTF_8, false);

        // 因为响应头为text/html;charset=utf-8，无法识别到MappingJackson2HttpMessageConverter,手动处理
        String result = restTemplate.getForObject(url, String.class, uriVariables);
        QQUserInfo qqUserInfo = MAPPER.readValue(result, QQUserInfo.class);
        qqUserInfo.setOpenId(openId);
        return qqUserInfo;
    }
}
