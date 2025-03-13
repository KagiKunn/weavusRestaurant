package com.example.project02.service;

import com.example.project02.dto.ReservationDto;
import com.example.project02.dto.everyone.UserEveryDto;
import com.example.project02.entity.Reservation;
import com.example.project02.entity.Restaurant;
import com.example.project02.entity.UserE;
import com.example.project02.entity.UserE;
import com.example.project02.repository.OwnerRepository;
import com.example.project02.repository.ReserveRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
	private final OwnerRepository ownerRepository;
	private final UserService userService;


	public List<ReservationDto> getReserveListById() {
		UserE userE = userService.getUser(userService.getUserId());
		if (userE == null) {
			return List.of();
		}
		List<Reservation> reservations = reserveRepository.findByUserIdWithRestaurant(userE.getId());
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

	public void setReservation(ReservationDto reservationDto) {
		UserE user = userService.getUser(userService.getUserId());
		Restaurant restaurant= ownerRepository.findById(reservationDto.getRestaurantId().getId()).orElse(null);

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

	public List<ReservationDto> getReserveListByRestaurantId(Long id) {
		UserE userE = userService.getUser(userService.getUserId());
		List<Reservation> reservations = reserveRepository.findByRestaurantId(id);
		List<ReservationDto> reservationDtos = new ArrayList<>();
		if (userE == null || reservations.isEmpty()) {
			return List.of();
		}
		for (Reservation r : reservations) {
			LocalDate date = r.getTime().toLocalDateTime().toLocalDate();
			LocalTime time = r.getTime().toLocalDateTime().toLocalTime();
			UserE u = UserE.builder()
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
		long rid = r.getRestaurant().getId();
		Reservation reservation =Reservation.builder()
				.id(r.getId())
				.status(1)
				.time(r.getTime())
				.peopleNo(r.getPeopleNo())
				.restaurant(r.getRestaurant())
				.user(r.getUser())
				.build();
		reserveRepository.save(reservation);
		Restaurant res = ownerRepository.findById(rid).orElse(null);
		Restaurant cres = res.toBuilder()
				.reserved(res.getReserved()+1)
				.build();
		ownerRepository.save(cres);
	}

	public String cancelDestination(String pg, Long id) {
		String text ="redirect:/reservelist/";
		UserE userE = userService.getUser(userService.getUserId());
		if(pg.equals("main")){
			text ="redirect:/reservelist/"+ userE.getId();
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
		UserE u = UserE.builder()
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

