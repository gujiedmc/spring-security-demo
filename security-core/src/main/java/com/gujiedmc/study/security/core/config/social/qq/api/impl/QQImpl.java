package com.gujiedmc.study.security.core.config.social.qq.api.impl;

import com.gujiedmc.study.security.core.config.social.qq.api.QQ;
import com.gujiedmc.study.security.core.config.social.qq.api.QQUserOperations;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * qq访问实现
 *
 * @author duyinchuan
 * @date 2019-09-09
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private String clientId;

    private QQUserOperations userOperations;

    public QQImpl(String clientId, String accessToken) {
        super(accessToken);
        this.clientId = clientId;
        initSubApis(accessToken);
    }

    private void initSubApis(String accessToken) {
        userOperations = new QQUserOperationsImpl(clientId, accessToken, getRestTemplate());
    }

    @Override
    public QQUserOperations userOperations() {
        return userOperations;
    }

    /**
     * 默认注册的StringHttpMessageConverter字符集为ISO-8859-1，而微信返回的是UTF-8的，所以覆盖了原来的方法。
     */
    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        messageConverters.add(getFormMessageConverter());
        messageConverters.add(getJsonMessageConverter());
        messageConverters.add(getByteArrayMessageConverter());
        return messageConverters;
    }
}
