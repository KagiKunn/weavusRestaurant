package com.example.project02.service;

import com.example.project02.dto.UserDto;
import com.example.project02.dto.everyone.RestaurantEveryDto;
import com.example.project02.dto.everyone.UserEveryDto;
import com.example.project02.entity.UserE;
import com.example.project02.entity.UserE;
import com.example.project02.repository.OwnerRepository;
import com.example.project02.repository.ReserveRepository;
import com.example.project02.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	public void signUp(UserDto userDto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(userDto.getPassword());
		try {
			UserE userE = UserE.builder()
					.id(userDto.getId())
					.password(encodedPassword)
					.username(userDto.getUsername())
					.phone(userDto.getPhone())
					.role(userDto.getRole())
					.build();
			userRepository.save(userE);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public boolean idCheck(String id) {
		boolean check = userRepository.existsById(id);
		System.out.println("!!!!!!!!!!!!!!"+check);
		return check;
	}

	public boolean signIn(UserDto userDto, HttpSession session) {
		Optional<UserE> userOptional  = userRepository.findByIdAndPassword(userDto.getId(),userDto.getPassword());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(userOptional.isPresent()) {
//			if(encoder.matches(userOptional.getPassword())) {}
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
						.img(r.getImg())
						.createdAt(r.getCreatedAt())
						.updatedAt(r.getUpdatedAt())
						.userName(r.getUser().getUsername())
						.favorite(r.getFavorite())
						.reserved(r.getReserved())
						.build()
				)
				.toList();
	}

	public String getUserId() {
		return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	}

	public UserE getUser(String id){
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
	}

	@Transactional
	public boolean updateUser(UserDto userDto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		UserE user = getUser(getUserId());
		UserE userE = UserE.builder()
				.id(user.getId())
				.password(encoder.encode(userDto.getPassword()))
				.username(userDto.getUsername())
				.phone(userDto.getPhone())
				.role(userDto.getRole())
				.build();
		UserE u = userRepository.save(userE);
		updateSecurityContext(u);
		return true;
	}

	private void updateSecurityContext(UserE user) {
		String role = "ROLE_USER";
		if(user.getRole()==1){
			role="ROLE_OWNER";
		}
		UserDetails newUserDetails = new User(
				user.getId(),
				user.getPassword(),  // 새 비밀번호 반영
				List.of(new SimpleGrantedAuthority(role)) // 권한 업데이트
		);

		UsernamePasswordAuthenticationToken newAuth =
				new UsernamePasswordAuthenticationToken(newUserDetails, newUserDetails.getPassword(), newUserDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}

	public void deleteUser(HttpSession session) {
		UserE sessionUserE = (UserE) session.getAttribute("user");
		String userId = sessionUserE.getId();
		userRepository.deleteById(userId);
		session.invalidate();
	}


	public boolean pwCheck(String pw) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		UserE user = getUser(getUserId());
		if(encoder.matches(pw, user.getPassword())){
			return true;
		}
		else{
			return false;
		}
	}

	public UserEveryDto getUserInfo() {
		UserE u = getUser(getUserId());
		return UserEveryDto.builder()
				.id(u.getId())
				.username(u.getUsername())
				.phone(u.getPhone())
				.role(u.getRole())
				.build();
	}
}
