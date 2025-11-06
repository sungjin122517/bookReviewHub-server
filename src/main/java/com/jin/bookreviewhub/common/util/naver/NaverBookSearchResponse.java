package com.jin.bookreviewhub.common.util.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverBookSearchResponse {

    @JsonProperty("total")
    private int total;

    @JsonProperty("start")
    private int start;

    @JsonProperty("display")
    private int display;

    @JsonProperty("items")
    private List<BookItem> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BookItem {

        @JsonProperty("title")
        private String title;

        @JsonProperty("link")
        private String link;

        @JsonProperty("image")
        private String image;

        @JsonProperty("author")
        private String author;

        @JsonProperty("discount")
        private String discount;

        @JsonProperty("publisher")
        private String publisher;

        @JsonProperty("pubDate")
        private String pubDate;

        @JsonProperty("isbn")
        private String isbn;

        @JsonProperty("description")
        private String description;
    }
}
