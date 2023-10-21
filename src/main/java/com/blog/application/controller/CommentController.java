package com.blog.application.controller;

import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.application.entity.Post;
import com.blog.application.Repository.CommentRepository;
import com.blog.application.Repository.PostRepository;
import com.blog.application.entity.Comment;

@Controller
public class CommentController {
	@Autowired
	PostRepository postRepository;

	@Autowired
	CommentRepository commentRepository;

	@RequestMapping("/readmore")
	public String reamMore(@RequestParam("id") int id, Model model) {
		Post posts = postRepository.findById(id).get();
		// System.out.print(posts.getContent());
		List<Comment> commentList = posts.getComments();
		model.addAttribute("comment", commentList);
		model.addAttribute("posts", posts);

		return "ReadMore";
	}

	@GetMapping("/comment")
	public String Comment(@RequestParam("postId") int id, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("comment") String comment) {
		Comment commentobj = new Comment();
		Date date = new Date();
		commentobj.setName(name);
		commentobj.setEmail(email);
		commentobj.setComment(comment);
		commentobj.setCreated_at(date);
		commentobj.setUpdated_at(date);
		Post posts = postRepository.findById(id).get();
		commentobj.setPost(posts);
		commentRepository.save(commentobj);

		return "redirect:/readmore?id=" + id;
	}

	@GetMapping("/showcomment")
	public String ShowComment(@RequestParam("id") int id, Model model) {
		Post post = postRepository.findById(id).get();
		List<Comment> commentList = post.getComments();
		model.addAttribute("comment", commentList);

		return "ShowComment";
	}

	@RequestMapping("/updatecomment")
	public String UpdateComment(@RequestParam("id") int id, Model model) {
		Comment comment = commentRepository.findById(id).get();
		model.addAttribute("comment", comment);
		return "UpdateComment";
		// return "redirect:/UpdateComment?id="+id;
	}

	@PostMapping("/submitcommentupdate")
	public String SubmitCommentUpdate(@RequestParam("postId") int postid, @RequestParam("commentId") int id,
			@RequestParam("commentfield") String comment, Model model) {
		Comment comments = commentRepository.findById(id).get();
		comments.setComment(comment.trim());
		comments.setUpdated_at(new Date());
		commentRepository.save(comments);

		return "redirect:/readmore?id=" + postid;
	}

	@RequestMapping("deletecomment")
	public String DeleteComment(@RequestParam("id") int id) {
		Comment comments = commentRepository.findById(id).get();
		Post post = comments.getPost();
		int postid = post.getId();
		commentRepository.deleteById(id);
		return "redirect:/readmore?id=" + postid;
	}

}
