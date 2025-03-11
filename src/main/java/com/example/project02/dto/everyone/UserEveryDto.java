package com.example.project02.dto.everyone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserEveryDto {
	private String id;
	private String username;
	private String phone;
	//0일반, 1가게소유
	private int role;
}
