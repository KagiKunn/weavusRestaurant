package com.example.project02.dto.everyone;

import com.example.project02.entity.Restaurant;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Builder
@Data
public class RestaurantEveryDto {
	private Long id;
	private String name;
	private String description;
	private String address;
	private String img;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	//추천 수
	private Long favorite;
	//누적 예약자
	private Long reserved;
	private String telephone;
	private String userName;
	private String userId;


}
