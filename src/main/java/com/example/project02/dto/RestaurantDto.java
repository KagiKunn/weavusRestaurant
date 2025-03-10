package com.example.project02.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
@Data
public class RestaurantDto {
	private Long id;
	private String name;
	private String description;
	private String address;
	private MultipartFile img;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	//추천 수
	private int favorite;
	//누적 예약자
	private int reserved;
	private String telephone;
	private String userId;
}
