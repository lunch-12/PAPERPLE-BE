package com.ktb.paperplebe.search.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktb.paperplebe.paper.dto.PaperResponse;
import com.ktb.paperplebe.paper.fixture.PaperFixture;
import com.ktb.paperplebe.search.dto.SearchRequest;
import com.ktb.paperplebe.search.dto.SearchResponse;
import com.ktb.paperplebe.search.fixture.SearchFixture;
import com.ktb.paperplebe.search.service.SearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class SearchControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected SearchService searchService;

    @Test
    @DisplayName("검색 요청")
    public void searchPapers() throws Exception {
        // given
        final SearchRequest searchRequest = SearchFixture.createSearchRequest1();
        final List<PaperResponse> paperResponses = PaperFixture.createPaperResponseList();
        final SearchResponse expectedResponse = SearchFixture.createSearchResponse(paperResponses);

        given(searchService.search(any(SearchRequest.class), any())).willReturn(expectedResponse);

        // when
        ResultActions resultActions = mockMvc.perform(get("/search")
                .param("keyword", searchRequest.keyword())
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());

        // restdocs
        resultActions.andDo(document("검색",
                queryParameters (
                        parameterWithName("keyword").description("검색 키워드"),
                        parameterWithName("page").description("페이지 번호"),
                        parameterWithName("size").description("페이지 크기")
                ),
                responseFields(
                        fieldWithPath("papers[].paperId").type(NUMBER).description("페이퍼 ID"),
                        fieldWithPath("papers[].content").type(STRING).description("내용"),
                        fieldWithPath("papers[].newspaperLink").type(STRING).description("신문 링크"),
                        fieldWithPath("papers[].tags").type(ARRAY).description("조회수"),
                        fieldWithPath("papers[].newspaperSummary").type(STRING).description("신문 요약"),
                        fieldWithPath("papers[].image").type(STRING).description("이미지 URL"),
                        fieldWithPath("papers[].createdAt").type(STRING).description("생성 시간"),
                        fieldWithPath("papers[].nickname").type(STRING).description("유저 닉네임"),
                        fieldWithPath("papers[].profileImage").type(STRING).description("유저 프로필")
                )
        ));
    }
}
