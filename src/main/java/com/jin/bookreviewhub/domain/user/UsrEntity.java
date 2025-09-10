package com.jin.bookreviewhub.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "usr")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UsrEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // BIGSERIAL
    private Long userId;

    @Column(length = 30, unique = true, nullable = false)
    private String nickname;

    @Column(length = 30, unique = true, nullable = false)
    private String userNm;

    @Column(length = 255, unique = true)
    private String email;

    @Column(nullable = false, columnDefinition = "timestamptz")
    private OffsetDateTime createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = OffsetDateTime.now();
    }
}