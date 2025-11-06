package com.jin.bookreviewhub.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/***** application.properties 의 설정값을 읽어올 Configuration 클래스 *****/
@Configuration
@ConfigurationProperties(prefix = "naver.api")
@Getter
@Setter
public class NaverApiConfig {

    private String clientId;
    private String clientSecret;
    private String search;

    @Getter
    @Setter
    public static class Search {
        private Book book;
        private Movie movie;
    }

    @Getter
    @Setter
    public static class Book {
        private String url;
    }

    @Getter
    @Setter
    public static class Movie {
        private String url;
    }
}
