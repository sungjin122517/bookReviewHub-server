package com.jin.bookreviewhub.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "social_account",
        uniqueConstraints = @UniqueConstraint(name = "uq_provider_user", columnNames = {"provider","provider_user_id"})
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class SocialAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // BIGSERIAL
    private Long socialAccountId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_social_account_user"))
    private UsrEntity user;

    @Column(length = 20, nullable = false)
    private String provider; // kakao / naver

    @Column(name = "provider_user_id", length = 100, nullable = false)
    private String providerUserId;

    @Column(name = "email_at_signup", length = 255)
    private String emailAtSignup;

    @Column(nullable = false, columnDefinition = "timestamptz")
    private OffsetDateTime createdAt;

    @Column(nullable = false, columnDefinition = "timestamptz")
    private OffsetDateTime lastLoginAt;

    @PrePersist
    void onCreate() {
        var now = OffsetDateTime.now();
        if (createdAt == null) createdAt = now;
        if (lastLoginAt == null) lastLoginAt = now;
    }

    public void touchLastLogin() {
        this.lastLoginAt = OffsetDateTime.now();
    }
}
