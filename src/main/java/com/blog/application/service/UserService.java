package com.blog.application.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blog.application.Repository.PostRepository;
import com.blog.application.Repository.TagRepository;
import com.blog.application.entity.Post;
import com.blog.application.entity.Tag;
import org.springframework.ui.Model;

@Component
public class UserService {
	@Autowired
	PostRepository postRepository;
	@Autowired
	TagRepository tagRepository;
	@Autowired
	TagService tagService;

	public Post updateBlogData(String id, String title, String excerpt, String content, String tagfield, Model model) {
		Post postsObject = postRepository.findById(Integer.parseInt(id)).get();
		postsObject.setTitle(title);
		postsObject.setExcerpt(excerpt);
		postsObject.setContent(content);
		postsObject.setUpdated_at(new Date());
		postRepository.save(postsObject);
		List<Tag> ListOftags = postsObject.getTags();
		String checkTagName = "";
		for (Tag ele : ListOftags) {
			checkTagName += ele.getName() + ",";
		}
		if (checkTagName.equals(tagfield)) {
			postRepository.save(postsObject);
		} else {
			String[] tagName = tagfield.split(",");
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

		}
		return postsObject;

	}
}
