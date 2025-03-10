package com.example.project02.service;

import com.example.project02.dto.RestaurantDto;
import com.example.project02.dto.everyone.RestaurantEveryDto;
import com.example.project02.entity.Restaurant;
import com.example.project02.entity.User;
import com.example.project02.repository.OwnerRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OwnerService {

	@Value("${file.upload-path}")
	private String uploadPath;

	private final OwnerRepository ownerRepository;

	@Transactional
	public void registry(RestaurantDto restaurantDto, HttpSession session) {
		System.out.println("regi service called");
			UUID uuid = UUID.randomUUID();
			String imgName = uuid + "_" + restaurantDto.getImg().getOriginalFilename();
			Path path = Paths.get(uploadPath + imgName);
		try{
			Files.write(path, restaurantDto.getImg().getBytes());

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			Restaurant restaurant = Restaurant.builder()
					.name(restaurantDto.getName())
					.description(restaurantDto.getDescription())
					.telephone(restaurantDto.getTelephone())
					.address(restaurantDto.getAddress())
					.img(imgName)
					.createdAt(timestamp)
					.updatedAt(timestamp)
					.user((User)session.getAttribute("user"))
					.build();
			ownerRepository.save(restaurant);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public List<RestaurantEveryDto> getOwnerRestaurant(HttpSession session) {
		User user = (User)session.getAttribute("user");
		return ownerRepository.findByUserId(user.getId())
				.stream()
				.map(r -> RestaurantEveryDto.builder()
						.id(r.getId())
						.name(r.getName())
						.description(r.getDescription())
						.telephone(r.getTelephone())
						.address(r.getAddress())
						.img("/upload/" + r.getImg())
						.createdAt(r.getCreatedAt())
						.updatedAt(r.getUpdatedAt())
						.userName(r.getUser().getUsername())
						.userId(r.getUser().getId())
						.favorite(r.getFavorite())
						.reserved(r.getReserved())
						.build()
				)
				.toList();
	}

	public RestaurantEveryDto getRestaurant(Long id) {
		return ownerRepository.findById(id).map(r -> RestaurantEveryDto.builder()
				.id(r.getId())
								.name(r.getName())
								.description(r.getDescription())
								.telephone(r.getTelephone())
								.address(r.getAddress())
								.img("/upload/" + r.getImg())
								.createdAt(r.getCreatedAt())
								.updatedAt(r.getUpdatedAt())
								.userName(r.getUser().getUsername())
								.userId(r.getUser().getId())
								.favorite(r.getFavorite())
								.reserved(r.getReserved())
								.build()
				)
				.orElseThrow(() -> new RuntimeException("Restaurant not found"));
	}
	@Transactional
	public void updateRestaurant(RestaurantDto restaurantDto,HttpSession session) {
		User sessionUser = (User)session.getAttribute("user");
		Restaurant old = ownerRepository.findByIdAndUserId(restaurantDto.getId(),sessionUser.getId());

		UUID uuid = UUID.randomUUID();
		String imgName = uuid + "_" + restaurantDto.getImg().getOriginalFilename();
		Path path = Paths.get(uploadPath + imgName);

		try {
			Files.write(path, restaurantDto.getImg().getBytes());

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			Restaurant restaurant = Restaurant.builder()
					.id(restaurantDto.getId())
					.name(restaurantDto.getName() != null ? restaurantDto.getName() : old.getName())
					.description(restaurantDto.getDescription() != null ? restaurantDto.getDescription() : old.getDescription())
					.telephone(restaurantDto.getTelephone() != null ? restaurantDto.getTelephone() : old.getTelephone())
					.address(restaurantDto.getAddress() != null ? restaurantDto.getAddress() : old.getAddress())
					.img(restaurantDto.getImg() != null ? imgName : old.getImg())
					.createdAt(old.getCreatedAt())
					.updatedAt(timestamp)
					.user(old.getUser())
					.build();
			ownerRepository.save(restaurant);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void deleteRestaurant(RestaurantDto restaurantDto, HttpSession session) {
		User sessionUser = (User)session.getAttribute("user");
		System.out.println(sessionUser.getId());
		System.out.println(restaurantDto.getUserId());
		if(sessionUser.getId().equals(restaurantDto.getUserId())){
			ownerRepository.deleteById(restaurantDto.getId());
		}
	}
}
