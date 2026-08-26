package com.sarav.jobtracker.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sarav.jobtracker.entity.JobApplication;
import com.sarav.jobtracker.entity.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;;



@Repository
public interface JobRepository extends JpaRepository<JobApplication,Long>{
    List<JobApplication> findByCompanyNameIgnoreCase(String companyName);
    List<JobApplication> findByStatus(JobStatus status);
    long countByStatus(JobStatus status);
    boolean existsByCompanyNameIgnoreCaseAndRoleIgnoreCaseAndStatusIn(String companyName,String role,List<JobStatus> statuses);
    Page<JobApplication> findByStatus(JobStatus status,Pageable pageable);
}
