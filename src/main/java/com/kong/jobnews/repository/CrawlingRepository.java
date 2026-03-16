package com.kong.jobnews.repository;

import com.kong.jobnews.domain.Crawling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlingRepository extends JpaRepository<Crawling, Long> {

}
