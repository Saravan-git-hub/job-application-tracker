package com.sarav.jobtracker.controller;

import java.util.List;

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

import com.sarav.jobtracker.entity.JobApplication;
import com.sarav.jobtracker.entity.dto.JobApplicationRequestDTO;
import com.sarav.jobtracker.entity.dto.JobStatistics;
import com.sarav.jobtracker.entity.JobStatus;
import com.sarav.jobtracker.service.JobService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;


@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<JobApplication> createJob(@Valid @RequestBody JobApplicationRequestDTO dto) {
        JobApplication job=new JobApplication();
        job.setCompanyName(dto.getCompanyName());
        job.setRole(dto.getRole());
        job.setStatus(dto.getStatus());
        job.setJobLink(dto.getJobLink());
        JobApplication savedJob = jobService.addJob(job);
        return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JobApplication>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
         jobService.deleteJob(id);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<JobApplication>> getJobsByStatus(@PathVariable JobStatus status) {
        return ResponseEntity.ok(jobService.getJobsByStatus(status));
    }

    @GetMapping("/company/{company}")
    public ResponseEntity<List<JobApplication>> getJobsByCompany(@PathVariable String company) {
        return ResponseEntity.ok(jobService.getJobsByCompany(company));
    }

    @GetMapping("/stats")
    public ResponseEntity<JobStatistics> getStatistics() {
        return ResponseEntity.ok(jobService.getStatistics());
    }
    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> updateApplication(@PathVariable Long id,@Valid @RequestBody JobApplicationRequestDTO dto){
        return ResponseEntity.ok(jobService.updateApplication(id, dto));

    }
    @GetMapping("/search")
    public ResponseEntity<Page<JobApplication>> getJobWithPagination(@RequestParam(required=false) JobStatus status,
     @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(jobService.getJobsWithPagination(status, page, size));
    }  
    
}