package com.jin.bookreviewhub;

import com.jin.bookreviewhub.domain.user.SocialAccountEntity;
import com.jin.bookreviewhub.domain.user.UsrEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SocialAccountRepository extends JpaRepository<SocialAccountEntity, Long>{

    Optional<SocialAccountEntity> findByProviderAndProviderUserId(
            @Param("provider")String provider,
            @Param("providerUserId")String providerUserId);

    @Query("select u " +
           "from SocialAccountEntity sa " +
           "join sa.user u " +
           "where sa.provider = :provider " +
           "and sa.providerUserId = :providerUserId")
    Optional<UsrEntity> findUserByProviderAndProviderUserId(
            @Param("provider") String provider,
            @Param("providerUserId") String providerUserId
    );

    boolean existsByProviderAndProviderUserId(String provider, String providerUserId);
}
