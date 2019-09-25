package com.gujiedmc.study.security.core.config.session;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 定制session失效策略
 *
 * @author duyinchuan
 * @date 2019-09-25
 */
public class CustomSessionExpiredStrategy implements SessionInformationExpiredStrategy {

    private boolean createNewSession;

    public CustomSessionExpiredStrategy(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletRequest request = event.getRequest();
        HttpServletResponse response = event.getResponse();
        // 重新创建session
        if (createNewSession){
            request.getSession();
        }
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request,response,"/login.html");
    }
}
