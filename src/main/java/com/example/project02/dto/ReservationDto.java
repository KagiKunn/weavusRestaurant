package com.example.project02.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReservationDto {
	private String id;
	private Timestamp time;
	private int peopleNo;
	//0:승낙대기 1:예약성공 2:예약취소
	private int status;
	private UserDto userId;
	private RestaurantDto restaurantId;
}
