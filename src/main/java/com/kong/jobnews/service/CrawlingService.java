package com.kong.jobnews.service;

import com.kong.jobnews.domain.Crawling;
import com.kong.jobnews.dto.CrawlingDto;
import com.kong.jobnews.repository.CrawlingRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CrawlingService {
    private final CrawlingRepository postRepository;

    // 크롤링 데이터 수집
    public List<CrawlingDto> crawling() throws IOException {
        String url = "https://www.work24.go.kr/wk/a/b/1200/retriveDtlEmpSrchList.do?occupation=133201"; // "Java 프로그래밍 언어 전문가" 분야만 선택
        String page = "&pageIndex=";

        List<Crawling> result = new ArrayList<>();

        for(int i=1; i<=1; i++){
            Document doc = Jsoup.connect(url + page + i).get();

            Elements rows = doc.select("table#contentArea tbody tr");   // 채용 공고 목록 추출

            for(Element row : rows){
                String company = row.select(".box_chk-group .cp_name.underline_hover").text();  // 회사명

                Element link = row.selectFirst("a.t3_sb.underline_hover");    // 공고 링크 선택
                String href = link.absUrl("href");    // 각 공고별 링크 주소만 추출

                String location =  row.select(".site").text();  // 회사 위치

                Elements specs = row.select(".member .item.sm");   // 경력, 학력 추출
                String experience = specs.get(0).text();
                String education = specs.get(1).text();

//                LocalDateTime createdAt = link.select(".pd24 .sl_r").last().text().
//                LocalDateTime createdAt = link.select(".pd24 .sl_r").first().text().

                // 전체 공고명 추출을 위해 링크 href 링크로 접속
                Document site = Jsoup.connect(href).get();
                String  title = site.select(".tit_area .title").text();

                result.add(Crawling.builder()
                        .url(href)
                        .company(company)
                        .title(title)
                        .experience(experience)
                        .education(education)
                        .location(location)
                        .build());
            }
        }
        List<Crawling> saved = postRepository.saveAll(result);
        return saved.stream()
                .map(CrawlingDto::from)
                .toList();

    }
}
