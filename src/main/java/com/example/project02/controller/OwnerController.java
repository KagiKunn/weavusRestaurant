package com.example.project02.controller;

import com.example.project02.dto.RestaurantDto;
import com.example.project02.dto.everyone.RestaurantEveryDto;
import com.example.project02.service.OwnerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class OwnerController {

	private final OwnerService ownerService;

	@GetMapping("owner/restaurants")
	public String ownerRestaurants(HttpSession session, Model model) {
		System.out.println("restaurant controller called");
		List<RestaurantEveryDto> restaurant = ownerService.getOwnerRestaurant(session);
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("title","My Restaurants");

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

	@GetMapping("owner/restaurant/{id}")
	public String ownerRestaurant(@PathVariable Long id, Model model) {
		model.addAttribute("r", ownerService.getRestaurant(id));
		return "owner/restaurant";
	}
	@GetMapping("owner/edit/{id}")
	public String ownerRestaurantEdit(@PathVariable Long id, Model model) {
		model.addAttribute("r", ownerService.getRestaurant(id));
		return "owner/edit";
	}

	@PostMapping("owner/edit")
	public String restaurantEditProcess(@ModelAttribute RestaurantDto restaurantDto, HttpSession session
			,RedirectAttributes redirectAttributes) {
		ownerService.updateRestaurant(restaurantDto, session);
		redirectAttributes.addAttribute("id", restaurantDto.getId());
		return "redirect:/owner/restaurant/{id}";
	}

	@PostMapping("owner/deleterestaurant")
	public String restaurantDeleter(@ModelAttribute RestaurantDto restaurantDto, HttpSession session) {
		ownerService.deleteRestaurant(restaurantDto, session);
		return "redirect:/main";
	}
}
