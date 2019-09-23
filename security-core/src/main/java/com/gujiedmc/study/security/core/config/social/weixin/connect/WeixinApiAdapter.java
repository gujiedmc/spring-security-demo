package com.gujiedmc.study.security.core.config.social.weixin.connect;

import com.gujiedmc.study.security.core.config.social.weixin.api.Weixin;
import com.gujiedmc.study.security.core.config.social.weixin.api.WeixinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 将微信用户信息转成OAuth2标准信息
 *
 * @author duyinchuan
 * @date 2019-09-06
 */
public class WeixinApiAdapter implements ApiAdapter<Weixin> {

    private String openId;

    public WeixinApiAdapter(String openId) {
        this.openId = openId;
    }

    @Override
    public boolean test(Weixin api) {
        return false;
    }

    @Override
    public void setConnectionValues(Weixin api, ConnectionValues values) {
        WeixinUserInfo userInfo = api.userOperations().getUserInfo(openId);
        values.setProviderUserId(openId);
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getHeadImgUrl());
    }

    @Override
    public UserProfile fetchUserProfile(Weixin api) {
        return null;
    }

    @Override
    public void updateStatus(Weixin api, String message) {

    }
}
