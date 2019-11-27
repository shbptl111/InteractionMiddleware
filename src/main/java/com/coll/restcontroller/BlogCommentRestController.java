package com.coll.restcontroller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coll.dao.BlogCommentDAO;
import com.coll.model.BlogComment;
import com.coll.model.UserDetail;

@RestController
public class BlogCommentRestController {

	@Autowired
	BlogCommentDAO blogCommentDAO;
	
	@PostMapping("addComment")
	public ResponseEntity<List<BlogComment>> addComment(@RequestBody BlogComment comment, HttpSession session){
		comment.setCommentDate(new Date());
		
		UserDetail userDetail = (UserDetail) session.getAttribute("userDetail");
		comment.setLoginName(userDetail.getLoginName());
		
		List<BlogComment> blogCommentsList;
		
		if(blogCommentDAO.addComment(comment)) {
			blogCommentsList = blogCommentDAO.listBlogComments(comment.getBlogId());
			return new ResponseEntity<List<BlogComment>>(blogCommentsList, HttpStatus.OK);
		}
		else {
			blogCommentsList = null;
			return new ResponseEntity<List<BlogComment>>(blogCommentsList, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("deleteComment")
	public ResponseEntity<String> deleteComment(@RequestBody BlogComment comment){
		if(blogCommentDAO.deleteComment(comment)) {
			return new ResponseEntity<String>("Comment deleted successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Failed to delete comment", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("listBlogComments/{blogId}")
	public ResponseEntity<List<BlogComment>> listBlogComments(@PathVariable("blogId") int blogId){
		List<BlogComment> listBlogComments = blogCommentDAO.listBlogComments(blogId);
		
		if(listBlogComments != null) {
			return new ResponseEntity<List<BlogComment>>(listBlogComments, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<BlogComment>>(listBlogComments, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("getBlogComment/{blogCommentId}")
	public ResponseEntity<BlogComment> getBlogComment(@PathVariable("blogCommentId") int blogCommentId){
		BlogComment comment = blogCommentDAO.getBlogComment(blogCommentId);
		
		if(comment != null) {
			return new ResponseEntity<BlogComment>(comment, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<BlogComment>(comment, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
