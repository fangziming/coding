package com.xbao.modules.sys.security;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import com.xbao.common.utils.CacheUtils;

/**
 * 票据及session监听器
 * @author sheungxin
 * 
 */
public class TicketSessionListener implements SessionListener{

    @Override
    public void onStart(Session session) {
        
    }

    @Override
    public void onStop(Session session) {
        String ticket = (String)session.getAttribute("_serviceTicket_");
        if (ticket != null) {
            CacheUtils.remove(ticket);
        }
    }

    @Override
    public void onExpiration(Session session) {
        onStop(session);
    }
}
