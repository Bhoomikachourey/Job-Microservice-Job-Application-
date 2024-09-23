package com.pack.jobs.dto;

import java.util.List;

import com.pack.jobs.external.Company;
import com.pack.jobs.external.Review;

public class JobDTO {

	private Long id;
	private String title;
	private String description;
	private String minSalary;
	private String maxSalary;
	private String location;
	private Company company;
	private List<Review> review;
	public JobDTO() {
		super();
	}
	

	public JobDTO(Long id, String title, String description, String minSalary, String maxSalary, String location,
			Company company, List<Review> review) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
		this.location = location;
		this.company = company;
		this.review = review;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(String minSalary) {
		this.minSalary = minSalary;
	}

	public String getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(String maxSalary) {
		this.maxSalary = maxSalary;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Review> getReview() {
		return review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}

	
}
