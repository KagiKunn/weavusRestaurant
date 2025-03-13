package com.example.project02.controller;

import com.example.project02.dto.UserDto;
import com.example.project02.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public String signUp(@ModelAttribute UserDto userDto, Model model) {
		userService.signUp(userDto);
		return "main/signin";
	}

//	@PostMapping("/signin")
//	public String signIn(@ModelAttribute UserDto userDto, HttpSession session) {
//		return userService.signIn(userDto,session)? "redirect:main":"main/signin";
//	}

	@GetMapping("/mypage")
	public String mypage(Model model) {
		model.addAttribute("title","MyPage");
		model.addAttribute("user",userService.getUserInfo());
		return "main/mypage";
	}

	@PostMapping("/changeuserinfo")
	public String changeUserInfo(@ModelAttribute UserDto userDto) {
		userService.updateUser(userDto);
		return "redirect:/mypage";
	}

	@PostMapping("deleteuser")
	public String deleteUser(HttpSession session) {
		userService.deleteUser(session);
		return "redirect:/main";
	}

}
