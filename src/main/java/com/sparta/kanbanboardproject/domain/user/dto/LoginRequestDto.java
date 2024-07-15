package com.sparta.kanbanboardproject.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @NotBlank
    @Size(min = 3, max = 20, message = "username 은 3 ~ 15 글자 이내만 입력 가능합니다.")
    private String username;

    @NotBlank
    @Size(min = 5, max = 20, message = "password 은 5 ~ 20 글자 이내만 입력 가능합니다.")
    private String password;
}
