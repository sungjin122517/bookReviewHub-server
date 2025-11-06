package com.jin.bookreviewhub.common.util.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverMovieSearchResponse {

    @JsonProperty("total")
    private int total;

    @JsonProperty("start")
    private int start;

    @JsonProperty("display")
    private int display;

    @JsonProperty("items")
    private List<MovieItem> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MovieItem {

            @JsonProperty("title")
            private String title;

            @JsonProperty("link")
            private String link;

            @JsonProperty("image")
            private String image;

            @JsonProperty("subtitle")
            private String subtitle;

            @JsonProperty("pubDate")
            private String pubDate;

            @JsonProperty("director")
            private String director;

            @JsonProperty("actor")
            private String actor;

            @JsonProperty("userRating")
            private String userRating;
    }
}
