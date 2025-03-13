package com.example.project02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String loginPage() {
		return "login"; // templates/login.html 반환
	}

	@GetMapping("/home")
	public String homePage() {
		return "home"; // 로그인 성공 후 이동할 페이지
	}
}
