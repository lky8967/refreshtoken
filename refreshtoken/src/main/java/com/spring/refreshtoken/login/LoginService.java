package com.spring.refreshtoken.login;

import com.spring.refreshtoken.jwt.JwtTokenProvider;
import com.spring.refreshtoken.jwt.RefreshToken;
import com.spring.refreshtoken.jwt.RefreshTokenRepository;
import com.spring.refreshtoken.jwt.Token;
import com.spring.refreshtoken.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @RequiredArgsConstructor
public class LoginService {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void login(Token token) {

        RefreshToken refreshToken = RefreshToken.builder().username(token.getUsername()).refreshToken(token.getRefreshToken()).build();
        String loginUsername = refreshToken.getUsername();
        if(refreshTokenRepository.existsByUsername(loginUsername)){
//            log.info("기존의 존재하는 refresh 토큰 삭제");
            refreshTokenRepository.deleteByUsername(loginUsername);
        }
        refreshTokenRepository.save(refreshToken);

    }
//    public Token login(LoginRequestDto loginRequestDto) {
//        User user = userRepository.findByUsername(loginRequestDto.getUsername())
//                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다"));
//
//        System.out.println("user = " + user);
////        if (user != null) {
////        if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
//        Token token = jwtTokenProvider.createToken(user.getUsername());
//        return token;
//
//
////            }
////        } else {
////            throw new IllegalArgumentException("사용자가 존재하지 않습니다");
////        }
////        throw new IllegalArgumentException("오류!");
//    }


}
