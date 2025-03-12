package com.example.project02.controller.rest;

import com.example.project02.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserRestController {

	private final UserService userService;

	@PostMapping("/idcheck")
	public ResponseEntity<Boolean> checkId(@RequestBody Map<String, String> request) {
		System.out.println("check Controller called");
		return ResponseEntity.ok(userService.idCheck(request.get("id")));
	}

	@PostMapping("/pwcheck")
	public ResponseEntity<Boolean> checkPw(@RequestBody Map<String, String> request) {
		System.out.println("check Controller called");
		return ResponseEntity.ok(userService.pwCheck(request.get("id"),request.get("pw")));
	}
}
