package com.example.project02.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private String address;
	private String img;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	//추천 수
	private int favorite;
	//누적 예약자
	private int reserved;
	private String telephone;
	//사장
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
