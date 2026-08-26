package com.sarav.jobtracker.service;

import com.sarav.jobtracker.entity.dto.JobApplicationRequestDTO;
import com.sarav.jobtracker.exception.InvalidStateTransitionException;
import com.sarav.jobtracker.exception.ResourceNotFoundException;
import com.sarav.jobtracker.entity.JobApplication;
import com.sarav.jobtracker.entity.JobStatus;
import com.sarav.jobtracker.repository.JobRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class JobServiceTest {
    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    @Test
    @DisplayName("Should create job application successfully")
    void testCreateApplication_Success(){
        JobApplicationRequestDTO requestDTO=new JobApplicationRequestDTO();
        requestDTO.setCompanyName("Zoho");
        requestDTO.setRole("Java Backend Developer");
        requestDTO.setStatus(JobStatus.APPLIED);

        JobApplication savedJob=new JobApplication();
        savedJob.setId(10L);
        savedJob.setCompanyName("Zoho");
        savedJob.setRole("Java Backend Developer");
        savedJob.setStatus(JobStatus.APPLIED);

        when(jobRepository.save(any(JobApplication.class))).thenReturn(savedJob);

        JobApplication result=jobService.addJob(savedJob);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Zoho", result.getCompanyName());
        verify(jobRepository,times(1)).save(any(JobApplication.class));
    }

    @Test
@DisplayName("Should delete existing job successfully")
void testDeleteJob_Success() {
    Long jobId = 1L;

    when(jobRepository.existsById(jobId)).thenReturn(true);
    doNothing().when(jobRepository).deleteById(jobId);

    jobService.deleteJob(jobId);

    verify(jobRepository, times(1)).existsById(jobId);
    verify(jobRepository, times(1)).deleteById(jobId);
}

@Test
@DisplayName("Should throw ResourceNotFoundException when deleting non-existing job")
void testDeleteJob_ThrowsException_WhenNotFound() {
    Long nonExistingId = 999L;

    when(jobRepository.existsById(nonExistingId)).thenReturn(false);

    assertThrows(
            ResourceNotFoundException.class,
            () -> jobService.deleteJob(nonExistingId)
    );

    verify(jobRepository, times(1)).existsById(nonExistingId);
    verify(jobRepository, never()).deleteById(anyLong());
}

    @Test
    @DisplayName("should update status successfully for valid transition")
    void testUpdateStatus_Success(){
        Long jobId=1L;
        JobApplication existingJob=new JobApplication();
        existingJob.setId(jobId);
        existingJob.setCompanyName("Amazon");
        existingJob.setRole("Backend Developer");
        existingJob.setStatus(JobStatus.APPLIED);
        JobApplicationRequestDTO updateDTO=new JobApplicationRequestDTO();
        updateDTO.setCompanyName("Amazon");
        updateDTO.setRole("Backend Developer");
        updateDTO.setStatus(JobStatus.APPLIED);


        when(jobRepository.findById(jobId)).thenReturn(Optional.of(existingJob));
        when(jobRepository.save(any(JobApplication.class))).thenAnswer(invocation->invocation.getArgument(0));
        JobApplication result=jobService.updateApplication(jobId, updateDTO);

        assertNotNull(result);
        assertEquals(JobStatus.APPLIED,result.getStatus());
        verify(jobRepository,times(1)).save(existingJob);
    }

    @Test
    @DisplayName("should throw InvalidStateTransitionException when updating from terminal status REJECTED")
    void testUpdateApplication_ThrowsException_WhenStatusIsRejected(){
        Long jobId=1L;
        JobApplication rejectedJob=new JobApplication();
        rejectedJob.setId(jobId);
        rejectedJob.setCompanyName("Amazon");
        rejectedJob.setStatus(JobStatus.REJECTED);
        JobApplicationRequestDTO updateDTO=new JobApplicationRequestDTO();
        updateDTO.setCompanyName("Amazon");
        updateDTO.setStatus(JobStatus.INTERVIEW);
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(rejectedJob));
        assertThrows(InvalidStateTransitionException.class,()->{
            jobService.updateApplication(jobId, updateDTO);
        });
        verify(jobRepository,never()).save(any(JobApplication.class));

    }   
}
