package com.xbao.common.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;

import com.xbao.common.utils.StringUtils;

/**
 * 利用CORS实现跨域请求
 * @author sheungxin
 * 可参考：http://newhtml.net/using-cors/
 *
 */
public class CorsFilter implements Filter{

	/*允许访问的域名，多用于跨域访问问题，多个以逗号隔开*/
	private String allowOrigin;
	
	/*允许的请求方式，GET,POST,PUT,DELETE,...，多个以逗号隔开*/
	private String allowMethods;
	
	/*请求当中包含cookies信息：true（必为小写），反之略去该项，而不是填写false*/
	private String allowCredentials;
	
	/*以逗号分隔的列表，可以返回所有支持的头部*/
	private String allowHeaders;
	
	/*定义可允许的额外头信息*/
	private String exposeHeaders;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request =(HttpServletRequest) req;
		HttpServletResponse response =(HttpServletResponse) res;
		if (StringUtils.isNotEmpty(allowOrigin)) {
			if(allowOrigin.equals("*")){
				response.setHeader("Access-Control-Allow-Origin", allowOrigin);
			}else{
				List<String> allowOriginList =Arrays.asList(allowOrigin.split(","));
				if (CollectionUtils.isNotEmpty(allowOriginList)) {
					String currentOrigin =request.getHeader("Origin");
					if (allowOriginList.contains(currentOrigin)) {
						response.setHeader("Access-Control-Allow-Origin", currentOrigin);
					}
				}
			}
			
		}
		if (StringUtils.isNotEmpty(allowMethods)) {
			response.setHeader("Access-Control-Allow-Methods", allowMethods);
		}
		if (StringUtils.isNotEmpty(allowCredentials)) {
			response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
		}
		if (StringUtils.isNotEmpty(allowHeaders)) {
			response.setHeader("Access-Control-Allow-Headers", allowHeaders);
		}
		if (StringUtils.isNotEmpty(exposeHeaders)) {
			response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		allowOrigin =filterConfig.getInitParameter("allowOrigin");
		allowMethods =filterConfig.getInitParameter("allowMethods");
		allowCredentials =filterConfig.getInitParameter("allowCredentials");
		allowHeaders =filterConfig.getInitParameter("allowHeaders");
		exposeHeaders =filterConfig.getInitParameter("exposeHeaders");
	}

}
