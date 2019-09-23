package com.gujiedmc.study.security.core.config.social.qq.connect;

import com.gujiedmc.study.security.core.config.social.qq.api.QQ;
import com.gujiedmc.study.security.core.config.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * qq适配器
 *
 * @author duyinchuan
 * @date 2019-09-09
 */
public class QQApiAdapter implements ApiAdapter<QQ> {

    @Override
    public boolean test(QQ api) {

        return false;
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.userOperations().getUserInfo();
        values.setProviderUserId(userInfo.getOpenId());
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {

    }
}
