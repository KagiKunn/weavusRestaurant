package com.example.project02.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Object user = request.getSession().getAttribute("user");

		if (user == null) {
			response.sendRedirect("/signin"); // 로그인 페이지로 리디렉션
			return false; // 요청 중단
		}

		return true; // 로그인 되어 있으면 컨트롤러로 진행
	}
}
