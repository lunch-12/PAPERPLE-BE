package com.ktb.paperplebe.paper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktb.paperplebe.auth.config.jwt.JwtAuthorizationFilter;
import com.ktb.paperplebe.auth.config.jwt.JwtUtil;
import com.ktb.paperplebe.paper.dto.PaperRequest;
import com.ktb.paperplebe.paper.dto.PaperResponse;
import com.ktb.paperplebe.paper.dto.UserPaperResponse;
import com.ktb.paperplebe.paper.fixture.PaperFixture;
import com.ktb.paperplebe.paper.service.PaperService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
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
@DisplayName("페이퍼 목록 조회")
@WithMockUser
public void getMyPapers() throws Exception {
    // given
    final List<UserPaperResponse> expectedResponse = PaperFixture.createUserPaperResponseList();
    given(paperService.getMyPapers(any())).willReturn(expectedResponse);

    // when
    ResultActions resultActions = mockMvc.perform(get("/paper/my-papers")
            .contentType(MediaType.APPLICATION_JSON)
            .with(csrf().asHeader())
    );

    // then
    resultActions.andExpect(status().isOk());

    // restdocs
    resultActions.andDo(document("페이퍼 목록 조회",
            responseFields(
                    fieldWithPath("[].paperId").type(NUMBER).description("페이퍼 ID"),
                    fieldWithPath("[].content").type(STRING).description("내용"),
                    fieldWithPath("[].newspaperLink").type(STRING).description("신문 링크"),
                    fieldWithPath("[].view").type(NUMBER).description("조회수"),
                    fieldWithPath("[].newspaperSummary").type(STRING).description("신문 요약"),
                    fieldWithPath("[].image").type(STRING).optional().description("이미지 URL"),
                    fieldWithPath("[].createdAt").type(STRING).description("생성 시간"),
                    fieldWithPath("[].nickname").type(STRING).description("작성자 닉네임"),
                    fieldWithPath("[].profileImage").type(STRING).description("작성자 프로필 이미지 URL"),
                    fieldWithPath("[].isLikedByCurrentUser").type(BOOLEAN).description("현재 로그인한 유저의 좋아요 여부")
            )
    ));
}
  
  @Test
    @DisplayName("페이퍼 목록 조회")
    @WithMockUser
    public void getPaperList() throws Exception {
        // given
        final List<PaperResponse> paperResponseList = PaperFixture.createPaperResponseList();

        // 페이퍼 목록 조회 시, 페이퍼 응답 리스트 반환을 기대
        given(paperService.getPaperList(any(Pageable.class), any(String.class))).willReturn(paperResponseList);

        // when
        ResultActions resultActions = mockMvc.perform(get("/paper")
                .param("orderBy", "createdAt") // 정렬 기준 (생성일자 순)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());

        // restdocs
        resultActions.andDo(document("페이퍼 목록 조회",
                queryParameters(
                        parameterWithName("orderBy").description("정렬 기준 (createdAt: 생성일자 순, like: 좋아요 순)")
                ),
                responseFields(
                        fieldWithPath("[].paperId").type(NUMBER).description("페이퍼 ID"),
                        fieldWithPath("[].content").type(STRING).description("내용"),
                        fieldWithPath("[].newspaperLink").type(STRING).description("뉴스 링크"),
                        fieldWithPath("[].view").type(NUMBER).description("조회수"),
                        fieldWithPath("[].newspaperSummary").type(STRING).description("신문 요약"),
                        fieldWithPath("[].image").type(STRING).description("이미지 URL")
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
