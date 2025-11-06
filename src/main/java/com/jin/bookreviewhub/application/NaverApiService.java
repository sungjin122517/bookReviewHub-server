package com.jin.bookreviewhub.application;

import com.jin.bookreviewhub.common.util.naver.NaverBookSearchResponse;
import com.jin.bookreviewhub.common.util.naver.NaverMovieSearchResponse;

public interface NaverApiService {
    /**
     * Search Book from Naver
     */
    NaverBookSearchResponse searchBooks(String query, Integer display, Integer start);

    /**
     * Search Movie from Naver
     */
    NaverMovieSearchResponse searchMovies(String query, Integer display, Integer start);
}
