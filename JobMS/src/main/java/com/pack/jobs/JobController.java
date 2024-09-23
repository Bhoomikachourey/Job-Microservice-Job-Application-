package com.pack.jobs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pack.jobs.dto.JobDTO;

 
@RestController
@RequestMapping("/jobs")
public class JobController {

	@Autowired
	JobService jobService;
	private List<Job> jobs = new ArrayList<>();
	
	@GetMapping
	public ResponseEntity<List<JobDTO>> findAll(){
		return ResponseEntity.ok(jobService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
		JobDTO jobWithCompanyDTO = jobService.getJobById(id);
		if(jobWithCompanyDTO != null) {
			return new ResponseEntity<>(jobWithCompanyDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
	
	@PostMapping
	public ResponseEntity<String> createJob(@RequestBody Job job){
		jobService.createJob(job);
		return new ResponseEntity<>("Job added successfully", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteJob(@PathVariable Long id){
		boolean deleted = jobService.deleteJobById(id);
		if(deleted) {
			return new ResponseEntity<String>("job deleted successfully", HttpStatus.OK);
			
		}
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job updatedJob){
		boolean updated = jobService.updateJob(id, updatedJob);
		if(updated) {
			return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
