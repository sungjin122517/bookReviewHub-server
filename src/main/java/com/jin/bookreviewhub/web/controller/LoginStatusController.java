package com.jin.bookreviewhub.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginStatusController {

    @GetMapping("/success")
    public Map<String, Object> success(@AuthenticationPrincipal OAuth2User user) {
        return Map.of(
                "status", "OK",
                "providerUserId", user.getName(),
                "attributes", user.getAttributes()
        );
    }
}
