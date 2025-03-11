package com.example.project02.controller;

import com.example.project02.dto.ReservationDto;
import com.example.project02.entity.User;
import com.example.project02.service.OwnerService;
import com.example.project02.service.ReserveService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReservationController {

	private final ReserveService reserveService;
	private final OwnerService ownerService;

	@GetMapping("/reservelist/{id}")
	public String reserveList(@PathVariable String id, Model model, HttpSession session) {
		List<ReservationDto> reserve = reserveService.getReserveListById(session);
		System.out.println(reserve);
		model.addAttribute("r", reserve);
		model.addAttribute("title", "My Reservation");
		return "main/reservelist";
	}

	@GetMapping("/restaurant/{id}")
	public String RestaurantDetail(@PathVariable Long id, Model model) {
		model.addAttribute("r",ownerService.getRestaurant(id));
		return "main/restaurant";
	}

	@GetMapping("/reservation/{id}")
	public String reserve(@PathVariable Long id, Model model) {
		model.addAttribute("r",ownerService.getRestaurant(id));
		return "main/reservation";
	}

	@PostMapping("/reservation")
	public String reservation(@ModelAttribute ReservationDto reservationDto, Model model, HttpSession session) {
		System.out.println("post called");
		System.out.println(reservationDto);
		reserveService.setReservation(reservationDto,session);
		User sessionUser = (User) session.getAttribute("user");
		return "redirect:/reservelist/"+sessionUser.getId();
	}
}
