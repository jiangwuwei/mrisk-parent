package com.zoom.risk.operating.web.filter;

import com.zoom.risk.operating.web.utils.SessionUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginVerifyFilter implements Filter {
	private static String skips[] = null;

	@Override
    public void init(final FilterConfig config) throws ServletException {
		final String value = config.getInitParameter("loginSkips");
		if (value != null) {
			skips = value.split(";");
		}
	}

  	@Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest r = (HttpServletRequest)request;
		final HttpServletResponse p = (HttpServletResponse)response;
		String uri = r.getRequestURI();
		final String contextPath = r.getContextPath();
		uri = uri.substring(uri.indexOf(contextPath)+contextPath.length());
		if ( this.needSkipUserLoginCheck(r) ){
			chain.doFilter(request,response);
			return;
		}

		String sessionId = request.getParameter(SessionUtil.SESSION_ID);
		if(StringUtils.isEmpty(sessionId)) {
			sessionId = SessionUtil.getFromCookie(r,SessionUtil.SESSION_ID);
		}
		if (StringUtils.isNotEmpty(sessionId) ){
			chain.doFilter(request,response);
		}else{
		    //Ajax 分类：普通http请求的header参数中没有x-requested-with:XMLHttpRequest头信息，而异步的有
		    if(r.getHeader("x-requested-with") != null && r.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
		    	p.setContentType("application/json");
		        p.setHeader("sessionstimeout","timeout");
		        p.getWriter().print("{sessionState:0}");
		        p.getWriter().close();
		    }else{
    			p.setContentType("text/html");
    			String script = "<script type='text/javascript'>top.location.href='"+r.getContextPath()+"/src/login/login.jsp'</script>";
    			script = new String(script.getBytes("utf-8"),"ISO8859-1");
    			p.getWriter().print(script);
		    }
		}
	}

	private boolean needSkipUserLoginCheck(final HttpServletRequest r) {
		boolean result = false;
		final String uri = r.getRequestURI();
        if ( uri.equals(r.getContextPath()) || uri.equals(r.getContextPath()+"/")){
        	return true;
        }
		for (final String s : skips ) {
			if( uri.indexOf(s) >= 0 ){
				result = true;
				break;
			}
		}
		return result;
	}


	@Override
    public void destroy() {

	}
}
