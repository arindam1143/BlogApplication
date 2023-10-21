package com.blog.application.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.blog.application.Repository.PostRepository;
import com.blog.application.Repository.TagRepository;
import com.blog.application.Repository.UserRepository;
import com.blog.application.entity.Post;
import com.blog.application.entity.Tag;
import com.blog.application.entity.User;
import com.blog.application.service.HomeService;
import org.springframework.ui.Model;

@Controller
public class HomeController {
	@Autowired
	PostRepository postRepository;

	@Autowired
	HomeService homeService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TagRepository tagRepository;

	@RequestMapping("/home")
	public String Homepage(Model model) {
		List<Post> list = postRepository.findAll();
		model.addAttribute("homePageData", list);
		return "HomePage";
	}

	@RequestMapping("/latest")
	public String LatestBlog(@RequestParam(name = "order") String order, Model model) {
		if (order.equals("asc")) {
			List<Post> list = postRepository.findAll(Sort.by(Sort.Order.desc("publishedAt")));
			model.addAttribute("homePageData", list);
			return "HomePage";
		} else {
			List<Post> list = postRepository.findAll(Sort.by(Sort.Order.asc("publishedAt")));
			model.addAttribute("homePageData", list);
			return "HomePage";

		}

	}

	@GetMapping("/search")
	public String search(@RequestParam("search") String searchText, Model model) {
		Set<Post> posts = homeService.search(searchText);
		model.addAttribute("homePageData", posts);
		return "HomePage";
	}

	@RequestMapping("/fielter")
	public String Fielter(Model model) {
		List<User> username = userRepository.findAll();
		model.addAttribute("username", username);
		List<Post> publishedDate = postRepository.findAll();
		model.addAttribute("publishedDate", publishedDate);
		List<Tag> tagname = tagRepository.findAll();
		model.addAttribute("tagname", tagname);

		return "FielterPage";
	}

	@RequestMapping("/filterblog")
	public String FilterBlog(@RequestParam(value = "authorCheckbox", required = false) String[] Authors,
			@RequestParam(value = "dateCheckbox", required = false) String[] Dates,
			@RequestParam(value = "tagCheckbox", required = false) String[] Tags, Model model) {

		List<Post> postList = homeService.FilterBlog(Authors, Dates, Tags);
		Set<Post> postSet = new HashSet<>(postList);
		model.addAttribute("homePageData", postSet);
		return "HomePage";
	}

}