package com.jin.bookreviewhub.domain.work;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.ZonedDateTime;

@Entity
@Table(name = "work")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_id")
    private Long workId;

    private WorkType type;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "sub_title", length = 255)
    private String subTitle;

    @Column(name = "year")
    private Integer year;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "authors", columnDefinition = "text[]")
    private String[] authors;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "directors", columnDefinition = "text[]")
    private String[] directors;

    @Column(name = "isbn13", length = 13, unique = true)
    private String isbn13;

    @Column(name = "external_id", length = 255)
    private String externalId;

    @Column(name = "poster_url", length = 255)
    private String posterUrl;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = ZonedDateTime.now();
    }

}
