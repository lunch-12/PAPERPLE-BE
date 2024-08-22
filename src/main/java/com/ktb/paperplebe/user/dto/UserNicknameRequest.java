package com.ktb.paperplebe.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserNicknameRequest {
    @NotBlank
    @Size(min = 1, max = 30)
    private String nickname;
}
