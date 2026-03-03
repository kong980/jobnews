package com.kong.jobnews.service;

import com.kong.jobnews.domain.Post;
import com.kong.jobnews.dto.PostCreateRequest;
import com.kong.jobnews.dto.PostResponse;
import com.kong.jobnews.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    // 게시글 생성
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

    // 게시글 목록 조회
    @Transactional(readOnly = true)
    public Page<PostResponse> list(Pageable pageable){
        return  postRepository.findAll(pageable)
                .map(PostResponse::from);   // 각 Post를 PostResponse.from(post)로 변환
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public PostResponse get(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 게시물을 찾을 수 없습니다."));

        return  PostResponse.from(post);
    }
}
