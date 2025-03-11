package com.example.project02.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
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
	private Long favorite;
	//누적 예약자
	private Long reserved;
	private String telephone;
	//사장
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE)  // 유저 삭제 시 레스토랑도 삭제됨
	private List<Reservation> reservations;

}
