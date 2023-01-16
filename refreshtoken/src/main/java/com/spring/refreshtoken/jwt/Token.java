package com.spring.refreshtoken.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Token {
//    private String grantType;
    private String username;
    private String accessToken;
    private String refreshToken;
//    private String key;

}
