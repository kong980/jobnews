package com.kong.jobnews.dto;

import com.kong.jobnews.domain.Crawling;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CrawlingDto {
    private Long id;

    private String company; // 회사명

    private String title;   // 공고 제목

    private String experience;  // 경력

    private String education;   // 학력

    private String location;    // 지역

    private String url; // 공고 상세 페이지 주소

    private LocalDateTime createdAt;    // 공고 게시일

    private LocalDateTime endAt;    // 공고 마감일

    public static CrawlingDto from(Crawling crawling) {
        return CrawlingDto.builder()
                .id(crawling.getId())
                .company(crawling.getCompany())
                .title(crawling.getTitle())
                .experience(crawling.getExperience())
                .education(crawling.getEducation())
                .location(crawling.getLocation())
                .url(crawling.getUrl())
                .createdAt(crawling.getCreatedAt())
                .endAt(crawling.getEndAt())
                .build();
    }
}
