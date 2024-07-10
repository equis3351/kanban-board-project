package com.sparta.kanbanboardproject.domain.user.controller;

import com.sparta.kanbanboardproject.domain.user.dto.SignupRequestDto;
import com.sparta.kanbanboardproject.domain.user.service.UserService;
import com.sparta.kanbanboardproject.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "UserController")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/signup")
    public ResponseEntity<String> signUp(
            @Valid @RequestBody SignupRequestDto requestDto) {
        if (requestDto.getIsManager()) log.info("true");
        if (!requestDto.getIsManager()) log.info("false");
        userService.signUp(requestDto);
        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body("회원가입 성공!");
    }

    @PostMapping("/users/logout")
    public ResponseEntity<String> logout(HttpServletRequest request,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        userService.logout(request, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDetails.getUser() + " 아이디가 로그아웃 되었습니다.");
    }
}