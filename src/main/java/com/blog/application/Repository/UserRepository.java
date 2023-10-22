package com.blog.application.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.application.entity.Post;
import com.blog.application.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	@Query ("select u from User u where u.email= :username")
	User getUserByUserName(String username);
	@Query("SELECT u.posts FROM User u WHERE LOWER(u.name) LIKE CONCAT('%', LOWER(:searchText), '%')")
    public List<Post> searchByName(@Param("searchText") String searchText);
	
	User findByEmail(String email);

}
