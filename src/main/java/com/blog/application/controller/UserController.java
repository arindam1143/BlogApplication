package com.blog.application.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.application.Repository.PostRepository;
import com.blog.application.Repository.TagRepository;
import com.blog.application.entity.Post;
import com.blog.application.entity.Tag;
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

		userService.updateBlogData(id, title, excerpt, content, tagfield, model);

		return "redirect:/authorpage";

	}

	@RequestMapping("/authorpage")
	public String authodPage(Model model) {
		List<Post> list = postRepository.findAll();
		model.addAttribute("homePageData", list);

		return "AuthorPage";
	}

	@RequestMapping("/delete")
	public String Delete(@RequestParam("id") int id) {
		postRepository.deleteById(id);

		return "redirect:/authorpage";
	}

}
