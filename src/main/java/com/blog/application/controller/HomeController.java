package com.blog.application.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	@GetMapping("/")
	 public String OpningHomepage(
			 @RequestParam(defaultValue = "0") int page, 
			 @RequestParam(defaultValue = "2") int size,
			Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User author = userRepository.getUserByUserName(username);
		Page<Post> blogPosts = homeService.getBlogPosts(page, size);
		model.addAttribute("homePageData", blogPosts);
		int pageLen=blogPosts.getSize();
		model.addAttribute("author",author);
		model.addAttribute("pageLen",pageLen);
		return "HomePage";
	}
	

	@RequestMapping("/home")
	 public String Homepage(
			 @RequestParam(defaultValue = "0") int page, 
			 @RequestParam(defaultValue = "2") int size,
			Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User author = userRepository.getUserByUserName(username);
		Page<Post> blogPosts = homeService.getBlogPosts(page, size);
		model.addAttribute("homePageData", blogPosts);
		int pageLen=blogPosts.getSize();
		model.addAttribute("author",author);
		model.addAttribute("pageLen",pageLen);
		return "HomePage";
	}

	 

	@RequestMapping("/latest")
	public String LatestBlog(
			@RequestParam(name = "order") String order,
			 @RequestParam(defaultValue = "0") int page, 
			 @RequestParam(defaultValue = "2") int size,Model model) {
		if (order.equals("asc")) {
			List<Post> list = postRepository.findAll(Sort.by(Sort.Order.desc("publishedAt")));
			model.addAttribute("homePageData", list);
			return "HomePage";
		} else {
			List<Post> list = postRepository.findAll(Sort.by(Sort.Order.asc("publishedAt")));
			model.addAttribute("homePageData", list);
			return "HomePage";

		}
//		List<Post> postslist = new ArrayList<>(posts);
//		int fromIndex = Math.min(page * size, posts.size());
//		int toIndex = Math.min(fromIndex + size, posts.size());
//		List<Post> content = postslist.subList(fromIndex, toIndex);
//       Page<Post> pageOfPosts = new PageImpl<>(content, PageRequest.of(page, size), posts.size());

	}

	@GetMapping("/search")
	public String search(
			@RequestParam("search") String searchText, 
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "2") int size,Model model) {
		Page<Post> posts = homeService.search(searchText, page, size);
		model.addAttribute("homePageData", posts);
		model.addAttribute("search",searchText);
		model.addAttribute("page",page);
		return "SearchResult";
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