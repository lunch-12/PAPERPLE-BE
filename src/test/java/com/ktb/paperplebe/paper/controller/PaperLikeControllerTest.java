package com.ktb.paperplebe.paper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktb.paperplebe.auth.config.jwt.JwtAuthorizationFilter;
import com.ktb.paperplebe.auth.config.jwt.JwtUtil;
import com.ktb.paperplebe.paper.fixture.PaperLikeFixture;
import com.ktb.paperplebe.paper.service.PaperLikeFacade;
import com.ktb.paperplebe.paper.service.PaperLikeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

@WebMvcTest(value = PaperLikeController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class),
})
@AutoConfigureRestDocs
public class PaperLikeControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected PaperLikeService paperLikeService;

    @MockBean
    protected PaperLikeFacade paperLikeFacade;

    @Test
    @DisplayName("좋아요 증가 테스트")
    @WithMockUser
    public void increaseLikeCount() throws Exception {
        // given
        willDoNothing().given(paperLikeFacade).increaseLikeCount(anyLong(), anyLong());

        // when
        ResultActions resultActions = mockMvc.perform(post("/paper/{paperId}/likes", PaperLikeFixture.PAPER_ID_1)
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());

        // restdocs
        resultActions.andDo(document("좋아요 증가",
                responseFields(
                        fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
                        fieldWithPath("successful").type(BOOLEAN).description("성공 여부")
                )
        ));
    }

    @Test
    @DisplayName("좋아요 취소 테스트")
    @WithMockUser
    public void decreaseLikeCount() throws Exception {
        // given
        willDoNothing().given(paperLikeFacade).decreaseLikeCount(anyLong(), anyLong());

        // when
        ResultActions resultActions = mockMvc.perform(delete("/paper/{paperId}/likes", PaperLikeFixture.PAPER_ID_1)
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());

        // restdocs
        resultActions.andDo(document("좋아요 취소",
                responseFields(
                        fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
                        fieldWithPath("successful").type(BOOLEAN).description("성공 여부")
                )
        ));
    }

    @Test
    @DisplayName("좋아요 상태 조회 테스트")
    @WithMockUser
    public void getLikeStatus() throws Exception {
        // given
        Map<Long, Boolean> likeStatus = PaperLikeFixture.createLikeStatus();
        given(paperLikeService.getLikeStatus(any(), any())).willReturn(likeStatus);

        // when
        ResultActions resultActions = mockMvc.perform(get("/paper/likes")
                .param("paperIds", PaperLikeFixture.PAPER_ID_1.toString(), PaperLikeFixture.PAPER_ID_2.toString())
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());

        // restdocs
        resultActions.andDo(document("좋아요 상태 조회",
                responseFields(
                        fieldWithPath("1").type(BOOLEAN).description("Paper 1의 좋아요 여부"),
                        fieldWithPath("2").type(BOOLEAN).description("Paper 2의 좋아요 여부")
                )
        ));
    }
}
