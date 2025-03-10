package com.example.project02.service;

import com.example.project02.dto.ReservationDto;
import com.example.project02.dto.RestaurantDto;
import com.example.project02.dto.everyone.RestaurantEveryDto;
import com.example.project02.entity.Reservation;
import com.example.project02.entity.User;
import com.example.project02.repository.ReserveRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReserveService {

	private final ReserveRepository reserveRepository;


	public List<ReservationDto> getReserveListById(HttpSession session) {
		User user = (User)session.getAttribute("user");
		if(user == null) {
			return List.of();
		}
		return reserveRepository.findAll()
				.stream()
				.map(r -> ReservationDto.builder()
						.id(r.getId())
						.time(r.getTime())
						.peopleNo(r.getPeopleNo())
//						.restaurantId()
						.build()
						//여기부터 하면됨
				)
				.toList();
	}
	}
}
