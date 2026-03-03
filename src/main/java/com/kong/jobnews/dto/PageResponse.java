package com.kong.jobnews.dto;

import org.springframework.data.domain.Page;

import java.util.List;

// Page 응답 설정 : 필요한 정보만 추려서 API 응답 구조를 통제
public record PageResponse<T>(
        List<T> data,   // 실제 데이터 리스트
        int page,   // 현재 페이지 번호(0부터 시작)
        int size,
        long totalElements,
        int totalPages,
        boolean isLast  // 마지막 페이지인지
) {
    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),  // 실제 데이터
                page.getNumber(),   // 현재 페이지 번호
                page.getSize(), // 페이지 크기
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
