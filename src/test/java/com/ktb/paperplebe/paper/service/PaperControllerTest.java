package com.ktb.paperplebe.paper.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktb.paperplebe.paper.controller.PaperController;
import com.ktb.paperplebe.paper.dto.PaperRequest;
import com.ktb.paperplebe.paper.dto.PaperResponse;
import com.ktb.paperplebe.paper.fixture.PaperFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

@WebMvcTest(value = PaperController.class)
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
    public void createPaper() throws Exception {
        // given
        final PaperRequest paperRequest = PaperFixture.createPaperRequest1();
        final PaperResponse expectedResponse = PaperFixture.createPaperResponse1();

        given(paperService.createPaper(any(PaperRequest.class))).willReturn(expectedResponse);

        // when
        ResultActions resultActions = mockMvc.perform(post("/paper")
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
                        fieldWithPath("image").description("이미지 URL")
                ),
                responseFields(
                        fieldWithPath("paperId").type(NUMBER).description("페이퍼 ID"),
                        fieldWithPath("content").type(STRING).description("내용"),
                        fieldWithPath("newspaperLink").type(STRING).description("신문 링크"),
                        fieldWithPath("view").type(NUMBER).description("조회수"),
                        fieldWithPath("newspaperSummary").type(STRING).description("신문 요약"),
                        fieldWithPath("image").type(STRING).description("이미지 URL")
                )
        ));
    }

    @Test
    @DisplayName("페이퍼 조회")
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
                        fieldWithPath("image").type(STRING).description("이미지 URL")
                )
        ));
    }

    @Test
    @DisplayName("페이퍼 수정")
    public void updatePaper() throws Exception {
        // given
        final Long paperId = PaperFixture.PAPER_ID_1;
        final PaperRequest paperRequest = PaperFixture.createPaperRequest1();
        final PaperResponse expectedResponse = PaperFixture.createPaperResponse1();

        given(paperService.updatePaper(eq(paperId), any(PaperRequest.class))).willReturn(expectedResponse);

        // when
        ResultActions resultActions = mockMvc.perform(patch("/paper/{paperId}", paperId)
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
                        fieldWithPath("image").description("이미지 URL")
                ),
                responseFields(
                        fieldWithPath("paperId").type(NUMBER).description("페이퍼 ID"),
                        fieldWithPath("content").type(STRING).description("내용"),
                        fieldWithPath("newspaperLink").type(STRING).description("신문 링크"),
                        fieldWithPath("view").type(NUMBER).description("조회수"),
                        fieldWithPath("newspaperSummary").type(STRING).description("신문 요약"),
                        fieldWithPath("image").type(STRING).description("이미지 URL")
                )
        ));
    }

    @Test
    @DisplayName("페이퍼 삭제")
    public void deletePaper() throws Exception {
        // given
        final Long paperId = PaperFixture.PAPER_ID_1;

        // when
        ResultActions resultActions = mockMvc.perform(delete("/paper/{paperId}", paperId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());

        // restdocs
        resultActions.andDo(document("페이퍼 삭제"));
    }
}
