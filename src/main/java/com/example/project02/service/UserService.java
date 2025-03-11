package com.example.project02.service;

import com.example.project02.dto.UserDto;
import com.example.project02.dto.everyone.RestaurantEveryDto;
import com.example.project02.entity.User;
import com.example.project02.repository.OwnerRepository;
import com.example.project02.repository.ReserveRepository;
import com.example.project02.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

	@Value("${file.upload-path}")
	private String imgPath;

	private final UserRepository userRepository;
	private final OwnerRepository ownerRepository;
	private final ReserveRepository	reserveRepository;

	@Transactional
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

	public boolean idCheck(String id) {return userRepository.existsById(id);
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
		return ownerRepository.findAll()
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
	}

	public void getUserInfo(HttpSession session) {

	}

	@Transactional
	public boolean updateUser(UserDto userDto, HttpSession session) {
		User sessionUser = (User) session.getAttribute("user");
		if(!userDto.getId().equals(sessionUser.getId())) {
			return false;
		}
		User user = User.builder()
				.id(sessionUser.getId())
				.password(userDto.getPassword())
				.username(userDto.getUsername())
				.phone(userDto.getPhone())
				.role(userDto.getRole())
				.build();
		session.setAttribute("user",userRepository.save(user));
		return true;
	}

	public void deleteUser(HttpSession session) {
		User sessionUser = (User) session.getAttribute("user");
		String userId = sessionUser.getId();
		userRepository.deleteById(userId);
		session.invalidate();
	}


	public boolean pwCheck(String id, String pw) {
		return userRepository.existsByIdAndPassword(id,pw);
	}
}
