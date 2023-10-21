package com.blog.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.application.Repository.TagRepository;
import com.blog.application.entity.Tag;
@Service
public class TagService {
@Autowired
TagRepository tagRepository;

public Tag findByName(String name) {
	 return tagRepository.findByName(name);
}


}
