package com.kong.jobnews.repository;

import com.kong.jobnews.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    Optional<Job> findByUrl(String url);
    Optional<Job> deleteByEndAtBefore(LocalDate today);
}
