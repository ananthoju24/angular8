package com.vts.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
//@Order(1)
public class VtsFilter implements Filter {

	private static final Logger logger = LogManager.getLogger();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		String uri = httpRequest.getRequestURI();
		logger.info("doFilter :: validating session : URI " + uri);
		httpRequest.setCharacterEncoding("UTF-8");

		if (!(uri.contains("h2-console"))) {

			String userName = null;
			try {
				userName = (String) session.getAttribute("username");
			} catch (Exception e) {
				logger.error("doFilter :: username is not available in session ", e.getMessage());
				userName = null;
			}

			if ((null == session && (!(uri.contains("login"))))
					|| (null != session && (null == userName || "".equals(userName)) && !(uri.contains("login")))) {
				logger.error("doFilter :: redirecting to login page as session is null or invalid");
				httpResponse.sendRedirect("/login");
			} else {
				logger.info("doFilter :: username in session " + userName + " sending request to controller ");
				chain.doFilter(request, response);
			}
		}
	}

}
