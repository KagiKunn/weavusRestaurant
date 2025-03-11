package com.example.project02.service;

import com.example.project02.dto.ReservationDto;
import com.example.project02.dto.everyone.UserEveryDto;
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
			LocalDate date = reservation.getTime().toLocalDateTime().toLocalDate();
			LocalTime time = reservation.getTime().toLocalDateTime().toLocalTime();

			ReservationDto reservationDto = ReservationDto.builder()
					.id(reservation.getId())
					.date(date)
					.time(time)
					.peopleNo(reservation.getPeopleNo())
					.status(reservation.getStatus())
					.userId(reservation.getUser())
					.restaurantId(reservation.getRestaurant())
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
				.user(user)
				.build();

		reserveRepository.save(reservation);
	}

	public void cancelReservation(Long id) {
		Reservation r = reserveRepository.findById(id).orElse(null);
		if (r == null) {
			return;
		}
		Reservation reservation = Reservation.builder()
				.id(r.getId())
				.status(2)
				.time(r.getTime())
				.peopleNo(r.getPeopleNo())
				.restaurant(r.getRestaurant())
				.user(r.getUser())
				.build();
		reserveRepository.save(reservation);
	}

	public List<ReservationDto> getReserveListByRestaurantId(Long id, HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<Reservation> reservations = reserveRepository.findByRestaurantId(id);
		List<ReservationDto> reservationDtos = new ArrayList<>();
		if (user == null || reservations.isEmpty()) {
			return List.of();
		}
		for (Reservation r : reservations) {
			LocalDate date = r.getTime().toLocalDateTime().toLocalDate();
			LocalTime time = r.getTime().toLocalDateTime().toLocalTime();
			User u = User.builder()
					.id(r.getUser().getId())
					.username(r.getUser().getUsername())
					.role(r.getUser().getRole())
					.phone(r.getUser().getPhone())
					.password("")
					.build();
			ReservationDto reservationDto = ReservationDto.builder()
					.id(r.getId())
					.date(date)
					.time(time)
					.peopleNo(r.getPeopleNo())
					.status(r.getStatus())
					.userId(u)
					.restaurantId(r.getRestaurant())
					.build();
			reservationDtos.add(reservationDto);
		}
		return reservationDtos;
	}

	public void confirmReservation(Long id) {
		Reservation r = reserveRepository.findById(id).orElse(null);
		if (r == null) {
			System.out.println("r is null");
			return;
		}
		Reservation reservation =Reservation.builder()
				.id(r.getId())
				.status(1)
				.time(r.getTime())
				.peopleNo(r.getPeopleNo())
				.restaurant(r.getRestaurant())
				.user(r.getUser())
				.build();
		reserveRepository.save(reservation);
	}

	public String cancelDestination(String pg, Long id, HttpSession session) {
		String text ="redirect:/reservelist/";
		User sessionUser = (User)session.getAttribute("user");
		System.out.println("here!!!!!!!!!!!!!!!!!!!!!!!!"+pg);
		System.out.println(id);
		System.out.println(sessionUser.getId());
		if(pg.equals("main")){
			text ="redirect:/reservelist/"+sessionUser.getId();
		}
		if(pg.equals("owner")){
			text ="redirect:/owner/reservelist/"+id;
		}
		System.out.println("!!text : "+text);
		return text;
	}

	public ReservationDto getReserve(Long id) {
		Reservation r = reserveRepository.findById(id).orElse(null);
		LocalDate date = r.getTime().toLocalDateTime().toLocalDate();
		LocalTime time = r.getTime().toLocalDateTime().toLocalTime();
		User u = User.builder()
				.id(r.getUser().getId())
				.username(r.getUser().getUsername())
				.role(r.getUser().getRole())
				.phone(r.getUser().getPhone())
				.password("")
				.build();
		return ReservationDto.builder()
						.id(r.getId())
						.date(date)
						.time(time)
						.peopleNo(r.getPeopleNo())
						.status(r.getStatus())
						.userId(u)
						.restaurantId(r.getRestaurant())
						.build();
	}

	public void favorite(Long rid) {
		Restaurant r = ownerRepository.findById(rid).orElse(null);
		if (r == null) {
			System.out.println("r is null");
			return;
		}
		Restaurant restaurant = r.toBuilder()
				.favorite(r.getFavorite()+1)
				.build();
		ownerRepository.save(restaurant);
	}

	public void deleteReservation(Long id) {
		reserveRepository.deleteById(id);
	}
}

