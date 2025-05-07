package com.venky.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venky.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	List<User> findByDeletedFalse();

}
