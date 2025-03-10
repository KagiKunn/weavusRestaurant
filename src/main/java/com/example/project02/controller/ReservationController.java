package com.example.project02.controller;

import com.example.project02.service.ReserveService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ReservationController {

	private final ReserveService reserveService;

	@GetMapping("/reservelist/{id}")
	public String reserveList(@PathVariable String id, Model model, HttpSession session) {
		model.addAttribute("restaurant",reserveService.getReserveListById(session));
		return "main/reservelist";
	}
}
