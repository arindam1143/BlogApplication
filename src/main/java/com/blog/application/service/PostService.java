package com.blog.application.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.blog.application.Repository.PostRepository;
import com.blog.application.Repository.TagRepository;
import com.blog.application.Repository.UserRepository;
import com.blog.application.entity.Post;
import com.blog.application.entity.Tag;
import com.blog.application.entity.User;

@Component
public class PostService {
	boolean flag = true;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository postRepository;
	@Autowired
	TagRepository tagRepository;
	@Autowired
	TagService tagService;

	public Post createBlogPost(String title, String excerpt, String content, String tagfild, Model model) {
		Post postsObject = new Post();
		User obj = new User();
		obj.setName("avi");
		obj.setEmail("avi@gmail.com");
		obj.setPassword("123");
		userRepository.save(obj);
		postsObject.setAuthor(obj);
		Date date = new Date();
		postsObject.setTitle(title.trim());
		postsObject.setExcerpt(excerpt.trim());
		postsObject.setContent(content.trim());
		postsObject.setPublishedAt(date);
		postsObject.setCreated_at(date);
		postsObject.setUpdated_at(date);
		postsObject.setIs_published(flag);
		postsObject.setAuthor(obj);
		postRepository.save(postsObject);
		String[] tagName = tagfild.split(",");
		List<Tag> tags = tagRepository.findAll();
		List<Tag> postTags = new ArrayList<>();
		if (tags.size() == 0) {
			for (String name : tagName) {
				Tag newTagObj = new Tag();
				newTagObj.setCreated_at(new Date());
				newTagObj.setUpdated_at(new Date());
				newTagObj.setName(name);
				tagRepository.save(newTagObj);
				Tag tag = tagService.findByName(name);
				List<Post> tagpost = tag.getPosts();
				if (tagpost == null) {
					tagpost = new ArrayList<>();
				}
				tagpost.add(postsObject);
				postTags.add(tag);
				tagRepository.save(tag);
			}
		} else {
			for (String name : tagName) {
				for (Tag tag : tags) {
					if (tag.getName().equals(name)) {
						Tag myTag = tagRepository.findById(tag.getId()).get();
						myTag.setUpdated_at(new Date());
						tagRepository.save(myTag);
						Tag tagN = tagRepository.findById(tag.getId()).get();
						List<Post> tagposts = tagN.getPosts();
						if (tagposts == null) {
							tagposts = new ArrayList<>();
						}
						tagposts.add(postsObject);
						postTags.add(tagN);
						tagRepository.save(tagN);
					}
				}
				if (tagService.findByName(name) == null) {
					Tag newTag = new Tag();
					newTag.setCreated_at(new Date());
					newTag.setUpdated_at(new Date());
					newTag.setName(name);
					tagRepository.save(newTag);
					Tag tagN = tagService.findByName(name);
					List<Post> tagposts = tagN.getPosts();
					if (tagposts == null) {
						tagposts = new ArrayList<>();
					}
					tagposts.add(postsObject);
					postTags.add(tagN);
					tagRepository.save(tagN);
				}
			}
		}
		postsObject.setTags(postTags);
		postRepository.save(postsObject);
		return postsObject;
	}

}
