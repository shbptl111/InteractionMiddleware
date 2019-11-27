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

import com.coll.dao.ApplyingJobDAO;
import com.coll.model.ApplyingJob;

@RestController
public class ApplyingJobRestController {

	@Autowired
	ApplyingJobDAO applyingJobDAO;
	
	@PostMapping("addApplyingJob")
	public ResponseEntity<String> addApplyingJob(@RequestBody ApplyingJob jobApplication){
		jobApplication.setAppliedDate(new Date());
		
		if(applyingJobDAO.addApplyingJob(jobApplication)) {
			return new ResponseEntity<String>("Applied for job successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Failed to apply for job", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("updateApplyingJob")
	public ResponseEntity<String> updateApplyingJob(@RequestBody ApplyingJob jobApplication){
		//jobApplication.setAppliedDate(new Date());
		if(applyingJobDAO.updateApplyingJob(jobApplication)) {
			return new ResponseEntity<String>("Update the applied job", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Failed to update the job", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("deleteApplyingJob")
	public ResponseEntity<String> deleteApplyingJob(@RequestBody ApplyingJob jobApplication){
		if(applyingJobDAO.deleteApplyingJob(jobApplication)) {
			return new ResponseEntity<String>("Deleted the job application successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Could not delete the job application", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("getApplyingJob/{applyJobId}")
	public ResponseEntity<ApplyingJob> getApplyingJob(@PathVariable("applyJobId") int applyJobId){
		ApplyingJob jobApplication = applyingJobDAO.getApplyingJob(applyJobId);
		
		if(jobApplication != null) {
			return new ResponseEntity<ApplyingJob>(jobApplication, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ApplyingJob>(jobApplication, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("listAppliedJobs")
	public ResponseEntity<List<ApplyingJob>> listAppliedJobs(){
		List<ApplyingJob> appliedJobs = applyingJobDAO.listAppliedJobs();
		
		if(appliedJobs  != null) {
			return new ResponseEntity<List<ApplyingJob>>(appliedJobs, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<ApplyingJob>>(appliedJobs, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
