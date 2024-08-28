package com.ktb.paperplebe.user.fixture;

import com.ktb.paperplebe.oauth.constant.SocialType;
import com.ktb.paperplebe.user.constant.UserRole;
import com.ktb.paperplebe.user.dto.UserInfoResponse;
import com.ktb.paperplebe.user.dto.UserNicknameRequest;
import com.ktb.paperplebe.user.entity.User;
import org.springframework.test.util.ReflectionTestUtils;

public class UserFixture {

    public static final long USER_ID_1 = 1L;

    public static final String NICKNAME_1 = "testUser1";

    private static final String SOCIAL_ID_1 = "socialId1";

    private static final UserRole ROLE_1 = UserRole.ROLE_USER;

    private static final SocialType SOCIAL_TYPE = SocialType.KAKAO;

    public static User createUser1() {
        User user = User.builder()
                .nickname(NICKNAME_1)
                .socialId(SOCIAL_ID_1)
                .role(ROLE_1)
                .socialType(SOCIAL_TYPE)
                .build();
        ReflectionTestUtils.setField(user, "id", USER_ID_1);
        return user;
    }

    public static UserInfoResponse createUserInfoResponse1() {
        User user = createUser1();
        return UserInfoResponse.of(user);
    }

    public static UserNicknameRequest createUserNicknameRequest() {
        return new UserNicknameRequest(NICKNAME_1);
    }
}
