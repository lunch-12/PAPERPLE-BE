package com.ktb.paperplebe.user.controller;

import com.ktb.paperplebe.user.constant.UserRole;
import com.ktb.paperplebe.user.dto.UserInfoResponse;
import com.ktb.paperplebe.user.dto.UserNicknameRequest;
import com.ktb.paperplebe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    @Secured(UserRole.ROLE_USER_VALUE)
    @GetMapping()
    public UserInfoResponse getUserInfo(
            @AuthenticationPrincipal Long id
    ) {
        return userService.getUserInfo(id);
    }

    @Secured(UserRole.ROLE_USER_VALUE)
    @PatchMapping("/nickname")
    public UserInfoResponse updateNickname(
            @RequestBody @Validated UserNicknameRequest nicknameRequest,
            @AuthenticationPrincipal Long id
    ) {
        return userService.updateNickname(id, nicknameRequest.getNickname());
    }
}
