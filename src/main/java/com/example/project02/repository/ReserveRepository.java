package com.example.project02.repository;

import com.example.project02.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reservation,Long> {
	List<Reservation> findByUserId(String id);
	//JOIN FETCH는 데이터를 즉시 로딩할 때 사용
	@Query("SELECT r FROM Reservation r JOIN FETCH r.restaurant WHERE r.user.id = :userId ORDER BY r.time DESC")
	List<Reservation> findByUserIdWithRestaurant(@Param("userId") String userId);

	//집계 함수 (COUNT, SUM 등)을 사용할 때는 데이터를 가져오는 것이 아니라 계산만 하기 때문에 즉시 로딩이 필요 없다.
	@Query("SELECT COUNT(r) FROM Reservation r JOIN r.restaurant res WHERE res.id = :restaurantId AND r.status = 1")
	Long countByRestaurantIdAndStatus(@Param("restaurantId") Long rid);

	@Query("SELECT r FROM Reservation r JOIN FETCH r.restaurant WHERE r.restaurant.id = :id ORDER BY r.time DESC")
	List<Reservation> findByRestaurantId(@Param("id") Long id);
}
