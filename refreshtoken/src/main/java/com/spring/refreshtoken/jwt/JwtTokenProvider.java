package com.spring.refreshtoken.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.accessSecretKey}")
    private String accessSecretKey;

//    private static final long TOKEN_VALID_TIME = 1000L * 60 * 1440;
    // 30분
    private static final long ACCESS_TOKEN_TIME = 60 * 30;
    // 1달
    private static final long REFRESH_TOKEN_TIME = 60 * 60 * 24 * 30;


    // 객체 초기화, secretKey 를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }



    // JWT 토큰 생성
    public Token createToken(String userPk) {

        byte[] refreshSecretKey = secretKey.getBytes(StandardCharsets.UTF_8);

        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위

        Map<String , Object> claimsMap = new HashMap<String , Object>();
        claimsMap.put("username" ,userPk);
        Date now = new Date();

        // 과거에 했던 코드
//        return Jwts.builder()
//                .setClaims(claimsMap)
//                .setIssuedAt(now) // 토큰 발행 시간 정보
//                .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME)) // set Expire Time
//                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
//                .compact();

        //Access Token
        String accessToken = Jwts.builder()
                .setClaims(claimsMap) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        //Refresh Token
        String refreshToken =  Jwts.builder()
                .setClaims(claimsMap) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        return Token.builder().accessToken(accessToken).refreshToken(refreshToken).username(userPk).build();

    }




    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public static String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    //////////////
//    public String validateRefreshToken(RefreshToken refreshTokenObj){
//
//
//        // refresh 객체에서 refreshToken 추출
//        String refreshToken = refreshTokenObj.getRefreshToken();
//
//
//        try {
//            // 검증
//            Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(refreshToken);
//
//            //refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰을 생성합니다.
//            if (!claims.getBody().getExpiration().before(new Date())) {
//                return recreationAccessToken(claims.getBody().get("sub").toString(), claims.getBody().get("roles"));
//            }
//        }catch (Exception e) {
//
//            //refresh 토큰이 만료되었을 경우, 로그인이 필요합니다.
//            return null;
//
//        }
//
//        return null;
//    }
//
//    public String recreationAccessToken(String userEmail, Object roles){
//
//        Claims claims = Jwts.claims().setSubject(userEmail); // JWT payload 에 저장되는 정보단위
//        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
//        Date now = new Date();
//
//        //Access Token
//        String accessToken = Jwts.builder()
//                .setClaims(claims) // 정보 저장
//                .setIssuedAt(now) // 토큰 발행 시간 정보
//                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // set Expire Time
//                .signWith(SignatureAlgorithm.HS256, accessSecretKey)  // 사용할 암호화 알고리즘과
//                // signature 에 들어갈 secret값 세팅
//                .compact();
//
//        return accessToken;
//    }

}
