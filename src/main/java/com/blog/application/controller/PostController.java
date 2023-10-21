package com.blog.application.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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

	@GetMapping("/login")
	public String LoginPage() {

		return "Login";
	}

	@PostMapping("/signupdata")
	public String SignUpData(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password) {
		User userObject = new User();
		userObject.setName(name);
		userObject.setEmail(email);
		userObject.setPassword(password);
		userRepository.save(userObject);
		System.out.println(userObject.getName() + " " + userObject.getEmail());
		return "redirect:/home";

	}

	@GetMapping("/signup")
	public String SignUpPage() {

		return "SignUp";
	}

	@PostMapping("/postblog")
	public String createPost(@RequestParam("title") String title, @RequestParam("excerpt") String excerpt,
			@RequestParam("content") String content, @RequestParam("tagfild") String tagfild, Model model) {
		Post postsObject = postService.createBlogPost(title, excerpt, content, tagfild, model);
		model.addAttribute("homePageData", postsObject);
		return "redirect:/home";
	}
}
