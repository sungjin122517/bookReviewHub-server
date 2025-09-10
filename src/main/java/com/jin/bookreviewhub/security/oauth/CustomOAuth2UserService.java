package com.jin.bookreviewhub.security.oauth;

import com.jin.bookreviewhub.application.AuthService;
import com.jin.bookreviewhub.domain.user.UsrEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final String REG_KAKAO = "kakao";
    private static final String REG_NAVER = "naver";

    private final AuthService authService;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest req) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(req);

        String registrationId = req.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oauth2User.getAttributes();

        OAuthUserProfile profile;
        if (REG_KAKAO.equals(registrationId)) {
            profile = fromKakao(attributes);
        } else if (REG_NAVER.equals(registrationId)) {
            profile = fromNaver(attributes);
        } else {
            throw new OAuth2AuthenticationException("Unsupported provider: " + registrationId);
        }

        String provider = registrationId;
        String providerId = profile.getId();

        if (providerId == null) {
            throw new OAuth2AuthenticationException("Provider user id is null for " + provider);
        }

        // 1. 기존 매핑 확인: 있으면 로그인 (ROLE_USER)
        UsrEntity existing = authService.findBySocialAccount(provider, providerId).orElse(null);
        if (existing != null) {
            Map<String, Object> principalAttrs = Map.of(
                    "uid", existing.getUserId(),
                    "provider", provider,
                    "providerId", providerId,
                    "userNm", existing.getUserNm(),
                    "nickname", existing.getNickname(),
                    "email", existing.getEmail()
            );
            return new DefaultOAuth2User(
                    Set.of(new SimpleGrantedAuthority("ROLE_USER")),
                    principalAttrs,
                    "uid"   // getName() 키
            );
        }

        // 2. 신규: 아직 DB 생성하지 않고 온보딩으로 넘김 (ROLE_GUEST)
        // 성공 핸들러에서 ROLE_GUEST 감지 -> 세션에 pendingProfile 저장 + /onboarding 리다이렉트
        Map<String, Object> principalAttrs = Map.of(
                "provider", provider,
                "providerId", providerId,
                "userNm", profile.getUserNm(),
                "suggestedNickname", profile.getNickname(),
                "email", profile.getEmail()
        );
        return new DefaultOAuth2User(
                Set.of(new SimpleGrantedAuthority("ROLE_GUEST")),
                principalAttrs,
                "providerId"
        );
    }

    private OAuthUserProfile fromKakao(Map<String, Object> attrs) {
        // id
        String id = String.valueOf(attrs.get("id"));

        // kakao_account.profile.nickname
        Map<String, Object> account = (Map<String, Object>) attrs.getOrDefault("kakao_account", Map.of());
        Map<String, Object> profile = (Map<String, Object>) account.getOrDefault("profile", Map.of());

        String nickname = (String) profile.getOrDefault("nickname", "카카오유저");
        String userNm = (String) profile.getOrDefault("nickname", nickname);
        String email = (String) account.get("email"); // 동의 받아야 옴

        return new OAuthUserProfile(id, userNm, nickname, email);
    }

    private OAuthUserProfile fromNaver(Map<String, Object> attrs) {
        // { resultcode, message, response: { id, email, nickname, ... } }
        Map<String, Object> response = (Map<String, Object>) attrs.getOrDefault("response", Map.of());
        String id = (String) response.get("id");
        String nickname = (String) response.getOrDefault("nickname", "네이버유저");
        String userNm = (String) response.getOrDefault("name", nickname);
        String email = (String) response.get("email");
        return new OAuthUserProfile(id, userNm, nickname, email);
    }

    @Getter
    @AllArgsConstructor
    static class OAuthUserProfile {
        private final String id;
        private final String userNm;
        private final String nickname;
        private final String email;
    }
}
