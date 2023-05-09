package com.frailty.backend.controller;

import com.frailty.backend.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping()
    public String login(Authentication authentication) {
        log.info("Token requested for user: {}", authentication.getName());
        String token = loginService.generateToken(authentication);
        log.info("Token is {}", token);
        return token;
    }
}
