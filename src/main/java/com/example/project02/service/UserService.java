package com.example.project02.service;

import com.example.project02.dto.UserDto;
import com.example.project02.dto.everyone.RestaurantEveryDto;
import com.example.project02.entity.Restaurant;
import com.example.project02.entity.User;
import com.example.project02.repository.OwnerRepository;
import com.example.project02.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

	@Value("${file.upload-path}")
	private String imgPath;

	private final UserRepository userRepository;
	private final OwnerRepository ownerRepository;

	public void signUp(UserDto userDto, HttpSession session) {
		try {
			User user = User.builder()
					.id(userDto.getId())
					.password(userDto.getPassword())
					.username(userDto.getUsername())
					.phone(userDto.getPhone())
					.role(userDto.getRole())
					.build();
			userRepository.save(user);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public boolean idCheck(String id) {
		return userRepository.existsById(id);
	}

	public boolean signIn(UserDto userDto, HttpSession session) {
		Optional<User> userOptional  = userRepository.findByIdAndPassword(userDto.getId(),userDto.getPassword());
		if(userOptional.isPresent()) {
			session.setAttribute("user", userOptional.get());
			return true;
		}
		return false;
	}

	public List<RestaurantEveryDto> getAllRestaurant() {
		List<RestaurantEveryDto> restaurantDtos = ownerRepository.findAll()
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
						.favorite(r.getFavorite())
						.reserved(r.getReserved())
						.build()
				)
				.toList();
		return restaurantDtos;
	}
}
