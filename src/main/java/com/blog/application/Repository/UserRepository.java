package com.blog.application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.application.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
