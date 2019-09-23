package com.gujiedmc.study.security.core.config.social.view;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.GenericConnectionStatusView;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 单一社交平台绑定状况视图
 *
 * @author duyinchuan
 * @date 2019-09-12
 */
public class ConnectionOneStatusView extends GenericConnectionStatusView {

    public ConnectionOneStatusView(String providerId, String providerDisplayName) {
        super(providerId, providerDisplayName);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        List<Connection<?>> connections = (List<Connection<?>>) map.get("connections");
        if (CollectionUtil.isNotEmpty(connections)) {
            Connection<?> connection = connections.get(0);
            String displayName = connection.getDisplayName();
            httpServletResponse.getWriter().write("<h3>已绑定成功:" + displayName + "</h3>");
        } else {
            httpServletResponse.getWriter().write("<h3>尚未绑定</h3>");
        }
    }
}
