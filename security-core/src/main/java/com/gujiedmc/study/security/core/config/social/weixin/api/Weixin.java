package com.gujiedmc.study.security.core.config.social.weixin.api;

/**
 * 定义微信可以执行的操作
 *
 * @author duyinchuan
 * @date 2019-09-09
 */
public interface Weixin {

    /**
     * 用户操作
     */
    WeixinUserOperations userOperations();
}
