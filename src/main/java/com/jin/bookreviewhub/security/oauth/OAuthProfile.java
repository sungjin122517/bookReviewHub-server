package com.jin.bookreviewhub.security.oauth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* OAuth Profile model */

@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class OAuthProfile {
    private String provider;      // kakao / naver
    private String providerId;    // social distinct id
    private String email;         // nullable
    private String nickname;      // nullable
}
