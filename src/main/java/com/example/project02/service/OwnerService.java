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
import java.time.LocalDateTime;
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

		ownerRepository.findByUserid(user.getId());
	}
}
