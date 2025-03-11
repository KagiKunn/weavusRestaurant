package com.example.project02.service;

import com.example.project02.dto.ReservationDto;
import com.example.project02.entity.Reservation;
import com.example.project02.entity.Restaurant;
import com.example.project02.entity.User;
import com.example.project02.repository.OwnerRepository;
import com.example.project02.repository.ReserveRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReserveService {

	private final ReserveRepository reserveRepository;
	private final OwnerService ownerService;
	private final OwnerRepository ownerRepository;


	public List<ReservationDto> getReserveListById(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return List.of();
		}
		List<Reservation> reservations = reserveRepository.findByUserIdWithRestaurant(user.getId());
		List<ReservationDto> reservationDtos = new ArrayList<>();
		for (Reservation reservation : reservations) {
			// Timestamp에서 LocalDate와 LocalTime 분리
			LocalDate date = reservation.getTime().toLocalDateTime().toLocalDate();
			LocalTime time = reservation.getTime().toLocalDateTime().toLocalTime();

			// DTO에 날짜와 시간 저장
			ReservationDto reservationDto = ReservationDto.builder()
					.id(reservation.getId())
					.date(date)
					.time(time)
					.peopleNo(reservation.getPeopleNo())
					.status(reservation.getStatus())
					.userId(reservation.getUser())  // 사용자 ID 저장
					.restaurantId(reservation.getRestaurant())  // 식당 ID 저장
					.build();

			reservationDtos.add(reservationDto);
		}
			return reservationDtos;
	}

	public void setReservation(ReservationDto reservationDto, HttpSession session) {

		Restaurant restaurant= ownerRepository.findById(reservationDto.getRestaurantId().getId()).orElse(null);

		User user = (User) session.getAttribute("user");
		LocalDateTime dateTime = LocalDateTime.of(reservationDto.getDate(), reservationDto.getTime());
		Reservation reservation = Reservation.builder()
				.time(Timestamp.valueOf(dateTime))
				.peopleNo(reservationDto.getPeopleNo())
				.status(0)
				.restaurant(restaurant)
				.user(user).build();
		reserveRepository.save(reservation);
	}
}

