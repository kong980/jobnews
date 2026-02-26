package com.kong.jobnews.dto;

import com.kong.jobnews.domain.Post;

import java.time.LocalDateTime;

public record PostResponse(Long id, String title, String url, LocalDateTime createdAt) {
    public static PostResponse from(Post post){
        return new PostResponse(post.getId(), post.getTitle(), post.getUrl(), post.getCreatedAt());
    }
}
