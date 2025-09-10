package com.jin.bookreviewhub.web.controller;

import com.jin.bookreviewhub.application.AuthService;
import com.jin.bookreviewhub.domain.user.UsrEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OnboardingController {

    private final AuthService authService;

    @GetMapping("/onboarding")
    public Map<String, Object> complete(HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<String, Object> pendingProfile = (Map<String, Object>) session.getAttribute("pendingProfile");

        if (pendingProfile == null) {
            return Map.of("status", "NO_PENDING");
        }
        return Map.of(
                "status", "PENDING",
                "suggestedNickname", pendingProfile.get("suggestedNickname"),
                "userNm", pendingProfile.get("userNm"),
                "email", pendingProfile.get("email")
        );
    }

    @PostMapping(value = "/onboarding", consumes = "application/json", produces = "application/json")
    public Map<String, Object> complete(HttpSession session, @RequestBody Map<String, String> body) {
        @SuppressWarnings("unchecked")
        Map<String, Object> pendingProfile = (Map<String, Object>) session.getAttribute("pendingProfile");

        if (pendingProfile == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No pending signup");
        }

        String nickname = body.get("nickname");
        if (nickname == null || nickname.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nickname is required");
        }

        UsrEntity user = authService.createUserAndLinkSocialAccount(
                nickname,
                (String) pendingProfile.get("userNm"),
                (String) pendingProfile.get("email"),
                (String) pendingProfile.get("provider"),
                (String) pendingProfile.get("providerId")
        );
        session.removeAttribute("pendingProfile");

        return Map.of(
                "status", "OK",
                "uid", user.getUserId(),
                "email", user.getEmail(),
                "nickname", user.getNickname(),
                "userNm", user.getUserNm()
        );
    }
    @GetMapping("/me")
    public Map<String, Object> getMe(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            return Map.of("authenticated", false);
        }
        return Map.of(
                "authenticated", true,
                "uid", user.getAttribute("uid"),
                "nickname", user.getAttribute("nickname"),
                "email", user.getAttribute("email"),
                "userNm", user.getAttribute("userNm")
        );
    }
}
