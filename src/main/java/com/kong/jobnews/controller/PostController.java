package com.kong.jobnews.controller;

import com.kong.jobnews.dto.CrawlingDto;
import com.kong.jobnews.service.CrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor    // final 필드를 대상으로 생성자를 자동 생성 --> 생성자 주입
@RequestMapping("/api/posts")
public class PostController {
    private final CrawlingService crawlingService;


    // 크롤링
    /*
    1. PostMapping으로 요청
    2. CrawlingService에서 반환
     */
    @PostMapping("/crawling")
    public List<CrawlingDto> createPost() throws IOException {
        return crawlingService.crawling();

    }
}
