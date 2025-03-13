package com.example.project02.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Timestamp time;
	private int peopleNo;
	//0:승낙대기 1:예약성공 2:예약취소
	private int status;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserE user;
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
}
