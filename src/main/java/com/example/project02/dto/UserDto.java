package com.example.project02.dto;

import lombok.Data;

@Data
public class UserDto {
	private String id;
	private String password;
	private String username;
	private String phone;
	//0일반, 1가게소유
	private int role;
}
