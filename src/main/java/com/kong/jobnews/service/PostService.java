package com.kong.jobnews.service;

import com.kong.jobnews.domain.Post;
import com.kong.jobnews.dto.PostCreateRequest;
import com.kong.jobnews.dto.PostResponse;
import com.kong.jobnews.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostResponse create(PostCreateRequest req){
        if(postRepository.existsByUrl(req.url())){
            throw  new IllegalArgumentException("이미 존재하는 뉴스입니다.");
        }
        Post post = Post.builder()
                .title(req.title())
                .url(req.url())
                .createdAt(LocalDateTime.now())
                .build();

        Post saved = postRepository.save(post);

        return  PostResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> list(){
        return  postRepository.findAll()
                .stream()
                .map(PostResponse::from)
                .toList();
    }
}
