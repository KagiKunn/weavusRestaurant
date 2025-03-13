package com.example.project02.controller;

import com.example.project02.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MainController {

	private final UserService userService;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/main")
	public String Main(Model model, HttpServletRequest request) {
		model.addAttribute("restaurant",userService.getAllRestaurant());
		model.addAttribute("title","Title");
		CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		if (csrfToken != null) {
			System.out.println("CSRF Token: " + csrfToken.getToken());
		}
			return "main/main";
	}

	@GetMapping("/signup")
	public String SignUp() {
		userService.getUserInfo();
		return "main/signup";
	}

	@GetMapping("/signin")
	public String SignIn() {
		return "main/signin";
	}

//	@PostMapping("/logout")
//	public String logout(HttpSession session) {
//		return "redirect:/main";
//	}
}
