package com.example.project02.dto;

import com.example.project02.entity.Restaurant;
import com.example.project02.entity.UserE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReservationDto {
	private Long id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime time;
	private int peopleNo;
	//0:승낙대기 1:예약성공 2:예약취소
	private int status;
	private UserE userId;
	private Restaurant restaurantId;
}
