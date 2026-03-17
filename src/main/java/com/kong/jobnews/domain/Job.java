package com.kong.jobnews.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company; // 회사명

    private String title;   // 공고 제목

    private String experience;  // 경력

    private String education;   // 학력

    private String location;    // 지역

    private String url; // 공고 상세 페이지 주소

    private LocalDate createdAt;    // 공고 게시일

    private LocalDate endAt;    // 공고 마감일
}
