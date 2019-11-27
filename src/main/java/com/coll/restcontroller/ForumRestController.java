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

import com.coll.dao.ForumDAO;
import com.coll.model.Forum;

@RestController
public class ForumRestController {

	@Autowired
	ForumDAO forumDAO;
	
	
	@PostMapping("addForum")
	public ResponseEntity<String> addForum(@RequestBody Forum forum){
		forum.setCreateDate(new Date());
		forum.setStatus("NA");
		
		if(forumDAO.addForum(forum)) {
			return new ResponseEntity<String>("Forum added successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Failed to add the forum", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("deleteForum")
	public ResponseEntity<String> deleteForum(@RequestBody Forum forum){
		if(forumDAO.deleteForum(forum)) {
			return new ResponseEntity<String>("Forum deleted successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Could not delete the forum", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("updateForum")
	public ResponseEntity<String> updateForum(@RequestBody Forum forum){
		if(forumDAO.updateForum(forum)) {
			return new ResponseEntity<String>("Forum updated successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Could not update the forum", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("getForum/{forumId}")
	public ResponseEntity<Forum> getForum(@PathVariable("forumId") int forumId){
		Forum forum = forumDAO.getForum(forumId);
		
		if(forum != null) {
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Forum>(forum, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("listForums")
	public ResponseEntity<List<Forum>> listForums(){
		List<Forum> listForums = forumDAO.listForums();
		
		if(listForums != null) {
			return new ResponseEntity<List<Forum>>(listForums, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Forum>>(listForums, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
