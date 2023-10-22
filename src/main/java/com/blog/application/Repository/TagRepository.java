package com.blog.application.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.application.entity.Post;
import com.blog.application.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {
	@Query("SELECT T.posts FROM Tag T WHERE LOWER(T.name) LIKE CONCAT('%', LOWER(:searchText), '%')")
      public List<Post> searchByTag(@Param("searchText") String searchText);

   Tag findByName(String name);

}
