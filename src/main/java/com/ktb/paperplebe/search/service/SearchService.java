package com.ktb.paperplebe.search.service;

import com.ktb.paperplebe.paper.dto.PaperResponse;
import com.ktb.paperplebe.paper.entity.Paper;
import com.ktb.paperplebe.paper.repository.PaperRepository;
import com.ktb.paperplebe.search.dto.SearchRequest;
import com.ktb.paperplebe.search.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SearchService {
    private final PaperRepository paperRepository;

    public SearchResponse search(SearchRequest searchRequest) {
        List<Paper> papers = paperRepository.findByContentContaining(searchRequest.keyword());

        List<PaperResponse> paperResponses = papers.stream()
                .map(PaperResponse::of)
                .collect(Collectors.toList());

        return SearchResponse.of(paperResponses);
    }
}
