package com.ktb.paperplebe.auth.dto;

import com.ktb.paperplebe.user.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class JwtClaimDTO {
    private Long userId;
    private UserRole role;
}
