package com.example.project02.repository;

import com.example.project02.entity.UserE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserE, String> {

	Optional<UserE> findByIdAndPassword(String id, String password);

	boolean existsByIdAndPassword(String id, String password);

}
