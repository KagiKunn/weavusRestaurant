package com.example.project02.controller;

import com.example.project02.dto.RestaurantDto;
import com.example.project02.service.OwnerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class OwnerController {

	private final OwnerService ownerService;

	@GetMapping("owner/restaurants")
	public String ownerRestaurants(HttpSession session, Model model) {
		System.out.println("restaurant controller called");
		ownerService.getOwnerRestaurant(session);
		return "owner/restaurants";
	}

	@GetMapping("owner/registry")
	public String ownerRegistry() {
		System.out.println("registry get controller called");
		return "owner/registry";
	}

	@PostMapping("owner/registry")
	public String ownerRegistryProcess(@ModelAttribute RestaurantDto restaurantDto , HttpSession session) {
		System.out.println("registry post controller called");
		ownerService.registry(restaurantDto, session);
		return "redirect:/owner/restaurants";
	}
}
