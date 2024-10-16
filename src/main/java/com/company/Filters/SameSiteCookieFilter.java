package com.company.Filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class SameSiteCookieFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Initialization code, if needed
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);

		if (response instanceof HttpServletResponse) {
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			String setCookieHeader = httpServletResponse.getHeader("Set-Cookie");

			if (setCookieHeader != null && setCookieHeader.contains("JSESSIONID")) {
				setCookieHeader = setCookieHeader + "; SameSite=None; ";
				httpServletResponse.setHeader("Set-Cookie", setCookieHeader);
			}
		}
	}

	@Override
	public void destroy() {
		// Cleanup code, if needed
	}
}
