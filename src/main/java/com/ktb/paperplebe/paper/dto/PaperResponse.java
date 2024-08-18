package com.ktb.paperplebe.paper.dto;

import com.ktb.paperplebe.paper.entity.Paper;

public record PaperResponse(
        Long paperId,
        String content,
        String newspaperLink,
        int view,
        String newspaperSummary,
        String image
) {
    public static PaperResponse of(final Paper paper) {
        return new PaperResponse(
                paper.getId(),
                paper.getContent(),
                paper.getNewspaperLink(),
                paper.getView(),
                paper.getNewspaperSummary(),
                paper.getImage()
        );
    }
}
