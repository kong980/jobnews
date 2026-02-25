package com.kong.jobnews.api;

import com.kong.jobnews.dto.PostCreateRequest;
import com.kong.jobnews.domain.Post;
import com.kong.jobnews.repository.PostRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;

    @PostMapping
    public Post create(@RequestBody @Valid PostCreateRequest req) {
        Post post = Post.builder()
                .title(req.title())
                .url(req.url())
                .createdAt(LocalDateTime.now())
                .build();
        return postRepository.save(post);
    }

    @GetMapping
    public List<Post> list() {
        return postRepository.findAll();
    }
}
