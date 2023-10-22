package com.blog.application.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.blog.application.service.UserService;

@Controller
public class UserController {
	@Autowired
	PostRepository postRepository;
	@Autowired
	TagRepository tagRepository;
	@Autowired
	TagService tagService;
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostService postService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@RequestMapping("/update")
	public String UpdateBlog(@RequestParam("id") int id, Model model) {
		Post posts = postRepository.findById(id).get();
		List<Tag> tag = posts.getTags();
		String tagName = "";
		for (Tag ele : tag) {
			tagName += ele.getName() + ",";
		}
		model.addAttribute("tagname", tagName);
		model.addAttribute("data", posts);

		return "UpdateBlog";
	}

	@RequestMapping("/updatedata")
	public String UpdateBlogData(@RequestParam("id") String id, @RequestParam("title") String title,
			@RequestParam("excerpt") String excerpt, @RequestParam("content") String content,
			@RequestParam("tagfield") String tagfield, Model model) {
		System.out.print("hello");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User author = userRepository.getUserByUserName(username);

        Post updatepost=userService.updateBlogData(id, title, excerpt, content, tagfield, model);
		model.addAttribute("posts",updatepost); 
		model.addAttribute("author",author);

		return "redirect:/authorpage";

	}

	@RequestMapping("/authorpage")
	public String authodPage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User author = userRepository.getUserByUserName(username);
		List<Post> listOfPost=author.getPosts();
		model.addAttribute("posts", listOfPost);
		model.addAttribute("author",author);
		 

		return "AuthorPage";
	}

	@RequestMapping("/delete")
	public String Delete(@RequestParam("id") int id) {
		postRepository.deleteById(id);

		return "redirect:/authorpage";
	} 
	@GetMapping("/login")
	public String LoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User author = userRepository.getUserByUserName(username);
        if(author !=null) {
        	return "redirect:/home";
        }

		return "Login";
	}
	

	@PostMapping("/register")
	public String SignUpData(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password) {
		User check=userRepository.findByEmail(email);
		if(check !=null) {
			return "redirect:signup";
		}else {
			User userObject = new User();
			userObject.setName(name);
			userObject.setEmail(email);
			userObject.setPassword(bCryptPasswordEncoder.encode(password));
			
			userRepository.save(userObject);
			
			
			return "redirect:/login";
		}
		
		

	} 
	
	@GetMapping("/signup")
	public String SignUpPage() {

		return "SignUp";
	}
	


}
