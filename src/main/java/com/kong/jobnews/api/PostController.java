package com.kong.jobnews.api;

import com.kong.jobnews.dto.PageResponse;
import com.kong.jobnews.dto.PostCreateRequest;
import com.kong.jobnews.dto.PostResponse;
import com.kong.jobnews.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor    // final 필드를 대상으로 생성자를 자동 생성 --> 생성자 주입
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // 게시글 생성
    @PostMapping
    public PostResponse create(@RequestBody @Valid PostCreateRequest req) {
        return postService.create(req);
    }

    // 게시글 목록 조회(페이징)
    @GetMapping
    public PageResponse<PostResponse> list(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return PageResponse.from(postService.list((pageable)));
    }

    // 단건 조회
    @GetMapping("/{id}")
    public PostResponse get(@PathVariable Long id) {
        return postService.get(id);
    }
}
