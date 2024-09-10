package com.ktb.paperplebe.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktb.paperplebe.auth.config.jwt.JwtAuthorizationFilter;
import com.ktb.paperplebe.auth.config.jwt.JwtUtil;
import com.ktb.paperplebe.user.dto.UserInfoResponse;
import com.ktb.paperplebe.user.dto.UserNicknameRequest;
import com.ktb.paperplebe.user.fixture.UserFixture;
import com.ktb.paperplebe.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserService userService;

    @Test
    @DisplayName("사용자 정보 조회 테스트")
    @WithMockUser
    public void getUserInfo() throws Exception {
        // given
        final UserInfoResponse expectedResponse = UserFixture.createUserInfoResponse1();

        given(userService.getUserInfo(any())).willReturn(expectedResponse);

        // when
        ResultActions resultActions = mockMvc.perform(get("/user")
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk()).andDo(print());

        // restdocs
        resultActions.andDo(document("사용자 정보 조회",
                responseFields(
                        fieldWithPath("userId").type(NUMBER).description("사용자 ID"),
                        fieldWithPath("nickname").type(STRING).description("사용자 닉네임"),
                        fieldWithPath("profileImage").type(STRING).description("프로필 이미지 URL")
                )
        ));
    }

    @Test
    @DisplayName("사용자 닉네임 수정 테스트")
    @WithMockUser
    public void updateNickname() throws Exception {
        // given
        final UserNicknameRequest nicknameRequest = UserFixture.createUserNicknameRequest();
        final UserInfoResponse expectedResponse = UserFixture.createUserInfoResponse1();

        given(userService.updateNickname(any(), any())).willReturn(expectedResponse);

        // when
        ResultActions resultActions = mockMvc.perform(patch("/user/nickname")
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nicknameRequest))
        );

        // then
        resultActions.andExpect(status().isOk()).andDo(print());

        // restdocs
        resultActions.andDo(document("사용자 닉네임 수정",
                requestFields(
                        fieldWithPath("nickname").type(STRING).description("변경할 사용자 닉네임")
                ),
                responseFields(
                        fieldWithPath("userId").type(NUMBER).description("사용자 ID"),
                        fieldWithPath("nickname").type(STRING).description("변경된 사용자 닉네임"),
                        fieldWithPath("profileImage").type(STRING).description("프로필 이미지 URL")
                )
        ));
    }
}
