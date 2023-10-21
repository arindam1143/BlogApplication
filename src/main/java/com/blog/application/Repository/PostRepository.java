package com.blog.application.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.application.entity.Post;


public interface PostRepository  extends JpaRepository<Post, Integer>{
	@Query("SELECT p FROM Post p WHERE p.title LIKE CONCAT('%', :searchText, '%')")
	public List<Post> searchByTitle(@Param("searchText") String searchText);
	@Query("SELECT p FROM Post p WHERE p.content LIKE CONCAT('%', :searchText, '%')")
	public List<Post> searchByContent(@Param("searchText") String searchText);

}
