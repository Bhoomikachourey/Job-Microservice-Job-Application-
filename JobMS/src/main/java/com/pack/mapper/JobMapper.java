package com.pack.mapper;

import java.util.List;

import com.pack.jobs.Job;
import com.pack.jobs.dto.JobDTO;
import com.pack.jobs.external.Company;
import com.pack.jobs.external.Review;

public class JobMapper {
	
	public static JobDTO mapToJobWithCompanyDTO(Job job, Company company, List<Review> reviews) {
		JobDTO jobDTO = new JobDTO();
		jobDTO.setId(job.getId());
		jobDTO.setTitle(job.getTitle());
		jobDTO.setDescription(job.getDescription());
		jobDTO.setLocation(job.getLocation());
		jobDTO.setMinSalary(job.getMinSalary());
		jobDTO.setMaxSalary(job.getMaxSalary());
		
		jobDTO.setCompany(company);
		
		jobDTO.setReview(reviews);
		return jobDTO;
	}

}
