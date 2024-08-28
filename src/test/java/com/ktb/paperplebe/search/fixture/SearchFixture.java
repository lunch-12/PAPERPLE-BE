package com.ktb.paperplebe.search.fixture;

import com.ktb.paperplebe.paper.dto.PaperResponse;
import com.ktb.paperplebe.search.dto.SearchRequest;
import com.ktb.paperplebe.search.dto.SearchResponse;

import java.util.List;

public class SearchFixture {
    public static final String SEARCH_KEYWORD_1 = "Content";
    public static final String SEARCH_KEYWORD_2 = "연어초밥";

    public static SearchRequest createSearchRequest1() {
        return new SearchRequest(SEARCH_KEYWORD_1);
    }

    public static SearchRequest createSearchRequest2() {
        return new SearchRequest(SEARCH_KEYWORD_2);
    }

    public static SearchResponse createSearchResponse(List<PaperResponse> paperResponses) {
        return new SearchResponse(paperResponses);
    }
}
