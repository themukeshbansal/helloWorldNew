package com.dailyhunt.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dailyhunt.model.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Long> {
	
	List<User> findByName(String name);

}
