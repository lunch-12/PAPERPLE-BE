package com.ktb.paperplebe.user.entity;

import com.ktb.paperplebe.oauth.constant.SocialType;
import com.ktb.paperplebe.user.constant.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String profileImage;

    private String socialId;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;


    @Builder
    public User(String nickname, String socialId, UserRole role, SocialType socialType) {
        this.nickname = nickname;
        this.socialId = socialId;
        this.role = role;
        this.socialType = socialType;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
