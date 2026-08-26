package com.sarav.jobtracker.entity.dto;

import com.sarav.jobtracker.entity.JobStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class JobApplicationRequestDTO {
 
    @NotBlank
    @Size(max=100) 
    private String companyName;
    @NotBlank
    @Size(max=100)
    private String role;
    @NotNull(message="Status cannot be null")
    private JobStatus status;
    @Size(max=500)  
    private String jobLink;

    public JobApplicationRequestDTO(){
    }
    public String getCompanyName(){
        return companyName;
    }
    public void setCompanyName(String companyName){
        this.companyName=companyName;
    }
    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role=role;
    }
    public JobStatus getStatus(){
        return status;
    }
    public void setStatus(JobStatus status){
        this.status=status;
    }
    public String getJobLink(){
        return jobLink;
    }
    public void setJobLink(String jobLink){
        this.jobLink=jobLink;
    }


}
