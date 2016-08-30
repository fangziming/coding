package com.xbao.modules.sys.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import com.xbao.common.utils.CacheUtils;

/**
 * 安全管理中心。<br>
 * 主要目的是保存session和ticket之间的关系。
 * @author sheungxin
 * 
 */
public class CasSecurityManager extends DefaultWebSecurityManager{
    
    @Override
    protected void onSuccessfulLogin(AuthenticationToken token,
            AuthenticationInfo info, Subject subject) {
        
        if (token instanceof CasToken) {
            CacheUtils.put((String)token.getCredentials(), subject.getSession(false).getId());
            subject.getSession(false).setAttribute("_serviceTicket_", token.getCredentials());
        }
        
        super.onSuccessfulLogin(token, info, subject);
    }
}
