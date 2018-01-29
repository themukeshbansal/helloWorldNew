package com.dailyhunt.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dailyhunt.model.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Long> {
	
	List<User> findByName(String name);
	List<User> findByNameOrCityOrState(
			String name,
			String city,
			String state
			);
	List<User> findByTagsContaining(
			String tagOne
			);
	List<User> findByTagsContainingAndTagsContaining(
			String tagOne,
			String tagTwo
			);
	List<User> findByTagsContainingAndTagsContainingAndTagsContaining(
			String tagOne,
			String tagTwo,
			String tagThree
			);
	List<User> findByTagsContainingAndTagsContainingAndTagsContainingAndTagsContaining(
			String tagOne,
			String tagTwo,
			String tagThree,
			String tagFour
			);
	List<User> findByTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContaining(
			String tagOne,
			String tagTwo,
			String tagThree,
			String tagFour,
			String tagFive
			);
	List<User> findByTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContaining(
			String tagOne,
			String tagTwo,
			String tagThree,
			String tagFour,
			String tagFive,
			String tagSix
			);
	List<User> findByTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContaining(
			String tagOne,
			String tagTwo,
			String tagThree,
			String tagFour,
			String tagFive,
			String tagSix,
			String tagSeven
			);
	List<User> findByTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContaining(
			String tagOne,
			String tagTwo,
			String tagThree,
			String tagFour,
			String tagFive,
			String tagSix,
			String tagSeven,
			String tagEight
			);
}
