package com.example.project02.config;

import com.example.project02.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecurityUserService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public SecurityUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("🔍 loadUserByUsername 실행됨: " + username);

		return userRepository.findById(username)
				.map(user -> {
					List<GrantedAuthority> auth = new ArrayList<>();
					if (user.getRole() == 1) {
						auth.add(new SimpleGrantedAuthority("ROLE_OWNER"));
					} else if (user.getRole() == 2) {
						auth.add(new SimpleGrantedAuthority("ROLE_USER"));
					} else {
						auth.add(new SimpleGrantedAuthority("ROLE_GUEST"));
					}
					return new org.springframework.security.core.userdetails.User(
							user.getId(),
							user.getPassword(),
							auth);
				}).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
	}

}

