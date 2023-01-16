package com.spring.refreshtoken.login;

import com.spring.refreshtoken.jwt.JwtTokenProvider;

//import com.spring.refreshtoken.jwt.Token;
import com.spring.refreshtoken.jwt.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController @RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;
//    private final JwtService jwtService;

    // 로그인
    @PostMapping("/api/login")
    public Token login(final HttpServletResponse response , @RequestBody LoginRequestDto loginRequestDto) {
//        log.info("user email = {}", user.get("userEmail"));
        Token Token = jwtTokenProvider.createToken(loginRequestDto.getUsername());
//        String login = String.valueOf(loginService.login(loginRequestDto));
//        log.info("getroleeeee = {}", member.getRoles());
        loginService.login(Token);
//        return "로그인";
        return Token;
    }
}
