package com.jin.bookreviewhub.security.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@Component
public class SessionSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${app.frontend-base}")
    private String frontendBase;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        boolean isGuest = authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_GUEST"));
        OAuth2User u = (OAuth2User) auth.getPrincipal();

        if (isGuest) {
            // 온보딩용 정보 세션 저장
            req.getSession(true).setAttribute("pendingProfile", Map.of(
                    "provider", u.getAttribute("provider"),
                    "providerId", u.getAttribute("providerId"),
                    "userNm", u.getAttribute("userNm"),
                    "suggestedNickname", u.getAttribute("suggestedNickname"),
                    "email", u.getAttribute("email")
            ));
            res.sendRedirect(frontendBase + "/onboarding");
        } else {
            res.sendRedirect(frontendBase + "/login/success");
        }
    }
}
