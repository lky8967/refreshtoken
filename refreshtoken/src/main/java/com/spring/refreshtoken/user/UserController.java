package com.spring.refreshtoken.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/api/users/register")
    public String registerUser(@RequestBody UserRequestDto userRequestDto){
        try{
            userService.registerUser(userRequestDto);
            return "회원가입 완료";
        } catch (Exception e){
            e.printStackTrace(); // 예외 정보 출력
            throw e;             // 예외 던지기
        }
    }

}
