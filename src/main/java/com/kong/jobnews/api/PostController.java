package com.kong.jobnews.api;

import com.kong.jobnews.dto.PostCreateRequest;
import com.kong.jobnews.dto.PostResponse;
import com.kong.jobnews.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostResponse create(@RequestBody @Valid PostCreateRequest req) {
        return postService.create(req);
    }

    @GetMapping
    public List<PostResponse> list() {
        return postService.list();
    }
}
