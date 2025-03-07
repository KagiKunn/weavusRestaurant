package com.example.project02.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

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
}
