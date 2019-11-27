package com.coll.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coll.dao.JobDAO;
import com.coll.model.Job;

@RestController
public class JobRestController {

	
	@Autowired
	JobDAO jobDetailDAO;
	
	@PostMapping("postJob")
	public ResponseEntity<String> postJob(@RequestBody Job job){
		if(jobDetailDAO.postJob(job)) {
			return new ResponseEntity<String>("Added job successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Could not add job", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("updateJob")
	public ResponseEntity<String> updateJob(@RequestBody Job job){
		if(jobDetailDAO.updateJob(job)) {
			return new ResponseEntity<String>("Updated job successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Could not update job", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("getJob/{jobId}")
	public ResponseEntity<Job> getJob(@PathVariable("jobId") int jobId){
		Job job = jobDetailDAO.getJob(jobId);
		
		if(job != null) {
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Job>(job, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("listJobDetails")
	public ResponseEntity<List<Job>> listJobDetails(){
		List<Job> listJobs = jobDetailDAO.listJobDetails();
		
		if(listJobs != null) {
			return new ResponseEntity<List<Job>>(listJobs, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Job>>(listJobs, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
