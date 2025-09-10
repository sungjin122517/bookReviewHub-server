package com.jin.bookreviewhub.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsrRepository extends JpaRepository<UsrEntity, Long> {
    Optional<UsrEntity> findByEmail(String email);
    Optional<UsrEntity> findByNickname(String nickname);
    boolean existsByNickname(String nickname);
}
