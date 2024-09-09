package com.ktb.paperplebe.paper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktb.paperplebe.auth.config.jwt.JwtAuthorizationFilter;
import com.ktb.paperplebe.auth.config.jwt.JwtUtil;
import com.ktb.paperplebe.paper.dto.PaperRequest;
import com.ktb.paperplebe.paper.dto.PaperResponse;
import com.ktb.paperplebe.paper.fixture.PaperFixture;
import com.ktb.paperplebe.paper.service.PaperService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;



@WebMvcTest(value = PaperController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class),
})
@AutoConfigureRestDocs
public class PaperControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected PaperService paperService;

    @Test
    @DisplayName("페이퍼 생성")
    @WithMockUser
    public void createPaper() throws Exception {
        // given
        final PaperRequest paperRequest = PaperFixture.createPaperRequest1();
        final PaperResponse expectedResponse = PaperFixture.createPaperResponse1();

        given(paperService.createPaper(any(PaperRequest.class), any())).willReturn(expectedResponse);

        // when
        ResultActions resultActions = mockMvc.perform(post("/paper")
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paperRequest))
        );

        // then
        resultActions.andExpect(status().isOk());

        // restdocs
        resultActions.andDo(document("페이퍼 생성",
                requestFields(
                        fieldWithPath("content").description("내용"),
                        fieldWithPath("newspaperLink").description("신문 링크"),
                        fieldWithPath("view").description("조회수"),
                        fieldWithPath("newspaperSummary").description("신문 요약"),
                        fieldWithPath("image").description("이미지 URL"),
                        fieldWithPath("createdAt").type(STRING).optional().description("생성 시간")
                ),
                responseFields(
                        fieldWithPath("paperId").type(NUMBER).description("페이퍼 ID"),
                        fieldWithPath("content").type(STRING).description("내용"),
                        fieldWithPath("newspaperLink").type(STRING).description("신문 링크"),
                        fieldWithPath("view").type(NUMBER).description("조회수"),
                        fieldWithPath("newspaperSummary").type(STRING).description("신문 요약"),
                        fieldWithPath("image").type(STRING).description("이미지 URL"),
                        fieldWithPath("createdAt").type(STRING).optional().description("생성 시간")
                )
        ));
    }

    @Test
    @DisplayName("페이퍼 조회")
    @WithMockUser
    public void getPaper() throws Exception {
        // given
        final Long paperId = PaperFixture.PAPER_ID_1;
        final PaperResponse expectedResponse = PaperFixture.createPaperResponse1();

        given(paperService.getPaper(paperId)).willReturn(expectedResponse);

        // when
        ResultActions resultActions = mockMvc.perform(get("/paper/{paperId}", paperId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());

        // restdocs
        resultActions.andDo(document("페이퍼 조회",
                responseFields(
                        fieldWithPath("paperId").type(NUMBER).description("페이퍼 ID"),
                        fieldWithPath("content").type(STRING).description("내용"),
                        fieldWithPath("newspaperLink").type(STRING).description("신문 링크"),
                        fieldWithPath("view").type(NUMBER).description("조회수"),
                        fieldWithPath("newspaperSummary").type(STRING).description("신문 요약"),
                        fieldWithPath("image").type(STRING).description("이미지 URL"),
                        fieldWithPath("createdAt").description("생성 시간")
                )
        ));
    }

    @Test
    @DisplayName("페이퍼 수정")
    @WithMockUser
    public void updatePaper() throws Exception {
        // given
        final Long paperId = PaperFixture.PAPER_ID_1;
        final PaperRequest paperRequest = PaperFixture.createPaperRequest1();
        final PaperResponse expectedResponse = PaperFixture.createPaperResponse1();

        given(paperService.updatePaper(eq(paperId), any(PaperRequest.class))).willReturn(expectedResponse);

        // when
        ResultActions resultActions = mockMvc.perform(patch("/paper/{paperId}", paperId)
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paperRequest))
        );

        // then
        resultActions.andExpect(status().isOk());

        // restdocs
        resultActions.andDo(document("페이퍼 수정",
                requestFields(
                        fieldWithPath("content").description("내용"),
                        fieldWithPath("newspaperLink").description("신문 링크"),
                        fieldWithPath("view").description("조회수"),
                        fieldWithPath("newspaperSummary").description("신문 요약"),
                        fieldWithPath("image").description("이미지 URL"),
                        fieldWithPath("createdAt").type(STRING).optional().description("생성 시간")
                ),
                responseFields(
                        fieldWithPath("paperId").type(NUMBER).description("페이퍼 ID"),
                        fieldWithPath("content").type(STRING).description("내용"),
                        fieldWithPath("newspaperLink").type(STRING).description("신문 링크"),
                        fieldWithPath("view").type(NUMBER).description("조회수"),
                        fieldWithPath("newspaperSummary").type(STRING).description("신문 요약"),
                        fieldWithPath("image").type(STRING).description("이미지 URL"),
                        fieldWithPath("createdAt").type(STRING).optional().description("생성 시간")
                )
        ));
    }

    @Test
    @DisplayName("페이퍼 삭제")
    @WithMockUser
    public void deletePaper() throws Exception {
        // given
        final Long paperId = PaperFixture.PAPER_ID_1;

        // when
        ResultActions resultActions = mockMvc.perform(delete("/paper/{paperId}", paperId)
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());

        // restdocs
        resultActions.andDo(document("페이퍼 삭제"));
    }
}
