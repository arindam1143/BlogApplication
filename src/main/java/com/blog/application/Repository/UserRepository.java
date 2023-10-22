package com.blog.application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blog.application.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	@Query ("select u from User u where u.email= :username")
	User getUserByUserName(String username);
	//@Query ("select u from User u where u.email= :email")
	User findByEmail(String email);

}
