package com.pack.jobs.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pack.jobs.Job;
import com.pack.jobs.JobRepository;
import com.pack.jobs.JobService;
import com.pack.jobs.clients.CompanyClient;
import com.pack.jobs.clients.ReviewClient;
import com.pack.jobs.dto.JobDTO;
import com.pack.jobs.external.Company;
import com.pack.jobs.external.Review;
import com.pack.mapper.JobMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class JobServiceImpl implements JobService{

	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private CompanyClient companyClient;
	
	@Autowired
	private ReviewClient reviewClient;
	private Long nextId = 1L;
	int attempt = 0;
	
	public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
		this.jobRepository = jobRepository;
		this.companyClient = companyClient;
		this.reviewClient = reviewClient;
	}

	
	@Override
//	@CircuitBreaker(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
//	@Retry(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
	@RateLimiter(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
	public List<JobDTO> findAll() {
		System.out.println("Attempts: "+attempt);
		List<Job> jobs = jobRepository.findAll();
		List<JobDTO> jobDTOs = new ArrayList<>();
		
		
		return jobs.stream().map(this::convertToDto)
				.collect(Collectors.toList());
	}
	
	public List<String> companyBreakerFallback(Exception e){
		List<String> list = new ArrayList<>();
		list.add("Dummy");
		return list;
	}
	
	private JobDTO convertToDto(Job job) {
		
			Company company = companyClient.getCompany(job.getCompanyId());
			
			List<Review> reviews = reviewClient.getReview(job.getCompanyId());
			
			JobDTO jobDTO = JobMapper.mapToJobWithCompanyDTO(job, company, reviews);
//			jobWithCompanyDTO.setCompany(company);
			
		return jobDTO;
	}

	@Override
	public void createJob(Job job) {
		job.setId(nextId++);
		jobRepository.save(job);
	}

	@Override
	public JobDTO getJobById(Long id) {
		Job job =  jobRepository.findById(id).orElse(null);
		return convertToDto(job);
	}

	@Override
	public boolean deleteJobById(Long id) {
		try {
			jobRepository.deleteById(id);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateJob(Long id, Job updateJob) {
		Optional<Job> jobOptional = jobRepository.findById(id);
		if(jobOptional.isPresent()) {
			Job job = jobOptional.get();
			job.setTitle(updateJob.getTitle());
			job.setDescription(updateJob.getDescription());
			job.setMinSalary(updateJob.getMinSalary());
			job.setMaxSalary(updateJob.getMaxSalary());
			job.setLocation(updateJob.getLocation());
			jobRepository.save(job);
			return true;
			
		}
		return false;
	}

	
}
