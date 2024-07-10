package com.sparta.kanbanboardproject.domain.user.service;

import com.sparta.kanbanboardproject.domain.user.dto.SignupRequestDto;
import com.sparta.kanbanboardproject.domain.user.entity.User;
import com.sparta.kanbanboardproject.domain.user.entity.UserRoleEnum;
import com.sparta.kanbanboardproject.domain.user.repository.UserRepository;
import com.sparta.kanbanboardproject.global.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "UserService")
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Value("${manager.key}")
    private String MANAGER_KEY;

    public void signUp(SignupRequestDto requestDto) {
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.getIsManager()){
            log.info("isManager true");
            if (MANAGER_KEY.equals(requestDto.getManagerToken())){
                log.info("managerKey equals");
                role = UserRoleEnum.MANAGER;
            }
        }
        User user = new User(requestDto.getUsername(), passwordEncoder.encode(requestDto.getPassword()), role);
        userRepository.save(user);
        log.info("회원가입 완료");
    }

    @Transactional
    public void logout(HttpServletRequest request, User user) {
        String accessToken = request.getHeader("Authorization").substring(7);

        jwtService.validateToken(accessToken);

        User saveUser = userRepository.findByUsername(user.getUsername()).orElseThrow(
                ()-> new IllegalArgumentException("유저를 찾을 수 없습니다.")
        );
        saveUser.logoutUser();

        log.info("로그아웃 성공");
    }
}