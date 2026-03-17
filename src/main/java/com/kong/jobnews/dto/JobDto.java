package com.kong.jobnews.dto;

import com.kong.jobnews.domain.Job;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class JobDto {
    private Long id;

    private String company; // 회사명

    private String title;   // 공고 제목

    private String experience;  // 경력

    private String education;   // 학력

    private String location;    // 지역

    private String url; // 공고 상세 페이지 주소

    private LocalDate createdAt;    // 공고 게시일

    private LocalDate endAt;    // 공고 마감일

    public static JobDto from(Job job) {
        return JobDto.builder()
                .id(job.getId())
                .company(job.getCompany())
                .title(job.getTitle())
                .experience(job.getExperience())
                .education(job.getEducation())
                .location(job.getLocation())
                .url(job.getUrl())
                .createdAt(job.getCreatedAt())
                .endAt(job.getEndAt())
                .build();
    }
}
