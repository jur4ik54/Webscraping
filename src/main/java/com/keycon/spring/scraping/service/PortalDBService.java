package com.keycon.spring.scraping.service;

import com.keycon.spring.scraping.model.JobEntity;
import com.keycon.spring.scraping.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PortalDBService {
    @Autowired
    JobRepository jobRepository;

    public List<JobEntity> getAll(){
        return jobRepository.findAll(Sort.by("portalId"));
    }
}
