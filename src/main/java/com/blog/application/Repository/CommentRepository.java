package com.blog.application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.application.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
