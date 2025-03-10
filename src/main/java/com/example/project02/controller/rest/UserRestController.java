package com.example.project02.controller.rest;

import com.example.project02.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserRestController {

	private final UserService userService;

	@PostMapping("idcheck")
	public boolean checkId(@RequestBody Map<String, String> request) {
		System.out.println("check Controller called");
		return userService.idCheck(request.get("id"));
	}

	@PostMapping("pwcheck")
	public boolean checkPw(@RequestBody Map<String, String> request) {
		System.out.println("check Controller called");
		return userService.pwCheck(request.get("id"),request.get("pw"));
	}
}
