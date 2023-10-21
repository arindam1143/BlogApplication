package com.blog.application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.application.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

   Tag findByName(String name);

}
