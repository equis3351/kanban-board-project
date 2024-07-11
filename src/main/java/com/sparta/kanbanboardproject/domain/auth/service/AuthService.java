package com.sparta.kanbanboardproject.domain.auth.service;

import com.sparta.kanbanboardproject.domain.user.entity.User;
import com.sparta.kanbanboardproject.domain.user.repository.UserRepository;
import com.sparta.kanbanboardproject.global.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "TokenService")
@Component
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = request.getHeader("RefreshToken").substring(7);

        String username = jwtService.extractUsername(refreshToken);

        User getUser = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("유저를 찾을 수 없습니다.")
        );

        if (!refreshToken.equals(getUser.getRefreshToken().substring(7))) {
            throw new IllegalArgumentException("refreshToken이 유효하지 않습니다.");
        }

        // refreshToken 유효성 확인
        if (jwtService.validateToken(refreshToken)) {

            // accessToken 새로 발급
            String newAccessToken = jwtService.createToken(username);
            log.info("새로운 access token : {}", newAccessToken);
            //refreshToken 새로 발급
            String newRefreshToken = jwtService.createRefreshToken(username);
            log.info("새로운 refresh token : {}", newRefreshToken);

            User saveUser = userRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
            saveUser.setRefreshToken(newRefreshToken);
            userRepository.save(saveUser);

            response.setHeader("Authorization", newAccessToken);
            response.setHeader("RefreshToken", newRefreshToken);
        }
    }
}