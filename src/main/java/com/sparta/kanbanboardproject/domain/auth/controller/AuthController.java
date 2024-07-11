package com.sparta.kanbanboardproject.domain.auth.controller;

import com.sparta.kanbanboardproject.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j(topic = "AuthController")
public class AuthController {

    private final AuthService authService;
    @PostMapping("/auth/refresh-token")
    public ResponseEntity<String> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        authService.refreshToken(request, response);
        return ResponseEntity.status(HttpStatus.OK)
                .body("access와 refresh 토큰을 재발급했습니다.");
    }
}
