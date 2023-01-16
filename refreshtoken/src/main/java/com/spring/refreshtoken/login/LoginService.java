package com.spring.refreshtoken.login;

import com.spring.refreshtoken.jwt.JwtTokenProvider;
import com.spring.refreshtoken.jwt.Token;
import com.spring.refreshtoken.user.User;
import com.spring.refreshtoken.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class LoginService {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;
    public Token login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다"));

        System.out.println("user = " + user);
//        if (user != null) {
//        if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
        Token token = jwtTokenProvider.createToken(user.getUsername());
        return token;


//            }
//        } else {
//            throw new IllegalArgumentException("사용자가 존재하지 않습니다");
//        }
//        throw new IllegalArgumentException("오류!");
    }


}
