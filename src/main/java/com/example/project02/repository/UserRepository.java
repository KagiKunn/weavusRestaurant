package com.example.project02.repository;

import com.example.project02.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

	boolean existsByIdAndPassword(String id, String password);

	Optional<User> findByIdAndPassword(String id, String password);
}
