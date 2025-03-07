package com.example.project02.repository;

import com.example.project02.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Restaurant,Long> {
	void findByUserid(String id);
}
