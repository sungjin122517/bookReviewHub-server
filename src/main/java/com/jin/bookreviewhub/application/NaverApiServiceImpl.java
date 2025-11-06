package com.jin.bookreviewhub.application;

import com.jin.bookreviewhub.common.util.naver.NaverBookSearchResponse;
import com.jin.bookreviewhub.common.util.naver.NaverMovieSearchResponse;
import com.jin.bookreviewhub.config.NaverApiConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Naver API Service 구현체
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NaverApiServiceImpl implements NaverApiService {

    private final NaverApiConfig naverApiConfig;
    private final WebClient.Builder webClientBuilder;

    @Override
    public NaverBookSearchResponse searchBooks(String query, Integer display, Integer start) {

        WebClient webClient = webClientBuilder.build();

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("openapi.naver.com")
                        .path("/v1/search/book.json")
                        .queryParam("query", query)
                        .queryParam("display", display != null ? display : 10)
                        .queryParam("start", start != null ? start : 1)
                        .build())
                .header("X-Naver-Client-Id", naverApiConfig.getClientId())
                .header("X-Naver-Client-Secret", naverApiConfig.getClientSecret())
                .retrieve()
                .bodyToMono(NaverBookSearchResponse.class)
                .doOnError(error -> log.error("Error searching books: ", error))
                .block();
    }

    @Override
    public NaverMovieSearchResponse searchMovies(String query, Integer display, Integer start) {
        WebClient webClient = webClientBuilder.build();

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("openapi.naver.com")
                        .path("/v1/search/movie.json")
                        .queryParam("query", query)
                        .queryParam("display", display != null ? display : 10)
                        .queryParam("start", start != null ? start : 1)
                        .build())
                .header("X-Naver-Client-Id", naverApiConfig.getClientId())
                .header("X-Naver-Client-Secret", naverApiConfig.getClientSecret())
                .retrieve()
                .bodyToMono(NaverMovieSearchResponse.class)
                .doOnError(error -> log.error("Error searching movies: ", error))
                .block();
    }
}
