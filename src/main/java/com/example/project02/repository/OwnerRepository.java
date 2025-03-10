package com.example.project02.repository;

import com.example.project02.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Restaurant,Long> {
	List<Restaurant> findByUserId(String id);

	Restaurant findByIdAndUserId(Long id, String id1);
}
