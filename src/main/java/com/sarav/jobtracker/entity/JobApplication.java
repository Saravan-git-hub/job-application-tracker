package com.sarav.jobtracker.entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="jobs")
public class JobApplication {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 100)
    private String role;
    @Column(nullable = false,length = 100)
    private String companyName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;
    private LocalDate appliedDate; 
    private String salaryPackage;
    private String jobLink;
    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public JobApplication(){

    }
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role=role;
    }
    public String getCompanyName(){
        return companyName;
    }
    public void setCompanyName(String companyName){
        this.companyName=companyName;
    }
    public JobStatus getStatus(){
        return status;
    }
    public void setStatus(JobStatus status){
        this.status=status;
    }
    public LocalDate getAppliedDate(){
        return appliedDate;
    }
    public void setAppliedDate(LocalDate appliedDate){
        this.appliedDate=appliedDate;
    }
    public String getSalaryPackage(){
        return salaryPackage;
    }
    public void setSalaryPackage(String salaryPackage){
        this.salaryPackage=salaryPackage;
    }
    public String getJobLink(){
        return jobLink;
    }
    public void setJobLink(String jobLink){
        this.jobLink=jobLink;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt=createdAt;
    }
    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt=updatedAt;
    }
}
