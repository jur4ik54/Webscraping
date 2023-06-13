package com.keycon.spring.scraping.repository;

import java.util.List;

import com.keycon.spring.scraping.model.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<JobEntity, Long> {
  List<JobEntity> findByLink(String link);
  List<JobEntity> findByTitleContainingIgnoreCase(String title);
  List<JobEntity> findAllByPortalId(String portalId);

  JobEntity findOneByLink(String link);
}

