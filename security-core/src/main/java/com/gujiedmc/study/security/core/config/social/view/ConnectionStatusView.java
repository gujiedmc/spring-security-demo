package com.gujiedmc.study.security.core.config.social.view;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 所有社交平台绑定状况
 *
 * @author duyinchuan
 * @date 2019-09-12
 */
@Component("connect/status")
public class ConnectionStatusView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) map.get("connectionMap");
        Map<String, String> result = new HashMap<>();
        connections.forEach((k,v) -> {
            if (CollectionUtil.isEmpty(v)){
                return;
            }
            Connection<?> connection = v.get(0);
            result.put(k,connection.getDisplayName());
        });

        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(result);

        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(value);
    }
}
