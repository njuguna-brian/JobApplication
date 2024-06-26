package com.brian.jobsapp.job.impl;

import com.brian.jobsapp.job.Job;
import com.brian.jobsapp.job.JobRepository;
import com.brian.jobsapp.job.JobService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    JobRepository jobRepository;
    private Long nextId = 1L;
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public void createJob(Job job) {
        job.setId(nextId++);
        jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateJob(Long id, Job updateJob) {
        if (jobRepository.existsById(id)) {
            updateJob.setId(id);
            jobRepository.save(updateJob);
            return true;
        }
        return false;
    }
}
