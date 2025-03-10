package com.example.project02.repository;

import com.example.project02.dto.ReservationDto;
import com.example.project02.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reservation,Long> {
	List<Reservation> findByUserId(String id);
}
