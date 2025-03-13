package com.example.project02.controller;

import com.example.project02.dto.ReservationDto;
import com.example.project02.entity.UserE;
import com.example.project02.entity.UserE;
import com.example.project02.service.OwnerService;
import com.example.project02.service.ReserveService;
import com.example.project02.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReservationController {

	private final ReserveService reserveService;
	private final OwnerService ownerService;
	private final UserService userService;

	@GetMapping("/reservelist/{id}")
	public String reserveList(@PathVariable String id, Model model, HttpSession session) {
		List<ReservationDto> reserve = reserveService.getReserveListById();
		model.addAttribute("r", reserve);
		model.addAttribute("title", "My Reservation");
		model.addAttribute("nowDateTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		return "main/reservelist";
	}

	@GetMapping("/restaurant/{id}")
	public String RestaurantDetail(@PathVariable Long id, Model model) {
		model.addAttribute("r",ownerService.getRestaurant(id));
		model.addAttribute("title","Detail Restaurant");
		return "main/restaurant";
	}

	@GetMapping("/reservation/{id}")
	public String reserve(@PathVariable Long id, Model model) {
		model.addAttribute("r",ownerService.getRestaurant(id));
		model.addAttribute("title","Reservation");
		return "main/reservation";
	}

	@PostMapping("/reservation")
	public String reservation(@ModelAttribute ReservationDto reservationDto, Model model) {
		System.out.println("post called");
		System.out.println(reservationDto);
		reserveService.setReservation(reservationDto);

		return "redirect:/reservelist/"+ ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	}

	@GetMapping("/reservate/{id}")
	public String reservate(@PathVariable Long id, Model model) {
		model.addAttribute("r",reserveService.getReserve(id));
		model.addAttribute("title","Reservation");
		return "main/reservate";
	}

	@PostMapping("/cancelReservation")
	public String cancelReservation(@RequestParam Long id) {
		reserveService.cancelReservation(id);
		return "redirect:/reservelist/"+ userService.getUserId();
	}
	@PostMapping("/owner/cancelReservation")
	public String ownerCancelReservation(@RequestParam Long id, @RequestParam Long rid) {
		reserveService.cancelReservation(id);
		return "redirect:/owner/reservelist/"+rid;
	}

	@GetMapping("/owner/reservelist/{id}")
	public String ownerReservation(@PathVariable Long id, Model model) {
		System.out.println("owner reserveList Page!!!");
		model.addAttribute("r", reserveService.getReserveListByRestaurantId(id));
		model.addAttribute("title", "Manage Reservation");
		return "/owner/reservelist";
	}

	@PostMapping("/confirmreservation")
	public String confirmReservation(@RequestParam Long id,@RequestParam Long rid, HttpSession session) {
		System.out.println(id);
		System.out.println(rid);
		reserveService.confirmReservation(id);
		return "redirect:/owner/reservelist/"+rid;
	}
	@PostMapping("/favorite")
	public String favorite(@RequestParam Long id, @RequestParam Long rid) {
		reserveService.favorite(rid);
		reserveService.deleteReservation(id);
		return "redirect:/reservelist/"+ userService.getUserId();
	}
}
