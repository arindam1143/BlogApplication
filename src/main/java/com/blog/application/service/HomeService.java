package com.blog.application.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
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

	public Page<Post> searchBlog(String searchText,int page,int size) {
		String[] searchEle = searchText.split(" ");
		// Post post=new Post();
//		System.out.println(searchEle[0]+searchEle[1]);
//		List<Post> author=postRepository.findAll();
		List<User> user = userRepository.findAll();
		List<Tag> tag = tagRepository.findAll();

		Set<Post> posts = new HashSet<>();
		for (String ele : searchEle) {
//			for (User username : user) {
//				if (username.getName().toLowerCase().equals(ele.toLowerCase())) {
//					posts.addAll(username.getPosts());
//				}
//			}
//			for (Tag tagname : tag) {
//				if (tagname.getName().equals(ele)) {
//					posts.addAll(tagname.getPosts());
//				}
//			}
			List<Post> namePosts=userRepository.searchByName(ele);
			List<Post> tagPosts = tagRepository.searchByTag(ele);
			List<Post> titlePosts = postRepository.searchByTitle(ele);
			List<Post> contentPosts = postRepository.searchByContent(ele);
			posts.addAll(namePosts);
			posts.addAll(contentPosts);
			posts.addAll(tagPosts);
			posts.addAll(titlePosts);
		}
		List<Post> postslist = new ArrayList<>(posts);
		int fromIndex = Math.min(page * size, posts.size());
		int toIndex = Math.min(fromIndex + size, posts.size());
		List<Post> content = postslist.subList(fromIndex, toIndex);
       Page<Post> pageOfPosts = new PageImpl<>(content, PageRequest.of(page, size), posts.size());

		
		return pageOfPosts;

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
	public Page<Post> getBlogPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable);
    }


}
