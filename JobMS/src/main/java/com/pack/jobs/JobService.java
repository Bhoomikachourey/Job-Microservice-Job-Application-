package com.pack.jobs;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pack.jobs.dto.JobDTO;

@Service
public interface JobService {

	List<JobDTO> findAll();
	
	void createJob(Job job);
	
	JobDTO getJobById(Long id);
	
	boolean deleteJobById(Long id);
	
	boolean updateJob(Long id, Job updateJob);
}
