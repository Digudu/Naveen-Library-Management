package com.venky.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venky.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
