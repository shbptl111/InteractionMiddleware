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

import com.coll.dao.BlogDAO;
import com.coll.model.Blog;
import com.coll.model.UserDetail;

@RestController
public class BlogRestController {

	@Autowired
	BlogDAO blogDAO;
	
	@GetMapping("/showAllBlogs")
	public ResponseEntity<List<Blog>> showAllBlogs(){
		List<Blog> listBlogs = blogDAO.listBlogs();
		
		if(listBlogs.size()>0) {
			return new ResponseEntity<List<Blog>>(listBlogs, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Blog>>(listBlogs, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/addBlog")
	public ResponseEntity<List<Blog>> addBlog(@RequestBody Blog blog, HttpSession session){
		blog.setCreatedate(new Date());
		blog.setLikes(0);
		blog.setDislikes(0);
		blog.setStatus("NA");
		
		UserDetail userDetail = (UserDetail)session.getAttribute("userDetail");
		
		blog.setLoginname(userDetail.getLoginName());
		
		List<Blog> listBlogs;
		
		if(blogDAO.addBlog(blog)) {
			listBlogs = blogDAO.listBlogs();
			return new ResponseEntity<List<Blog>>(listBlogs, HttpStatus.OK);
		}
		else {
			listBlogs = null;
			return new ResponseEntity<List<Blog>>(listBlogs, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/deleteBlog/{blogId}")
	public ResponseEntity<String> deleteBlog(@PathVariable("blogId") int blogId){
		Blog blog = blogDAO.getBlog(blogId);
		if(blogDAO.deleteBlog(blog)) {
			return new ResponseEntity<String>("Blog deleted", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Could not delete the blog", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/updateBlog")
	public ResponseEntity<String> updateBlog(@RequestBody Blog blog){
		if(blogDAO.update(blog)) {
			return new ResponseEntity<String>("Blog updated successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Could not update the blog", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getBlog/{blogId}")
	public ResponseEntity<Blog> getBlog(@PathVariable("blogId") int blogId){
		Blog blog;
		if(blogDAO.getBlog(blogId) != null) {
			blog = blogDAO.getBlog(blogId);
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		else {
			blog = null;
			return new ResponseEntity<Blog>(blog, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/incrementLike/{blogId}")
	public ResponseEntity<Blog> incrementLikes(@PathVariable("blogId") int blogId){
		Blog blog;
		
		if(blogDAO.incrementLikes(blogId)) {
			blog = blogDAO.getBlog(blogId);
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		else {
			blog = blogDAO.getBlog(blogId);
			return new ResponseEntity<Blog>(blog, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/incrementDisLike/{blogId}")
	public ResponseEntity<Blog> incrementDisLikes(@PathVariable("blogId") int blogId){
		Blog blog;
		
		if(blogDAO.incrementDisLikes(blogId)) {
			blog = blogDAO.getBlog(blogId);
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		else {
			blog = blogDAO.getBlog(blogId);
			return new ResponseEntity<Blog>(blog, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/approveBlog/{blogId}")
	public ResponseEntity<List<Blog>> approveBlog(@PathVariable("blogId") int blogId){
		List<Blog> listBlogs = blogDAO.listBlogs();
		for(Blog blog : listBlogs) {
			System.out.println(blog.getBlogName() + "   " + blog.getStatus());
		}
		if(blogDAO.approveBlog(blogId)) {
			return new ResponseEntity<List<Blog>>(listBlogs, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Blog>>(listBlogs, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/rejectBlog/{blogId}")
	public ResponseEntity<List<Blog>> rejectBlog(@PathVariable("blogId") int blogId){
		List<Blog> listBlogs = blogDAO.listBlogs();
		for(Blog blog : listBlogs) {
			System.out.println(blog.getBlogName() + "   " + blog.getStatus());
		}
		if(blogDAO.rejectBlog(blogId)) {
			return new ResponseEntity<List<Blog>>(listBlogs, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Blog>>(listBlogs, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}