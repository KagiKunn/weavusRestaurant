package com.example.project02.repository;

import com.example.project02.dto.ReservationDto;
import com.example.project02.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reservation,Long> {
	List<Reservation> findByUserId(String id);

	@Query("SELECT r FROM Reservation r JOIN FETCH r.restaurant WHERE r.user.id = :userId")
	List<Reservation> findByUserIdWithRestaurant(@Param("userId") String userId);
}
