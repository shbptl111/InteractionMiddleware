package com.coll.restcontroller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coll.dao.ForumCommentDAO;
import com.coll.model.ForumComment;

@RestController
public class ForumCommentRestController {

	@Autowired
	ForumCommentDAO forumCommentDAO;
	
	@PostMapping("addForumComment")
	public ResponseEntity<String> addForumComment(@RequestBody ForumComment comment){
		comment.setCommentDate(new Date());
		if(forumCommentDAO.addForumComment(comment)) {
			return new ResponseEntity<String>("Forum comment added successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Failed to add comment", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("getForumComment/{commentId}")
	public ResponseEntity<ForumComment> getForumComment(@PathVariable("commentId") int commentId){
		ForumComment comment = forumCommentDAO.getForumComment(commentId);
		if(comment != null) {
			return new ResponseEntity<ForumComment>(comment, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ForumComment>(comment, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("deleteForumComment")
	public ResponseEntity<String> deleteForumComment(@RequestBody ForumComment comment){
		if(forumCommentDAO.deleteForumComment(comment)) {
			return new ResponseEntity<String>("Forum comment deleted successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Failed to delete comment", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("listComments")
	public ResponseEntity<List<ForumComment>> listComments(){
		List<ForumComment> listComments = forumCommentDAO.listComments();
		if(listComments != null) {
			return new ResponseEntity<List<ForumComment>>(listComments, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<ForumComment>>(listComments, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
