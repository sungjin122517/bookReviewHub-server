package com.jin.bookreviewhub.application;

import com.jin.bookreviewhub.SocialAccountRepository;
import com.jin.bookreviewhub.domain.user.SocialAccountEntity;
import com.jin.bookreviewhub.domain.user.UsrEntity;
import com.jin.bookreviewhub.domain.user.UsrRepository;
import com.jin.bookreviewhub.security.oauth.OAuthProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/* Core business logic */

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsrRepository usrRepository;
    private final SocialAccountRepository socialAccountRepository;

    @Transactional(readOnly = true)
    public Optional<UsrEntity> findBySocialAccount(String provider, String providerUserId) {
        return socialAccountRepository.findUserByProviderAndProviderUserId(provider, providerUserId);
    }

    @Transactional
    public UsrEntity createUserAndLinkSocialAccount(String nickname, String userNm, String email, String provider, String providerUserId) {
        // 닉네임/이메일 정책 검증 + 중복 검사
        if (usrRepository.existsByNickname(nickname)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "nickname_taken");
        }

        UsrEntity user = usrRepository.save(
                UsrEntity.builder()
                        .nickname(nickname)
                        .userNm(userNm)
                        .email(email)
                        .build()
        );
        socialAccountRepository.save(
                SocialAccountEntity.builder()
                        .user(user)
                        .provider(provider)
                        .providerUserId(providerUserId)
                        .emailAtSignup(email)
                        .build()
        );

        return user;
    }
}
