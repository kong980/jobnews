package com.kong.jobnews.repository;

import com.kong.jobnews.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByUrl(String url);
}
