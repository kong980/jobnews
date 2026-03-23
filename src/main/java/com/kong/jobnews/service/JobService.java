package com.kong.jobnews.service;

import com.kong.jobnews.domain.Job;
import com.kong.jobnews.dto.JobDto;
import com.kong.jobnews.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class JobService {
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    private final JobRepository jobRepository;

    // 크롤링 데이터 수집
    public List<JobDto> crawling() throws IOException {
        String url = "https://www.work24.go.kr/wk/a/b/1200/retriveDtlEmpSrchList.do?occupation=133201"; //"Java 프로그래밍 언어 전문가" 분야만 선택
        String page = "&pageIndex=";

        List<Job> result = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Document doc = Jsoup.connect(url + page + i).get();
            Elements rows = doc.select("table#contentArea tbody tr");   // 채용 공고 목록 추출

            for (Element row : rows) {
                String company = row.select(".box_chk-group .cp_name.underline_hover").text();

                Element link = row.selectFirst("a.t3_sb.underline_hover");  // 공고 링크 선택
                if (link == null) {
                    continue;
                }
                String href = link.absUrl("href");  // 각 공고별 링크 주소만 추출
                // 중복 방지
                if((jobRepository.findByUrl(href)).isPresent()){
                    continue;
                }

                String location = row.select(".site").text();   // 근무지

                Elements specs = row.select(".member .item.sm");    // 학력 & 경력
                if (specs.size() < 2) {
                    continue;
                }
                String experience = specs.get(0).text();    // 경력
                String education = specs.get(1).text();     // 학력

                Elements dates = row.select(".pd24").last().select(".s1_r");    // 공고일 & 마감일
                LocalDate createdAt;
                LocalDate endAt;

                // 데이터가 없는 경우
                if(dates.size() == 0){
                    continue;
                }
                // 마감일이 따로 없이 '채용시까지'인 경우
                else if (dates.size() == 1) {
                    createdAt = parseDate(dates.get(0).text());
                    endAt = createdAt.plusMonths(3);  // 공고 진행일에서 3달 추가
                }
                else{
                    endAt = parseDate(dates.get(0).text());   // 마감일
                    createdAt = parseDate(dates.get(1).text());   // 공고일
                }

                // 채용 공고 제목 추출을 위해 세부 페이지로 이동
                Document site = Jsoup.connect(href).get();
                String title = site.select(".tit_area .title").text();

                result.add(Job.builder()
                        .url(href)
                        .company(company)
                        .title(title)
                        .experience(experience)
                        .education(education)
                        .location(location)
                        .createdAt(createdAt)
                        .endAt(endAt)
                        .build());
            }
        }

        List<Job> saved = jobRepository.saveAll(result);
        return saved.stream()
                .map(JobDto::from)
                .toList();
    }

    // 등록일 & 마감일 날짜 변환을 위한 메서드
    private LocalDate parseDate(String rawText) {
        Matcher matcher = DATE_PATTERN.matcher(rawText);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Failed to extract date from text: " + rawText);
        }
        return LocalDate.parse(matcher.group());
    }

    // 공고 전체 조회
    public Page<Job> view(Pageable pageable, JobDto jobDto) {
        // 마감된 공고 데이터 삭제
        LocalDate today = LocalDate.now();
        jobRepository.deleteByEndAtBefore(today);

        return jobRepository.findAll(pageable);
    }
}
