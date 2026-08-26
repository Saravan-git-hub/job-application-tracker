package com.sarav.jobtracker.service;

import com.sarav.jobtracker.repository.JobRepository;
import com.sarav.jobtracker.entity.JobApplication;
import com.sarav.jobtracker.entity.dto.JobApplicationRequestDTO;
import com.sarav.jobtracker.entity.dto.JobStatistics;
import com.sarav.jobtracker.entity.JobStatus;
import com.sarav.jobtracker.exception.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobService {
    private final JobRepository jobRepository;
    public JobService(JobRepository jobRepository){
        this.jobRepository=jobRepository;
    }
    private static final List<JobStatus> ACTIVE_STATUSES=List.of(
        JobStatus.APPLIED,
        JobStatus.INTERVIEW
    );
    public JobApplication addJob(JobApplication job){
        boolean hasActiveApplication=jobRepository.existsByCompanyNameIgnoreCaseAndRoleIgnoreCaseAndStatusIn(job.getCompanyName(),job.getRole(),ACTIVE_STATUSES);
        if(hasActiveApplication){
            throw new DuplicateResourceException("You already have an active application(APPLIED/INTERVIEW) for "
        +job.getRole()+" at "+job.getCompanyName());
        }
        return jobRepository.save(job);
    }
    public List<JobApplication> getAllJobs(){
        return jobRepository.findAll();
    }
    public void deleteJob(Long id) {
    if (!jobRepository.existsById(id)) {
        throw new ResourceNotFoundException("Job application not found with id: " + id);
    }

    jobRepository.deleteById(id);
}
    public JobApplication getJobById(Long id){
        return jobRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Job application not found with Id:"+id));
    }
    public List<JobApplication> getJobsByCompany(String company){
        return jobRepository.findByCompanyNameIgnoreCase(company);
    }
    public List<JobApplication> getJobsByStatus(JobStatus status){
        return jobRepository.findByStatus(status);
    }

    public JobStatistics getStatistics(){
        int applied=(int)jobRepository.countByStatus(JobStatus.APPLIED);
        int interview=(int)jobRepository.countByStatus(JobStatus.INTERVIEW);
        int rejected=(int)jobRepository.countByStatus(JobStatus.REJECTED);
        int selected=(int)jobRepository.countByStatus(JobStatus.SELECTED);
        int offer=(int)jobRepository.countByStatus(JobStatus.OFFER);
        int total=applied+interview+rejected+selected+offer;

        return new JobStatistics(total,applied,interview,selected,rejected,offer);
    }
    public JobApplication updateApplication(Long id,JobApplicationRequestDTO dto){
        JobApplication existingJob=jobRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Job application not found with Id:"+id));
        validateStateTransition(existingJob.getStatus(), dto.getStatus());
        existingJob.setCompanyName(dto.getCompanyName());
        existingJob.setRole(dto.getRole());
        existingJob.setStatus(dto.getStatus());
        existingJob.setJobLink(dto.getJobLink());

        return jobRepository.save(existingJob);
    }
    private void validateStateTransition(JobStatus currentStatus,JobStatus newStatus){
        if((currentStatus==JobStatus.REJECTED || currentStatus==JobStatus.SELECTED)
        && currentStatus!=newStatus){
    throw new InvalidStateTransitionException("Cannot Transition application from terminal state "+currentStatus+" to "+newStatus);
    }
    }
    public Page<JobApplication> getJobsWithPagination(JobStatus status,int page,int size){
        Pageable pageable=PageRequest.of(page,size);
        if(status!=null){
            return jobRepository.findByStatus(status,pageable);

        }
        return jobRepository.findAll(pageable);
    }
}

