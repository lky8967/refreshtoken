package com.spring.refreshtoken.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j @Service
@RestController
@RequiredArgsConstructor
public class RefreshController {


    private final JwtService jwtService;

    @PostMapping("/api/refresh")
    public String validateRefreshToken(@RequestBody HashMap<String, String> bodyJson){

        log.info("refresh controller 실행");
        Map<String, String> map = jwtService.validateRefreshToken(bodyJson.get("refreshToken"));

        if(map.get("status").equals("402")){
            log.info("RefreshController - Refresh Token이 만료.");
            return "Refresh Token이 만료";
//            RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
//            return new ResponseEntity<RefreshApiResponseMessage>(refreshApiResponseMessage, HttpStatus.UNAUTHORIZED);
        }

        log.info("RefreshController - Refresh Token이 유효.");
        return "Refresh Token이 유효";
//        RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
//        return new ResponseEntity<RefreshApiResponseMessage>(refreshApiResponseMessage, HttpStatus.OK);

    }
}
