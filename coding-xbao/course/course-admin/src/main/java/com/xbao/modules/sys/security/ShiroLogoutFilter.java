package com.xbao.modules.sys.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.NativeSessionManager;
import org.jasig.cas.client.session.SingleSignOutHandler;
import org.jasig.cas.client.util.AbstractConfigurationFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;
import com.xbao.common.utils.CacheUtils;
import com.xbao.common.utils.SpringContextHolder;

/**
 * 接收单点登出server请求，注销本地session
 * @author sheungxin
 *
 */
public class ShiroLogoutFilter extends AbstractConfigurationFilter{

	private NativeSessionManager nativeSessionManager=SpringContextHolder.getBean(NativeSessionManager.class);
	private static final SingleSignOutHandler handler = new SingleSignOutHandler();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if (!isIgnoreInitConfiguration()) {
            handler.setArtifactParameterName(getPropertyFromInitParams(filterConfig, "artifactParameterName", "ticket"));
            handler.setLogoutParameterName(getPropertyFromInitParams(filterConfig, "logoutParameterName", "logoutRequest"));
        }
        handler.init();
	}

	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		
		if (handler.isLogoutRequest(request)) {
		    final String logoutMessage = CommonUtils.safeGetParameter(request, "logoutRequest");
		    final String token = XmlUtils.getTextForElement(logoutMessage, "SessionIndex");
		    String sessionId = (String)CacheUtils.get(token);
		    nativeSessionManager.stop(new DefaultSessionKey(sessionId));
		    return;
		}
		
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
	}

}
