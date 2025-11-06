package com.jin.bookreviewhub.web.controller;

import com.jin.bookreviewhub.application.NaverApiService;
import com.jin.bookreviewhub.common.util.naver.NaverBookSearchResponse;
import com.jin.bookreviewhub.common.util.naver.NaverMovieSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final NaverApiService naverApiService;

    /**
     * Book Search API
     */
    @GetMapping("/books")
    public ResponseEntity<NaverBookSearchResponse> searchBooks(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "10") Integer display,
            @RequestParam(required = false, defaultValue = "1") Integer start) {

        NaverBookSearchResponse response = naverApiService.searchBooks(query, display, start);

        return ResponseEntity.ok(response);
    }

    /**
     * Movie Search API : 2023년 3월 31일 부로 종료되었음
     */
    /*
    @GetMapping("/movies")
    public ResponseEntity<NaverMovieSearchResponse> searchMovies(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "10") Integer display,
            @RequestParam(required = false, defaultValue = "1") Integer start) {

        NaverMovieSearchResponse response = naverApiService.searchMovies(query, display, start);

        return ResponseEntity.ok(response);
    }
     */
}
