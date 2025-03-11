package com.example.project02.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class User {
	@Id
	private String  id;
	private String password;
	private String username;
	private String phone;
	private int role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)  // 유저 삭제 시 레스토랑도 삭제됨
	private List<Restaurant> restaurants;
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)  // 유저 삭제 시 레스토랑도 삭제됨
	private List<Reservation> reservations;
}
