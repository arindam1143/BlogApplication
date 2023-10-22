package com.blog.application.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.application.Repository.PostRepository;
import com.blog.application.Repository.TagRepository;
import com.blog.application.Repository.UserRepository;
import com.blog.application.entity.Post;
import com.blog.application.entity.Tag;
import com.blog.application.entity.User;
import com.blog.application.service.PostService;
import com.blog.application.service.TagService;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestParam;

@Controller

//@RequestMapping("/home")
public class PostController {
	boolean flag = true;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository postRepository;
	@Autowired
	TagRepository tagRepository;
	@Autowired
	TagService tagService;
	@Autowired
	PostService postService;
	

	@GetMapping("/post")
	public String Write() {
		return "Post";
	}

	
	@PostMapping("/postblog")
	public String createPost(@RequestParam("title") String title, @RequestParam("excerpt") String excerpt,
			@RequestParam("content") String content, @RequestParam("tagfild") String tagfild, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User author = userRepository.getUserByUserName(username);
		
		Post postsObject = postService.createBlogPost(title, excerpt, content, tagfild, model,author);
		model.addAttribute("homePageData", postsObject);
		return "redirect:/home";
	}
}
