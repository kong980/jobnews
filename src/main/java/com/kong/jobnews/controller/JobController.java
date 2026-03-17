package com.kong.jobnews.controller;

import com.kong.jobnews.domain.Job;
import com.kong.jobnews.dto.JobDto;
import com.kong.jobnews.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor    // final 필드를 대상으로 생성자를 자동 생성 --> 생성자 주입
@RequestMapping("/api/job")
public class JobController {
    private final JobService jobService;

    /*  채용 공고 크롤링
            1. PostMapping으로 요청
            2. CrawlingService에서 반환
     */
    @PostMapping("/crawling")
    public List<JobDto> crawling() throws IOException {
        return jobService.crawling();
    }

    /*
        전체 데이터 조회: @PageableDefault로 페이징 처리
            1. 한 페이지에 10건씩
            2. 정렬 조건은 id
            3. 최신순으로 보기 위해 direction을 내림차순으로 설정(디폴트 : ASC)
     */
    @GetMapping
    public Page<Job> view(@PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
        return jobService.view(pageable);
    }
}
