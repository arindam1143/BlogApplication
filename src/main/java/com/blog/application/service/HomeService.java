package com.blog.application.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blog.application.Repository.PostRepository;
import com.blog.application.Repository.TagRepository;
import com.blog.application.Repository.UserRepository;
import com.blog.application.entity.Post;
import com.blog.application.entity.Tag;
import com.blog.application.entity.User;

@Component
public class HomeService {
	@Autowired
	PostRepository postRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TagRepository tagRepository;

	public Set<Post> search(String searchText) {
		String[] searchEle = searchText.split(" ");
		// Post post=new Post();
//		System.out.println(searchEle[0]+searchEle[1]);
//		List<Post> author=postRepository.findAll();
		List<User> user = userRepository.findAll();
		List<Tag> tag = tagRepository.findAll();

		Set<Post> posts = new HashSet<>();
		for (String ele : searchEle) {
			for (User username : user) {
				if (username.getName().equals(ele)) {
					posts.addAll(username.getPosts());
				}
			}
			for (Tag tagname : tag) {
				if (tagname.getName().equals(ele)) {
					posts.addAll(tagname.getPosts());
				}
			}

			List<Post> titlePosts = postRepository.searchByTitle(ele);
			List<Post> contentPosts = postRepository.searchByContent(ele);
			posts.addAll(contentPosts);
			posts.addAll(titlePosts);
		}

		return posts;

	}

	public List<Post> FilterBlog(String[] Authors, String[] Dates, String[] Tags) {
		List<Post> filteredBlogs = new ArrayList<>();
		List<Post> allBlogs = postRepository.findAll();
		boolean tagMatch = false;
		for (Post blog : allBlogs) {
			boolean authorMatch = Authors == null || Authors.length == 0
					|| Arrays.asList(Authors).contains(blog.getAuthor().getName());
			boolean dateMatch = Dates == null || Dates.length == 0
					|| Arrays.asList(Dates).contains(blog.getPublishedAt().toString());
			List<Tag> tags = blog.getTags();
			for (Tag tagsfiend : tags) {
				tagMatch = Tags == null || Tags.length == 0 || Arrays.asList(Tags).contains(tagsfiend.getName());
//				System.out.println(tagsfiend.getName());
				if (authorMatch && dateMatch && tagMatch) {
					// System.out.print("hello ");
					filteredBlogs.add(blog);
				}
			}

		}

		return filteredBlogs;

	}

}
