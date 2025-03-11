package com.example.project02.controller;

import com.example.project02.dto.RestaurantDto;
import com.example.project02.dto.everyone.RestaurantEveryDto;
import com.example.project02.service.OwnerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@RequestMapping("owner/")
@RequiredArgsConstructor
@Controller
public class OwnerController {

	private final OwnerService ownerService;

	@GetMapping("restaurants")
	public String ownerRestaurants(HttpSession session, Model model) {
		System.out.println("restaurant controller called");
		List<RestaurantEveryDto> restaurant = ownerService.getOwnerRestaurant(session);
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("title","My Restaurants");

		return "owner/restaurants";
	}

	@GetMapping("registry")
	public String ownerRegistry() {
		System.out.println("registry get controller called");
		return "owner/registry";
	}

	@PostMapping("registry")
	public String ownerRegistryProcess(@ModelAttribute RestaurantDto restaurantDto , HttpSession session) {
		System.out.println("registry post controller called");
		ownerService.registry(restaurantDto, session);
		return "redirect:/owner/restaurants";
	}

	@GetMapping("restaurant/{id}")
	public String ownerRestaurant(@PathVariable Long id, Model model) {
		model.addAttribute("r", ownerService.getRestaurant(id));
		return "owner/restaurant";
	}
	@GetMapping("edit/{id}")
	public String ownerRestaurantEdit(@PathVariable Long id, Model model) {
		model.addAttribute("r", ownerService.getRestaurant(id));
		return "owner/edit";
	}

	@PostMapping("edit")
	public String restaurantEditProcess(@ModelAttribute RestaurantDto restaurantDto, HttpSession session
			,RedirectAttributes redirectAttributes) {
		ownerService.updateRestaurant(restaurantDto, session);
		redirectAttributes.addAttribute("id", restaurantDto.getId());
		return "redirect:/owner/restaurant/{id}";
	}

	@PostMapping("deleterestaurant")
	public String restaurantDeleter(@ModelAttribute RestaurantDto restaurantDto, HttpSession session) {
		ownerService.deleteRestaurant(restaurantDto, session);
		return "redirect:/main";
	}
}
