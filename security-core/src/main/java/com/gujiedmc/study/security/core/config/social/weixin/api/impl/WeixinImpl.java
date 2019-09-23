package com.gujiedmc.study.security.core.config.social.weixin.api.impl;

import com.gujiedmc.study.security.core.config.social.weixin.api.Weixin;
import com.gujiedmc.study.security.core.config.social.weixin.api.WeixinUserOperations;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信操作
 *
 * @author duyinchuan
 * @date 2019-09-09
 */
public class WeixinImpl extends AbstractOAuth2ApiBinding implements Weixin {

    private String accessToken;
    /**
     * 用户模块操作
     */
    private WeixinUserOperations userOperations;

    public WeixinImpl() {
        initSubApis();
    }

    public WeixinImpl(String accessToken) {
        super(accessToken);
        this.accessToken = accessToken;
        initSubApis();
    }

    /**
     * 初始化子模块操作
     */
    private void initSubApis() {
        userOperations = new WeixinUserOperationsImpl(getRestTemplate(), accessToken);
    }

    @Override
    public WeixinUserOperations userOperations() {
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
